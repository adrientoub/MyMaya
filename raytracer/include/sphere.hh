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

  friend std::istream& operator>>(std::istream& is, Sphere& sphere);
  friend std::ostream& operator<<(std::ostream& is, const Sphere& sphere);

protected:
  virtual Vector3 normal_vect(const Vector3& intersect) const override;
  virtual Vector3 normal_vect_point(const Vector3& intersect) const override;
  virtual std::ostream& display(std::ostream& os) const override;

private:
  double radius;
};

std::istream& operator>>(std::istream& is, Sphere& sphere);
std::ostream& operator<<(std::ostream& is, const Sphere& sphere);
