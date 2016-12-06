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
  virtual Color& apply_point_lights(const Input& file, Color& r,
                                    const Vector3& intersect, size_t ttl) override;
  virtual Color& apply_directional_lights(const Input& file, Color& r,
                                          const Vector3& intersect) override;
  virtual std::ostream& display(std::ostream& os) const override;
  Vector3 normal_vect() const;

  friend std::istream& operator>>(std::istream& is, Triangle& sphere);
  friend std::ostream& operator<<(std::ostream& is, const Triangle& sphere);

private:
  Vector3 b;
  Vector3 c;
};

std::istream& operator>>(std::istream& is, Triangle& sphere);
std::ostream& operator<<(std::ostream& is, const Triangle& sphere);
