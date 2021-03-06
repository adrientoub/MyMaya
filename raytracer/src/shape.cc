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

void Shape::apply_ambiant_light(const Input& file, Color& out_color) const
{
  out_color += file.get_ambiant_light().color * color * 0.1;
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
    if (!vect)
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
                                     const Vector3& intersect) const
{
  Vector3 normal = normal_vect(intersect);
  for (const DirectionalLight& dl: file.get_directional_lights())
  {
    double shadow = get_directional_shadows(file, intersect, dl);

    double ln = dl.dir.dot_product(normal.normalize());
    double ld = attr.diff * ln * shadow;

    Color c = dl.color * color;
    out_color += c * ld;
  }
}

Vector3 Shape::refraction_vector(const Vector3& incidence,
                                 const Vector3& normal) const
{
  double cosi = std::min(std::max(incidence.dot_product(normal), -1.), 1.);
  double etai = 1.;
  double etat = attr.refr;

  Vector3 normal_refr = normal;
  if (cosi < 0)
    cosi = -cosi;
  else
  {
    std::swap(etai, etat);
    normal_refr = -normal;
  }
  double eta = etai / etat;
  double k = 1 - eta * eta * (1 - cosi * cosi);
  if (k < 0)
    return Vector3();

  return incidence * eta + normal_refr * (eta * cosi - std::sqrt(k));
}

void Shape::refract(const Input& file, Color& refraction_color,
                    const Vector3& intersect, const Vector3& normal,
                    const Vector3& direction, const size_t ttl) const
{
  bool outside = direction.dot_product(normal) < 0;
  Vector3 refr = refraction_vector(direction.normalize(), normal.normalize());
  if (refr)
  {
    Vector3 bias = normal * 0.01;
    Ray refract_ray(refr.normalize(),
                    outside ? intersect - bias: intersect + bias);
    refract_ray.cast(file, refraction_color, ttl);
    refraction_color = refraction_color * (1 - attr.opac);
  }
}

void Shape::reflect(const Input& file, Color& reflection_color,
                    const Vector3& intersect, const Vector3& normal,
                    const size_t ttl) const
{
  Ray new_ray(intersect - normal * (2 * normal.dot_product(intersect)),
              intersect);
  new_ray.cast(file, reflection_color, ttl);
  reflection_color = reflection_color * attr.refl * 0.1;
}

void Shape::apply_point_lights(const Input& file, Color& out_color,
                               const Vector3& intersect,
                               const Vector3& normal) const
{
  for (const PointLight& pl: file.get_point_lights())
  {
    Vector3 l = pl.pos - intersect;
    double ln = l.normalize().dot_product(normal);
    double ld = ln * attr.diff * (5 / l.norm());
    Color c = pl.color * color;
    out_color += c * ld;
  }
}

void Shape::apply_lights(const Input& file, Color& out_color,
                         const Vector3& intersect,
                         const Vector3& direction, size_t ttl) const
{
  Vector3 normal = normal_vect_point(intersect);
  Color lights_color;
  apply_point_lights(file, lights_color, intersect, normal);
  apply_directional_lights(file, lights_color, intersect);
  apply_ambiant_light(file, lights_color);

  Color reflection_color;
  if (attr.refl > 0)
    reflect(file, reflection_color, intersect, normal, ttl);

  Color refraction_color;
  if (attr.opac < 1)
    refract(file, refraction_color, intersect, normal, direction, ttl);

  out_color += lights_color * attr.opac + refraction_color + reflection_color;
}

Vector3 Shape::normal_vect_point(const Vector3& intersect) const
{
  return normal_vect(intersect);
}
