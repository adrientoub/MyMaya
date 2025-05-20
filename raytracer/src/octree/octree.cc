#include "octree/octree.hh"
#include "box.hh"

#include <algorithm>
#include <iterator>
#include <memory>

std::vector<const BasicTriangle*> find_all_triangles(const std::vector<BasicTriangle>& triangles,
                                                const std::array<Vector3, 2>& bounds)
{
  std::vector<const BasicTriangle*> out;
  for (const BasicTriangle& triangle: triangles)
    if (triangle.inside(bounds))
      out.push_back(&triangle);

  return out;
}

std::vector<const BasicTriangle*> find_all_triangles(const std::vector<const BasicTriangle*>& triangles,
                                                const std::array<Vector3, 2>& bounds)
{
  std::vector<const BasicTriangle*> out;
  for (const BasicTriangle* triangle: triangles)
    if (triangle->inside(bounds))
      out.push_back(triangle);

  return out;
}

std::vector<const BasicTriangle*> Octree::intersect(const Ray& ray) const
{
  std::vector<const BasicTriangle*> triangles;
  intersect(ray, triangles);
  std::sort(triangles.begin(), triangles.end());
  triangles.erase(std::unique(triangles.begin(), triangles.end()), triangles.end());
  return triangles;
}

void Octree::intersect(const Ray& ray,
                       std::vector<const BasicTriangle*>& triangles) const
{
  if (!box_is_intersecting(ray, bounds))
    return;

  for (size_t i = 0; i < children.size(); ++i)
  {
    if (children[i])
      children[i]->intersect(ray, triangles);
  }
}
