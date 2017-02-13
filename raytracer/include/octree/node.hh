#pragma once

#include <array>
#include <vector>

#include "vector3.hh"
#include "ray.hh"
#include "triangle.hh"

class Node
{
public:
  virtual ~Node() = default;
  virtual void intersect(const Ray& ray, std::vector<const BasicTriangle*>& triangles) const = 0;

protected:
  // can be recalculated
  std::array<Vector3, 2> bounds;
};
