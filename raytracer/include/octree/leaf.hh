#pragma once

#include "node.hh"

class Leaf: public Node
{
public:
  virtual ~Leaf() = default;
  virtual void intersect(const Ray& ray, std::vector<const Triangle*>& triangles) const;

private:
  friend class Octree;

  std::vector<const Triangle*> triangles;
};
