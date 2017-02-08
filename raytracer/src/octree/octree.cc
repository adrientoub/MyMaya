#include "octree/octree.hh"
#include "octree/leaf.hh"

#include <algorithm>
#include <iterator>
#include <memory>


std::vector<Triangle*> find_all_triangles(const std::vector<Triangle>& triangles,
                                          const std::array<Vector3, 2>& bounds)
{
  std::vector<Triangle*> out;
  for (Triangle triangle: triangles)
    if (triangle.inside(bounds))
      out.push_back(&triangle);

  return out;
}

Octree Octree::build_octree(const std::vector<Triangle>& triangles,
                            const std::array<Vector3, 2>& bounds,
                            const size_t depth)
{
  Octree tree;
  std::copy(bounds.begin(), bounds.end(), tree.bounds.begin());

  Vector3 mid = (bounds[1] - bounds[0]) / 2;

  for (size_t i = 0; i < tree.children.size(); ++i)
  {
    std::array<Vector3, 2> bnds;
    bnds[0] = Vector3(
      bounds[0].getX() + (i & 1) * mid.getX(),
      bounds[0].getY() + (i & 2) * mid.getY(),
      bounds[0].getZ() + (i & 4) * mid.getZ()
    );
    bnds[1] = bnds[0] + mid;
    std::vector<Triangle*> part_triangles = find_all_triangles(triangles, bnds);

    if (part_triangles.size() == 0)
      tree.children[i] = nullptr;
    else
    {
      if (depth == 0 || part_triangles.size() < optimal_by_node)
      {
        std::unique_ptr<Leaf> leaf = std::make_unique<Leaf>();
        leaf->triangles = part_triangles;
        leaf->bounds = bnds;
        tree.children[i] = std::move(leaf);
      }
      else
        tree.children[i] = std::make_unique<Octree>(build_octree(triangles, bnds, depth - 1));
    }
  }

  return tree;
}
