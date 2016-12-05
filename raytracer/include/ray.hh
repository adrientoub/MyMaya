#pragma once

class Input;
class Shape;

#include <memory>

#include "vector3.hh"
#include "color.hh"

class Ray
{
public:
  Ray(const Vector3& direction, const Vector3& position);
  void cast(const Input& file, Color& color, size_t ttl) const;
  double cast_shapes(const Input& file, std::shared_ptr<Shape>* min_shape,
                     Vector3& intersect) const;

  Vector3 direction;
  Vector3 position;
};
