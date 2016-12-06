#pragma once

#include "shape.hh"

class Plane: public Shape
{
public:
  virtual ~Plane() = default;
  Plane() = default;
  Plane(const Vector3& pos, const Attributes& attr, const Color& color,
        double d);
  virtual Vector3 intersect(const Ray& ray) override;
  virtual Vector3 normal_vect(const Vector3& intersect) const override;
  virtual std::ostream& display(std::ostream& os) const override;

  friend std::istream& operator>>(std::istream& is, Plane& sphere);
  friend std::ostream& operator<<(std::ostream& is, const Plane& sphere);

private:
  double d;
};

std::istream& operator>>(std::istream& is, Plane& sphere);
std::ostream& operator<<(std::ostream& is, const Plane& sphere);
