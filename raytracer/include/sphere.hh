#pragma once

#include "shape.hh"

class Sphere: public Shape
{
public:
  virtual ~Sphere() = default;
  Sphere() = default;
  Sphere(const Vector3& pos, const Attributes& attr, const Color& color,
         double radius);
  virtual Vector3 intersect(const Ray& ray) override;

private:
  double radius;
};
