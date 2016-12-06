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
  Color& apply_point_lights(const Input& file, Color& r,
                            const Vector3& intersect, size_t ttl);
  Color& apply_ambiant_light(const Input& file, Color& r);
  Color& apply_directional_lights(const Input& file, Color& r,
                                  const Vector3& intersect);
  virtual Vector3 normal_vect(const Vector3& intersect) const = 0;
  virtual Vector3 normal_vect_point(const Vector3& intersect) const;

  virtual std::ostream& display(std::ostream& os) const = 0;
  friend std::ostream& operator<<(std::ostream& os, const Shape& shape);

protected:
  Vector3 pos;
  Attributes attr;
  Color color;
};

std::ostream& operator<<(std::ostream& os, const Shape& shape);
