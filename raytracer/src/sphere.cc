#include "sphere.hh"
#include "input.hh"

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

Color& Sphere::apply_point_lights(const Input *file,
                                  Color& r,
                                  const Vector3& intersect,
                                  int ttl)
{
  Vector3 normal = intersect - pos;
  for (const PointLight& pl: file->get_point_lights())
  {
    Vector3 l = pl.pos - intersect;
    double ln = l.normalize().dot_product(normal);
    double ld = ln * attr.diff * l.norm();
    Color c = pl.color * color;

    r = r + c * ld;
  }
  Ray new_ray(intersect - normal * (2 * normal.dot_product(intersect)), intersect);
  Color res;
  if (attr.refl > 0)
  {
    new_ray.cast(file, res, ttl);
    r = res * attr.refl * 0.1 + r;
  }

  // r = apply_direction_lights(file, r, intersect);
  return r;
}
