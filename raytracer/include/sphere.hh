#pragma once

#include "attributes.hh"
#include "color.hh"
#include "vector3.hh"
#include "shape.hh"

class Sphere: public Shape
{
public:
  Sphere() = default;
  Sphere(const Vector3& pos, const Attributes& attr, const Color& color,
         double radius);
private:
  double radius;
};
