#include "triangle.hh"
#include "input.hh"

#include <iostream>

Triangle::Triangle(const Vector3& a, const Vector3& b, const Vector3& c,
                   const Attributes& attr, const Color& color)
      : Shape(a, attr, color), b(b), c(c)
{}

Vector3 Triangle::intersect(const Ray& ray)
{
  Vector3& a = pos;
  Vector3 ab = (b - a).normalize();
  Vector3 ac = (c - a).normalize();
  Vector3 n = ab * ac;
  double d = -1 * n.dot_product(a);
  double t0 = (n.dot_product(ray.position) + d)
               * (-1 / n.dot_product(ray.direction));
  Vector3 p = ray.position + ray.direction * t0;
  Vector3 ap = (p - a).normalize();
  double s = ap.dot_product(ac);
  double r = ap.dot_product(ab);

  if ((0 <= r && r <= 1) || (0 <= s && s <= 1)
      || (0 <= (r + s) && (r + s) <= 1))
    return p;
  else
    return Vector3();
}

std::istream& operator>>(std::istream& is, Triangle& triangle)
{
  return is >> triangle.pos >> triangle.b >> triangle.c >> triangle.attr
            >> triangle.color;
}

std::ostream& operator<<(std::ostream& os, const Triangle& triangle)
{
  return os << "Triangle: Pos: " << triangle.pos << " " << triangle.b << " "
            << triangle.c << " " << triangle.attr << " Color: "
            << triangle.color;
}

Vector3 Triangle::normal_vect(const Vector3&) const
{
  const Vector3& a = pos;
  Vector3 ab = b - a;
  Vector3 ac = c - a;
  return ab * ac;
}

std::ostream& Triangle::display(std::ostream& os) const
{
  return os << *this;
}
