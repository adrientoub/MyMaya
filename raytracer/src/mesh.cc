#include "mesh.hh"
#include "box.hh"
#include "input.hh"

#include <iostream>
#include <iomanip>
#include <array>

Mesh::Mesh(const Vector3& pos, const Attributes& attr, const Color& color,
          const std::vector<Triangle>& triangles)
       : Shape(pos, attr, color), triangles(triangles)
{
  calculate_bounds();
}

void Mesh::calculate_bounds()
{
  if (triangles.size() > 0)
  {
    bounds[0] = Vector3(std::numeric_limits<double>::max(),
                        std::numeric_limits<double>::max(),
                        std::numeric_limits<double>::max());
    bounds[1] = Vector3(std::numeric_limits<double>::min(),
                        std::numeric_limits<double>::min(),
                        std::numeric_limits<double>::min());
    for (const Triangle& triangle: triangles)
    {
      std::array<Vector3, 3> vertices = triangle.get_vertices();
      for (Vector3 vertex: vertices)
      {
        bounds[0] = Vector3(std::min(vertex.getX(), bounds[0].getX()),
                            std::min(vertex.getY(), bounds[0].getY()),
                            std::min(vertex.getZ(), bounds[0].getZ()));
        bounds[1] = Vector3(std::max(vertex.getX(), bounds[1].getX()),
                            std::max(vertex.getY(), bounds[1].getY()),
                            std::max(vertex.getZ(), bounds[1].getZ()));
      }
    }
  }
}

Vector3 Mesh::intersect(const Ray& ray) const
{
  // FIXME: uncomment when box_intersect works correctly
  Vector3 intersect; // = box_intersect(bounds, ray);
  if (intersect)
  {
    double min_dist = INFINITY;
    for (const Triangle& triangle: triangles)
    {
      Vector3 vect = triangle.intersect(ray);
      if (!vect)
        continue;
      double norm = (vect - ray.position).norm();

      if (min_dist > norm)
      {
        min_dist = norm;
        intersect = vect;
      }
    }
    if (min_dist != INFINITY)
      return intersect;
  }
  return Vector3();
}

std::istream& operator>>(std::istream& is, Mesh& mesh)
{
  is >> std::quoted(mesh.name);
  std::string field;
  while (is >> field)
  {
    if (field == "end")
    {
      mesh.calculate_bounds();
      return is;
    }

    if (field == "triangle")
    {
      Triangle triangle;
      is >> triangle;
      mesh.triangles.push_back(triangle);
    }
  }
  mesh.calculate_bounds();
  return is;
}

std::ostream& operator<<(std::ostream& os, const Mesh& mesh)
{
  return os << "Mesh: " << mesh.name << ": triangle count: "
            << mesh.triangles.size();
}

Vector3 Mesh::normal_vect(const Vector3& intersect) const
{
  // FIXME
  return pos - intersect;
}

std::ostream& Mesh::display(std::ostream& os) const
{
  return os << *this;
}