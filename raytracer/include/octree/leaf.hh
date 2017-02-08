#pragma once

#include "node.hh"

class Leaf: public Node
{
  friend class Octree;
private:
  std::vector<Triangle*> triangles;
};
