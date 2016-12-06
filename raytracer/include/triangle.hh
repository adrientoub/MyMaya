#pragma once

#include "shape.hh"

class Triangle: public Shape
{
public:
  virtual ~Triangle() = default;
  Triangle() = default;
  Triangle(const Vector3& a, const Vector3& b, const Vector3& c,
           const Attributes& attr, const Color& color);
  virtual Vector3 intersect(const Ray& ray) override;
  virtual Vector3 normal_vect(const Vector3& intersect) const override;
  virtual std::ostream& display(std::ostream& os) const override;

  friend std::istream& operator>>(std::istream& is, Triangle& sphere);
  friend std::ostream& operator<<(std::ostream& is, const Triangle& sphere);

private:
  Vector3 b;
  Vector3 c;
};

std::istream& operator>>(std::istream& is, Triangle& sphere);
std::ostream& operator<<(std::ostream& is, const Triangle& sphere);
