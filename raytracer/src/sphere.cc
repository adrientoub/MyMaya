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
  double t0 = (-b + std::sqrt(delta)) / (2 * a);
  double t1 = (-b - std::sqrt(delta)) / (2 * a);

  if (t0 > t1)
    std::swap(t0, t1);
  if (t0 < 0 && t1 < 0)
    return Vector3();

  if (t0 < 0)
    t0 = t1;

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

Vector3 Sphere::normal_vect_point(const Vector3& intersect) const
{
  return intersect - pos;
}

Vector3 Sphere::normal_vect(const Vector3& intersect) const
{
  return pos - intersect;
}

std::ostream& Sphere::display(std::ostream& os) const
{
  return os << *this;
}
