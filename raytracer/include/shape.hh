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
  virtual Color& apply_point_lights(const Input *file, Color& r,
                                    const Vector3& intersect, int ttl) = 0;
  virtual Color& apply_ambiant_light(const Input *file, Color& r) = 0;

protected:
  Vector3 pos;
  Attributes attr;
  Color color;
};
