#pragma once

#include "node.hh"

class Leaf: public Node
{
public:
  virtual ~Leaf() = default;
  Leaf(const std::vector<const BasicTriangle*>& triangles,
       const std::array<Vector3, 2>& bounds);
  virtual void intersect(const Ray& ray, std::vector<const BasicTriangle*>& triangles) const;

private:
  friend class Octree;

  std::vector<const BasicTriangle*> triangles;
};
