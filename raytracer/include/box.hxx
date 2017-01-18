#pragma once

#include "box.hh"

template <typename T>
Vector3 find_closest_intersection(T& triangles, const Ray& ray)
{
  Vector3 intersect;
  double min_dist = INFINITY;
  for (const Triangle& triangle: triangles)
  {
    Vector3 vect = triangle.intersect(ray);
    if (!vect)
      continue;
    double norm = (vect - ray.position).norm();

    if (min_dist > norm)
    {
      min_dist = norm;
      intersect = vect;
    }
  }
  return intersect;
}
