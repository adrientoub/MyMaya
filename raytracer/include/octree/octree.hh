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
  std::vector<const BasicTriangle*> intersect(const Ray& ray) const;
  template <typename T>
  static Octree* build_octree(const T& triangles,
                              const std::array<Vector3, 2>& bounds,
                              const size_t depth = 9);

  virtual void intersect(const Ray& ray, std::vector<const BasicTriangle*>& triangles) const override;

private:
  static const size_t optimal_by_node = 5;
  std::array<std::unique_ptr<Node>, 8> children;
};

std::vector<const BasicTriangle*> find_all_triangles(const std::vector<BasicTriangle>& triangles,
                                                const std::array<Vector3, 2>& bounds);

std::vector<const BasicTriangle*> find_all_triangles(const std::vector<const BasicTriangle*>& triangles,
                                                const std::array<Vector3, 2>& bounds);


#include "octree.hxx"
