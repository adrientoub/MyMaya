#include "triangle.hh"
#include "input.hh"

#include <iostream>
#include <iomanip>
#include <array>

Triangle::Triangle(const Vector3& a, const Vector3& b, const Vector3& c,
                   const Attributes& attr, const Color& color)
      : Shape(a, attr, color), triangle(a, b, c)
{}

Vector3 Triangle::normal_vect(const Vector3&) const
{
  return triangle.normal_vect();
}

std::array<Vector3, 3> Triangle::get_vertices() const
{
  return triangle.get_vertices();
}

// Use Möller-Trumbore intersection algorithm
Vector3 Triangle::intersect(const Ray& ray) const
{
  return triangle.intersect(ray);
}

std::istream& operator>>(std::istream& is, Triangle& triangle)
{
  return is >> std::quoted(triangle.name) >> triangle.triangle
            >> triangle.attr >> triangle.color;
}

std::ostream& operator<<(std::ostream& os, const Triangle& triangle)
{
  return os << "Triangle: " << triangle.name << " Pos: " << triangle.triangle
            << " " << triangle.attr << " Color: " << triangle.color;
}

std::ostream& Triangle::display(std::ostream& os) const
{
  return os << *this;
}

bool Triangle::inside(const std::array<Vector3, 2> bounds) const
{
  return triangle.inside(bounds);
}
