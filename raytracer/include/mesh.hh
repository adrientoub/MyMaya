#pragma once

#include "shape.hh"
#include "triangle.hh"
#include "octree/octree.hh"

#include <vector>
#include <memory>
#include <array>
#include <map>

class Mesh: public Shape
{
public:
  virtual ~Mesh() = default;
  Mesh() = default;
  Mesh(const Vector3& pos, const Attributes& attr, const Color& color,
       const std::vector<BasicTriangle>& triangles);
  virtual Vector3 intersect(const Ray& ray) const override;

  friend std::istream& operator>>(std::istream& is, Mesh& mesh);
  friend std::ostream& operator<<(std::ostream& is, const Mesh& mesh);

protected:
  virtual Vector3 normal_vect(const Vector3& intersect) const override;
  virtual std::ostream& display(std::ostream& os) const override;

private:
  void calculate_bounds();

  std::vector<BasicTriangle> triangles;
  std::map<Vector3, const BasicTriangle*> intersect_to_triangle;
  std::shared_ptr<Octree> octree;
};

std::istream& operator>>(std::istream& is, Mesh& mesh);
std::ostream& operator<<(std::ostream& is, const Mesh& mesh);
