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
  virtual Color& apply_point_lights(const Input& file, Color& r,
                                    const Vector3& intersect, size_t ttl) = 0;
  Color& apply_ambiant_light(const Input& file, Color& r);
  virtual Color& apply_directional_lights(const Input& file, Color& r,
                                          const Vector3& intersect) = 0;
  virtual std::ostream& display(std::ostream& os) const = 0;
  friend std::ostream& operator<<(std::ostream& os, const Shape& shape);

protected:
  Vector3 pos;
  Attributes attr;
  Color color;
};

std::ostream& operator<<(std::ostream& os, const Shape& shape);
