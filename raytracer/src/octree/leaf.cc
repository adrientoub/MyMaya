#include "octree/leaf.hh"
#include "box.hh"

void Leaf::intersect(const Ray& ray,
                     std::vector<const BasicTriangle*>& triangles) const
{
  if (box_is_intersecting(ray, bounds))
    triangles.insert(triangles.end(), this->triangles.begin(), this->triangles.end());
}
