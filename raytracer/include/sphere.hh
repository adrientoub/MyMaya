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
  virtual Color& apply_point_lights(const Input *file, Color& r,
                                    const Vector3& intersect, int ttl) override;

  friend std::istream& operator>>(std::istream& is, Sphere& sphere);

private:
  double radius;
};

std::istream& operator>>(std::istream& is, Sphere& sphere);
