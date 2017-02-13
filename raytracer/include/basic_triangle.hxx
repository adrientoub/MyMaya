#pragma once

#include "basic_triangle.hh"

BasicTriangle::BasicTriangle(const Vector3& a, const Vector3& b,
                             const Vector3& c)
              : a(a), b(b), c(c)
{}

Vector3 BasicTriangle::normal_vect() const
{
  Vector3 ab = b - a;
  Vector3 ac = c - a;
  return ab * ac;
}

std::array<Vector3, 3> BasicTriangle::get_vertices() const
{
  std::array<Vector3, 3> vertices {{ a, b, c }};
  return vertices;
}

// Use Möller-Trumbore intersection algorithm
Vector3 BasicTriangle::intersect(const Ray& ray) const
{
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

bool BasicTriangle::inside(const std::array<Vector3, 2> bounds) const
{
  return a.inside(bounds) || b.inside(bounds) || c.inside(bounds);
}
