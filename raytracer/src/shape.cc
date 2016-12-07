#include "shape.hh"
#include "input.hh"

#include <utility>

Shape::Shape(const Vector3& pos, const Attributes& attr, const Color& color)
      : pos(pos), attr(attr), color(color)
{}

std::ostream& operator<<(std::ostream& os, const Shape& shape)
{
  return shape.display(os);
}

void Shape::apply_ambiant_light(const Input& file, Color& out_color)
{
  out_color = out_color + file.get_ambiant_light().color * color * 0.1;
}

double Shape::get_directional_shadows(const Input& file,
                                      const Vector3& intersect,
                                      const DirectionalLight& dl) const
{
  std::vector<shape_double> collided;
  for (const std::shared_ptr<Shape> shape: file.get_shapes())
  {
    // Do not collide with itself
    if (shape.get() == this)
      continue;
    Vector3 vect = shape->intersect(Ray(-dl.dir, intersect));
    if (vect == Vector3())
      continue;
    if (shape->attr.opac == 1.)
      return 0.;
    else
      collided.emplace_back(shape, vect.norm());
  }

  // Handle partially transparent shapes
  double shadow = 1.;
  sort_shape_double(collided);
  for (const auto& pair: collided)
    shadow *= (1 - pair.first->attr.opac);

  return shadow;
}

void Shape::apply_directional_lights(const Input& file, Color& out_color,
                                     const Vector3& intersect)
{
  Vector3 normal = normal_vect(intersect);
  for (const DirectionalLight& dl: file.get_directional_lights())
  {
    double shadow = get_directional_shadows(file, intersect, dl);

    double ln = dl.dir.dot_product(normal.normalize());
    double ld = attr.diff * ln * shadow;

    Color c = dl.color * color;
    out_color = out_color + c * ld;
  }
  apply_ambiant_light(file, out_color);
}

void Shape::apply_point_lights(const Input& file,
                               Color& out_color,
                               const Vector3& intersect,
                               size_t ttl)
{
  Vector3 normal = normal_vect_point(intersect);
  for (const PointLight& pl: file.get_point_lights())
  {
    Vector3 l = pl.pos - intersect;
    double ln = l.normalize().dot_product(normal);
    double ld = ln * attr.diff * (5 / l.norm());
    Color c = pl.color * color;
    out_color = out_color + c * ld;
  }
  Ray new_ray(intersect - normal * (2 * normal.dot_product(intersect)),
              intersect);
  Color res;
  if (attr.refl > 0)
  {
    new_ray.cast(file, res, ttl);
    out_color = out_color + res * attr.refl * 0.1;
  }

  apply_directional_lights(file, out_color, intersect);
}

Vector3 Shape::normal_vect_point(const Vector3& intersect) const
{
  return normal_vect(intersect);
}
