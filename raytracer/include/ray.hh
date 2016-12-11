#pragma once

class Input;
class Shape;

#include "vector3.hh"
#include "color.hh"

#include <memory>
#include <vector>

class Ray
{
public:
  Ray(const Vector3& direction, const Vector3& position);
  void cast(const Input& file, Color& color, size_t ttl) const;
  bool cast_shapes(const Input& file, std::shared_ptr<Shape>& shapes,
                   Vector3& intersect) const;

  Vector3 direction;
  Vector3 position;
};

extern const double epsilon;
