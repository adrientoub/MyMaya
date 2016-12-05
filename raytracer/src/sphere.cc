#include "sphere.hh"
#include "input.hh"

#include <iostream>

Sphere::Sphere(const Vector3& pos, const Attributes& attr, const Color& color,
               double radius)
       : Shape(pos, attr, color), radius(radius)
{}

Vector3 Sphere::intersect(const Ray& ray)
{
  const double a = ray.direction.dot_product(ray.direction);
  const Vector3 sub = ray.position - pos;
  const double b = 2 * ray.direction.dot_product(sub);
  const double c = sub.dot_product(sub) - std::pow(radius, 2.);
  const double delta = b * b - 4 * a * c;

  if (delta < 0)
    return Vector3();
  const double t0 = std::min((-b + std::sqrt(delta)) / (2 * a),
                             (-b - std::sqrt(delta)) / (2 * a));

  return ray.position + ray.direction * t0;
}

std::istream& operator>>(std::istream& is, Sphere& sphere)
{
  return is >> sphere.radius >> sphere.pos >> sphere.attr >> sphere.color;
}

std::ostream& operator<<(std::ostream& os, const Sphere& sphere)
{
  return os << "Sphere: R: " << sphere.radius << " Pos: " << sphere.pos
            << " " << sphere.attr << " Color: " << sphere.color;
}

Color& Sphere::apply_directional_lights(const Input& file, Color& out_color,
                                        const Vector3& intersect)
{
  Vector3 normal = pos - intersect;
  for (const DirectionalLight& dl: file.get_directional_lights())
  {
    double ln = dl.dir.dot_product(normal.normalize());
    double ld = attr.diff * ln;

    Color c = dl.color * color;
    out_color = out_color + c * ld;
  }
  out_color = apply_ambiant_light(file, out_color);
  return out_color;
}


Color& Sphere::apply_ambiant_light(const Input& file, Color& out_color)
{
  return out_color = out_color + file.get_ambiant_light().color *
                     (out_color + color * 0.075);
}

Color& Sphere::apply_point_lights(const Input& file,
                                  Color& out_color,
                                  const Vector3& intersect,
                                  size_t ttl)
{
  Vector3 normal = intersect - pos;
  for (const PointLight& pl: file.get_point_lights())
  {
    Vector3 l = pl.pos - intersect;
    double ln = l.normalize().dot_product(normal);
    double ld = ln * attr.diff * l.norm();
    Color c = pl.color * color;

    out_color = out_color + c * ld;
  }
  Ray new_ray(intersect - normal * (2 * normal.dot_product(intersect)), intersect);
  Color res;
  if (attr.refl > 0)
  {
    new_ray.cast(file, res, ttl);
    out_color = res * attr.refl * 0.1 + out_color;
  }

  out_color = apply_directional_lights(file, out_color, intersect);
  return out_color;
}

std::ostream& Sphere::display(std::ostream& os) const
{
  return os << *this;
}
