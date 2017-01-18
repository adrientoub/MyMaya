#pragma once

#include "shape.hh"
#include "triangle.hh"

#include <array>

class Box: public Shape
{
public:
  virtual ~Box() = default;
  Box() = default;
  Box(const Vector3& pos, const Attributes& attr, const Color& color,
      double scale);
  virtual Vector3 intersect(const Ray& ray) const override;
  void calculate_bounds();

  friend std::istream& operator>>(std::istream& is, Box& box);
  friend std::ostream& operator<<(std::ostream& is, const Box& box);

protected:
  virtual Vector3 normal_vect(const Vector3& intersect) const override;
  virtual std::ostream& display(std::ostream& os) const override;

private:
  double scale;
  Vector3 bounds[2];
  std::array<Triangle, 12> triangles;
};

std::istream& operator>>(std::istream& is, Box& box);
std::ostream& operator<<(std::ostream& is, const Box& box);

Vector3 box_intersect(const std::array<Triangle, 12>& triangles, const Ray& ray);

template <typename T>
Vector3 find_closest_intersection(T& triangles, const Ray& ray);
