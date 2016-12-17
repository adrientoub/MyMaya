#include "triangle.hh"
#include "input.hh"

#include <iostream>
#include <iomanip>

Triangle::Triangle(const Vector3& a, const Vector3& b, const Vector3& c,
                   const Attributes& attr, const Color& color)
      : Shape(a, attr, color), b(b), c(c)
{}

Vector3 Triangle::normal_vect(const Vector3&) const
{
  const Vector3& a = pos;
  Vector3 ab = b - a;
  Vector3 ac = c - a;
  return ab * ac;
}

Vector3 Triangle::intersect(const Ray& ray)
{
  // Argument is ignored
  Vector3 n = normal_vect(ray.position);
  const Vector3& a = pos;

  double n_dot_direction = n.dot_product(ray.direction);
  if (std::abs(n_dot_direction) < epsilon)
    return Vector3();
  double d = n.dot_product(a);
  double t0 = (n.dot_product(ray.position) + d)
               / n_dot_direction;

  // Triangle is behind the ray
  if (t0 < 0)
    return Vector3();

  Vector3 p = ray.position + ray.direction * t0;

  // perpendicular to triangle plane
  Vector3 C;

  Vector3 ab = b - a;
  Vector3 ap = p - a;
  C = ab * ap;
  if (n.dot_product(C) < 0)
    return Vector3();

  Vector3 bc = c - b;
  Vector3 bp = p - b;
  C = bc * bp;
  if (n.dot_product(C) < 0)
    return Vector3();

  Vector3 ca = a - c;
  Vector3 cp = p - c;
  C = ca * cp;
  if (n.dot_product(C) < 0)
    return Vector3();

  return p;
}

std::istream& operator>>(std::istream& is, Triangle& triangle)
{
  return is >> std::quoted(triangle.name) >> triangle.pos >> triangle.b
            >> triangle.c >> triangle.attr >> triangle.color;
}

std::ostream& operator<<(std::ostream& os, const Triangle& triangle)
{
  return os << "Triangle: " << triangle.name << " Pos: " << triangle.pos << " "
            << triangle.b << " " << triangle.c << " " << triangle.attr
            << " Color: " << triangle.color;
}

std::ostream& Triangle::display(std::ostream& os) const
{
  return os << *this;
}
