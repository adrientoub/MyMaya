#include "mesh.hh"
#include "box.hh"
#include "input.hh"

#include <iostream>
#include <iomanip>
#include <array>
#include <limits>

Mesh::Mesh(const Vector3& pos, const Attributes& attr, const Color& color,
          const std::vector<BasicTriangle>& triangles)
       : Shape(pos, attr, color), triangles(triangles)
{
  calculate_bounds();
}

void Mesh::calculate_bounds()
{
  if (triangles.size() > 0)
  {
    std::array<Vector3, 2> bounds {{
      Vector3(std::numeric_limits<double>::max(),
              std::numeric_limits<double>::max(),
              std::numeric_limits<double>::max()),
      Vector3(std::numeric_limits<double>::lowest(),
              std::numeric_limits<double>::lowest(),
              std::numeric_limits<double>::lowest())
    }};
    for (const BasicTriangle& triangle: triangles)
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
    octree = std::shared_ptr<Octree>(Octree::build_octree(triangles, bounds));
  }
}

Vector3 Mesh::intersect(const Ray& ray) const
{
  if (octree)
  {
    std::vector<const BasicTriangle*> t = octree->intersect(ray);
    auto pair = find_closest_intersection_ptr(t, ray);
    const_cast<std::map<Vector3, const BasicTriangle*>&>(intersect_to_triangle).insert(pair);
    return pair.first;
  }
  return Vector3();
}

std::istream& operator>>(std::istream& is, Mesh& mesh)
{
  is >> std::quoted(mesh.name) >> mesh.attr >> mesh.color;
  std::string field;

  while (is >> field)
  {
    if (field == "end")
      break;

    if (field == "triangle")
    {
      BasicTriangle triangle;
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
            << mesh.triangles.size() << " attr: " << mesh.attr << " color: "
            << mesh.color;
}

Vector3 Mesh::normal_vect(const Vector3& intersect) const
{
  auto triangle = intersect_to_triangle.find(intersect);
  if (triangle != intersect_to_triangle.end())
    return triangle->second->normal_vect();

  return pos - intersect;
}

std::ostream& Mesh::display(std::ostream& os) const
{
  return os << *this;
}
