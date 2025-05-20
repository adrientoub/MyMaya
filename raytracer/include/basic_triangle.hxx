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
  // Compute triangle's bounding box
  double min_x = std::min({a.getX(), b.getX(), c.getX()});
  double min_y = std::min({a.getY(), b.getY(), c.getY()});
  double min_z = std::min({a.getZ(), b.getZ(), c.getZ()});
  double max_x = std::max({a.getX(), b.getX(), c.getX()});
  double max_y = std::max({a.getY(), b.getY(), c.getY()});
  double max_z = std::max({a.getZ(), b.getZ(), c.getZ()});

  // Check for AABB overlap
  return (min_x <= bounds[1].getX() && max_x >= bounds[0].getX() &&
          min_y <= bounds[1].getY() && max_y >= bounds[0].getY() &&
          min_z <= bounds[1].getZ() && max_z >= bounds[0].getZ());
}
