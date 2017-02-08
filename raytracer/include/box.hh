#pragma once

#include "shape.hh"
#include "triangle.hh"

#include <array>
#include <utility>
#include <map>

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
  std::array<Vector3, 2> bounds;
  std::array<Triangle, 12> triangles;

  std::map<Vector3, const Triangle*> intersect_to_triangle;
};

std::istream& operator>>(std::istream& is, Box& box);
std::ostream& operator<<(std::ostream& is, const Box& box);

Vector3 box_intersect(const std::array<Triangle, 12>& triangles, const Ray& ray);
bool box_is_intersecting(const Ray& ray, const std::array<Vector3, 2>& bounds);

void calculate_box_triangles(const std::array<Vector3, 2>& bounds,
                             std::array<Triangle, 12>& triangles,
                             const Attributes& attr, const Color& color);

template <typename T>
std::pair<Vector3, const Triangle*> find_closest_intersection(T& triangles, const Ray& ray);

#include "box.hxx"
