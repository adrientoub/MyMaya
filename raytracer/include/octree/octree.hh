#pragma once

#include <vector>
#include <memory>
#include <array>

#include "vector3.hh"
#include "triangle.hh"
#include "octree/node.hh"

class Octree: public Node
{
public:
  virtual ~Octree() = default;

  static Octree* build_octree(const std::vector<Triangle>& triangles,
                              const std::array<Vector3, 2>& bounds,
                              const size_t depth = 5);

private:
  static const size_t optimal_by_node = 5;
  std::array<std::unique_ptr<Node>, 8> children;
};
