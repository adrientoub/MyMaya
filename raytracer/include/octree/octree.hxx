#pragma once

#include "octree.hh"
#include "leaf.hh"

template <typename T>
Octree* Octree::build_octree(const T& triangles,
                             const std::array<Vector3, 2>& bounds,
                             const size_t depth)
{
  Octree* tree = new Octree();
  std::copy(bounds.begin(), bounds.end(), tree->bounds.begin());

  Vector3 mid = (bounds[1] - bounds[0]) / 2;

  for (size_t i = 0; i < tree->children.size(); ++i)
  {
    std::array<Vector3, 2> bnds;
    bnds[0] = Vector3(
      bounds[0].getX() + (i & 1) * mid.getX(),
      bounds[0].getY() + ((i & 2) >> 1) * mid.getY(),
      bounds[0].getZ() + ((i & 4) >> 2) * mid.getZ()
    );
    bnds[1] = bnds[0] + mid;
    std::vector<const Triangle*> part_triangles = find_all_triangles(triangles, bnds);

    if (part_triangles.empty())
      tree->children[i] = nullptr;
    else
    {
      if (depth == 0 || part_triangles.size() < optimal_by_node)
      {
        std::unique_ptr<Leaf> leaf = std::make_unique<Leaf>();
        leaf->triangles = part_triangles;
        leaf->bounds = bnds;
        tree->children[i] = std::move(leaf);
      }
      else
        tree->children[i] = std::unique_ptr<Octree>(build_octree(part_triangles, bnds, depth - 1));
    }
  }

  return tree;
}
