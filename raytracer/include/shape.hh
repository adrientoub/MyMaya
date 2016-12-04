#pragma once

#include "attributes.hh"
#include "color.hh"
#include "vector3.hh"
#include "ray.hh"

class Shape
{
public:
  virtual ~Shape() = default;
  Shape() = default;
  Shape(const Vector3& pos, const Attributes& attr, const Color& color);
  virtual Vector3 intersect(const Ray& ray) = 0;

protected:
  Vector3 pos;
  Attributes attr;
  Color color;
};
