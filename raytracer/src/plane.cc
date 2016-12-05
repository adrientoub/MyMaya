#include "plane.hh"
#include "input.hh"

#include <iostream>

Plane::Plane(const Vector3& pos, const Attributes& attr, const Color& color,
             double d)
      : Shape(pos, attr, color), d(d)
{}

Vector3 Plane::intersect(const Ray& ray)
{
  const Vector3 r = ray.direction.normalize();
  const Vector3 p = pos.normalize();
  const double p_cal = p.dot_product(ray.position) + d;
  const double r_cal = p.dot_product(r);
  if (r_cal != 0)
  {
    const double t0 = -p_cal / r_cal;

    if (0 < t0)
      return ray.position + r * t0;
  }
  return Vector3();
}

std::istream& operator>>(std::istream& is, Plane& plane)
{
  return is >> plane.pos >> plane.d >> plane.attr >> plane.color;
}

std::ostream& operator<<(std::ostream& os, const Plane& plane)
{
  return os << "Plane: Pos: " << plane.pos << " " <<  plane.d
            << " " << plane.attr << " Color: " << plane.color;
}

Color& Plane::apply_directional_lights(const Input& file, Color& out_color,
                                       const Vector3&)
{
  Vector3 normal = pos;
  for (const DirectionalLight& dl : file.get_directional_lights())
  {
    float ln = dl.dir.dot_product(normal.normalize());
    float ld = attr.diff * ln;

    Color c = dl.color * color;
    out_color = out_color + c * ld;
  }
  out_color = apply_ambiant_light(file, out_color);

  return out_color;
}

Color& Plane::apply_ambiant_light(const Input& file, Color& out_color)
{
  return out_color = out_color + file.get_ambiant_light().color * color;
}

Color& Plane::apply_point_lights(const Input& file,
                                 Color& out_color,
                                 const Vector3& intersect,
                                 int ttl)
{
  Vector3 normal = pos;
  for (const PointLight& pl : file.get_point_lights())
  {
    Vector3 l = pl.pos - intersect;
    float ln = l.normalize().dot_product(normal);
    float ld = ln * attr.diff * l.norm();
    Color c = pl.color * color;
    out_color = out_color + c * ld;
  }
  Ray new_ray(intersect - (normal * 2 * normal.dot_product(intersect)),
              intersect);
  Color res;
  if (attr.refl > 0)
  {
    new_ray.cast(file, res, ttl);
    out_color = res * attr.refl * 0.1 + out_color;
  }

  out_color = apply_directional_lights(file, out_color, intersect);
  return out_color;
}

std::ostream& Plane::display(std::ostream& os) const
{
  return os << *this;
}
