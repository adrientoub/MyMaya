#include "octree/octree.hh"
#include "octree/leaf.hh"
#include "box.hh"

#include <algorithm>
#include <iterator>
#include <memory>

std::vector<const Triangle*> find_all_triangles(const std::vector<Triangle>& triangles,
                                                const std::array<Vector3, 2>& bounds)
{
  std::vector<const Triangle*> out;
  for (const Triangle& triangle: triangles)
    if (triangle.inside(bounds))
      out.push_back(&triangle);

  return out;
}

std::vector<const Triangle*> Octree::intersect(const Ray& ray) const
{
  std::vector<const Triangle*> triangles;
  intersect(ray, triangles);
  std::sort(triangles.begin(), triangles.end());
  triangles.erase(std::unique(triangles.begin(), triangles.end()), triangles.end());
  return triangles;
}

void Octree::intersect(const Ray& ray,
                       std::vector<const Triangle*>& triangles) const
{
  if (!box_is_intersecting(ray, bounds))
    return;

  for (size_t i = 0; i < children.size(); ++i)
  {
    if (children[i])
      children[i]->intersect(ray, triangles);
  }
}

Octree* Octree::build_octree(const std::vector<Triangle>& triangles,
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
        tree->children[i] = std::unique_ptr<Octree>(build_octree(triangles, bnds, depth - 1));
    }
  }

  return tree;
}
