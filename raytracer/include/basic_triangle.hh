#pragma once

#include "vector3.hh"
#include "ray.hh"

#include <array>

class BasicTriangle
{
public:
  BasicTriangle() = default;
  inline BasicTriangle(const Vector3& a, const Vector3& b, const Vector3& c);
  inline Vector3 intersect(const Ray& ray) const;
  inline std::array<Vector3, 3> get_vertices() const;
  inline Vector3 normal_vect() const;
  inline bool inside(const std::array<Vector3, 2> bounds) const;

  friend std::istream& operator>>(std::istream& is, BasicTriangle& triangle);
  friend std::ostream& operator<<(std::ostream& os, const BasicTriangle& triangle);

private:
  Vector3 a;
  Vector3 b;
  Vector3 c;
};

std::istream& operator>>(std::istream& is, BasicTriangle& triangle);
std::ostream& operator<<(std::ostream& os, const BasicTriangle& triangle);

#include "basic_triangle.hxx"
