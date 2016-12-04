#include "sphere.hh"

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
