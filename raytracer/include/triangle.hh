#pragma once

#include "shape.hh"
#include "basic_triangle.hh"

class Triangle: public Shape
{
public:
  virtual ~Triangle() = default;
  Triangle() = default;
  Triangle(const Vector3& a, const Vector3& b, const Vector3& c,
           const Attributes& attr, const Color& color);
  virtual Vector3 intersect(const Ray& ray) const override;
  std::array<Vector3, 3> get_vertices() const;
  virtual Vector3 normal_vect(const Vector3& intersect) const override;
  bool inside(const std::array<Vector3, 2> bounds) const;

  friend std::istream& operator>>(std::istream& is, Triangle& triangle);
  friend std::ostream& operator<<(std::ostream& is, const Triangle& triangle);

protected:
  virtual std::ostream& display(std::ostream& os) const override;

private:
  BasicTriangle triangle;
};

std::istream& operator>>(std::istream& is, Triangle& sphere);
std::ostream& operator<<(std::ostream& is, const Triangle& sphere);
