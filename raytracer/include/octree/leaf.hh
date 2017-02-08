#pragma once

#include "node.hh"

class Leaf: public Node
{
public:
  virtual ~Leaf() = default;

private:
  friend class Octree;

  std::vector<Triangle*> triangles;
};
