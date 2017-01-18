#include "box.hh"
#include "input.hh"

#include <iostream>
#include <iomanip>

Box::Box(const Vector3& pos, const Attributes& attr, const Color& color,
         double scale)
       : Shape(pos, attr, color), scale(scale)
{
  calculate_bounds();
}

void Box::calculate_bounds()
{
  Vector3 half_scale = Vector3(scale / 2, scale / 2, scale / 2);
  bounds[0] = pos - half_scale;
  bounds[1] = pos + half_scale;
  std::array<Vector3, 8> vertices {{
    bounds[0],
    {bounds[0].getX(), bounds[0].getY(), bounds[1].getZ()},
    {bounds[0].getX(), bounds[1].getY(), bounds[0].getZ()},
    {bounds[0].getX(), bounds[1].getY(), bounds[1].getZ()},
    {bounds[1].getX(), bounds[0].getY(), bounds[0].getZ()},
    {bounds[1].getX(), bounds[0].getY(), bounds[1].getZ()},
    {bounds[1].getX(), bounds[1].getY(), bounds[0].getZ()},
    bounds[1]
  }};
  // FIXME: front and back not displaying?
  triangles = {{
    { Triangle(vertices[0], vertices[2], vertices[4], attr, color) }, // front
    { Triangle(vertices[2], vertices[4], vertices[6], attr, color) },
    { Triangle(vertices[0], vertices[1], vertices[2], attr, color) }, // left
    { Triangle(vertices[1], vertices[2], vertices[3], attr, color) },
    { Triangle(vertices[0], vertices[1], vertices[4], attr, color) }, // bottom
    { Triangle(vertices[1], vertices[4], vertices[5], attr, color) },
    { Triangle(vertices[4], vertices[5], vertices[6], attr, color) }, // right
    { Triangle(vertices[5], vertices[6], vertices[7], attr, color) },
    { Triangle(vertices[1], vertices[3], vertices[7], attr, color) }, // back
    { Triangle(vertices[1], vertices[7], vertices[5], attr, color) },
    { Triangle(vertices[2], vertices[3], vertices[6], attr, color) }, // top
    { Triangle(vertices[3], vertices[6], vertices[7], attr, color) }
  }};
}

Vector3 Box::intersect(const Ray& ray) const
{
  return box_intersect(triangles, ray);
}

std::istream& operator>>(std::istream& is, Box& box)
{
  return is >> std::quoted(box.name) >> box.scale >> box.pos
            >> box.attr >> box.color;
}

std::ostream& operator<<(std::ostream& os, const Box& box)
{
  return os << "Box: " << box.name << " Scale: " << box.scale << " Pos: "
            << box.pos << " " << box.attr << " Color: " << box.color;
}

Vector3 Box::normal_vect(const Vector3& intersect) const
{
  // FIXME
  return intersect - pos;
}

std::ostream& Box::display(std::ostream& os) const
{
  return os << *this;
}

Vector3 box_intersect(const std::array<Triangle, 12>& triangles, const Ray& ray)
{
  return find_closest_intersection(triangles, ray);
}

template <typename T>
Vector3 find_closest_intersection(T& triangles, const Ray& ray)
{
  Vector3 intersect;
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
  return intersect;
}
