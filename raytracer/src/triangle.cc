#include "triangle.hh"
#include "input.hh"

#include <iostream>
#include <iomanip>
#include <array>

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

std::array<Vector3, 3> Triangle::get_vertices() const
{
  std::array<Vector3, 3> vertices {{ pos, b, c }};
  return vertices;
}

// Use Möller-Trumbore intersection algorithm
Vector3 Triangle::intersect(const Ray& ray) const
{
  const Vector3& a = pos;

  Vector3 ab = b - a;
  Vector3 ac = c - a;
  Vector3 pvec = ray.direction * ac;
  double determinant = ab.dot_product(pvec);

  if (std::abs(determinant) < epsilon)
    return Vector3();

  double inverse_determinant = 1. / determinant;

  Vector3 tvec = ray.position - a;
  double u = tvec.dot_product(pvec) * inverse_determinant;
  if (u < 0 || u > 1)
    return Vector3();

  Vector3 qvec = tvec * ab;
  double v = ray.direction.dot_product(qvec) * inverse_determinant;
  if (v < 0 || u + v > 1)
    return Vector3();

  double t = ac.dot_product(qvec) * inverse_determinant;

  if (t > epsilon)
    return ray.direction * t + ray.position;

  return Vector3();
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

bool Triangle::inside(const std::array<Vector3, 2> bounds) const
{
  return pos.inside(bounds) || b.inside(bounds) || c.inside(bounds);
}
