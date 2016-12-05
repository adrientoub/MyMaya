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
  virtual Color& apply_point_lights(const Input& file, Color& r,
                                    const Vector3& intersect, int ttl) override;
  virtual Color& apply_ambiant_light(const Input& file, Color& r) override;
  virtual Color& apply_directional_lights(const Input& file, Color& r,
                                          const Vector3& intersect) override;
  virtual std::ostream& display(std::ostream& os) const override;

  friend std::istream& operator>>(std::istream& is, Plane& sphere);
  friend std::ostream& operator<<(std::ostream& is, const Plane& sphere);

private:
  double d;
};

std::istream& operator>>(std::istream& is, Plane& sphere);
std::ostream& operator<<(std::ostream& is, const Plane& sphere);
