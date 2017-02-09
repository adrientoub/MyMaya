#include "octree/octree.hh"
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

std::vector<const Triangle*> find_all_triangles(const std::vector<const Triangle*>& triangles,
                                                const std::array<Vector3, 2>& bounds)
{
  std::vector<const Triangle*> out;
  for (const Triangle* triangle: triangles)
    if (triangle->inside(bounds))
      out.push_back(triangle);

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
