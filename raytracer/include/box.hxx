#pragma once

#include "box.hh"

template <typename T>
std::pair<Vector3, const BasicTriangle*> find_closest_intersection(T& triangles, const Ray& ray)
{
  Vector3 intersect;
  const BasicTriangle* min_triangle;
  double min_dist = INFINITY;
  for (const BasicTriangle& triangle: triangles)
  {
    Vector3 vect = triangle.intersect(ray);
    if (!vect)
      continue;
    double norm = (vect - ray.position).norm();

    if (min_dist > norm)
    {
      min_dist = norm;
      intersect = vect;
      min_triangle = &triangle;
    }
  }
  return std::make_pair(intersect, min_triangle);
}

template <typename T>
std::pair<Vector3, const BasicTriangle*> find_closest_intersection_ptr(T& triangles, const Ray& ray)
{
  Vector3 intersect;
  const BasicTriangle* min_triangle;
  double min_dist = INFINITY;
  for (const BasicTriangle* triangle: triangles)
  {
    Vector3 vect = triangle->intersect(ray);
    if (!vect)
      continue;
    double norm = (vect - ray.position).norm();

    if (min_dist > norm)
    {
      min_dist = norm;
      intersect = vect;
      min_triangle = triangle;
    }
  }
  return std::make_pair(intersect, min_triangle);
}
