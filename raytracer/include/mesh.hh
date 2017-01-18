#pragma once

#include "shape.hh"
#include "triangle.hh"

#include <vector>
#include <memory>

class Mesh: public Shape
{
public:
  virtual ~Mesh() = default;
  Mesh() = default;
  Mesh(const Vector3& pos, const Attributes& attr, const Color& color,
       const std::vector<Triangle>& triangles);
  virtual Vector3 intersect(const Ray& ray) const override;

  friend std::istream& operator>>(std::istream& is, Mesh& mesh);
  friend std::ostream& operator<<(std::ostream& is, const Mesh& mesh);

protected:
  virtual Vector3 normal_vect(const Vector3& intersect) const override;
  virtual std::ostream& display(std::ostream& os) const override;

private:
  void calculate_bounds();

  std::vector<Triangle> triangles;
  Vector3 bounds[2];
};

std::istream& operator>>(std::istream& is, Mesh& mesh);
std::ostream& operator<<(std::ostream& is, const Mesh& mesh);
