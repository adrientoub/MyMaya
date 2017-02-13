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
  std::array<Vector3, 2> bounds {{
    pos - half_scale,
    pos + half_scale
  }};
  calculate_box_triangles(bounds, triangles, attr, color);
}

Vector3 Box::intersect(const Ray& ray) const
{
  std::pair<Vector3, const Triangle*> pair = find_closest_intersection(triangles, ray);
  const_cast<std::map<Vector3, const Triangle*>&>(intersect_to_triangle).insert(pair);
  return pair.first;
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
  auto triangle = intersect_to_triangle.find(intersect);
  if (triangle != intersect_to_triangle.end())
    return triangle->second->normal_vect(intersect);

  return intersect - pos;
}

std::ostream& Box::display(std::ostream& os) const
{
  return os << *this;
}

Vector3 box_intersect(const std::array<Triangle, 12>& triangles, const Ray& ray)
{
  auto pair = find_closest_intersection(triangles, ray);
  return pair.first;
}

void calculate_box_triangles(const std::array<Vector3, 2>& bounds, std::array<Triangle, 12>& triangles, const Attributes& attr, const Color& color)
{
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

bool box_is_intersecting(const Ray& ray, const std::array<Vector3, 2>& bounds)
{
  Vector3 inv_direction = 1 / ray.direction;
  int sign[] = {
    inv_direction.getX() < 0,
    inv_direction.getY() < 0,
    inv_direction.getZ() < 0
  };

  double tmin = (bounds[sign[0]].getX() - ray.position.getX()) * inv_direction.getX();
  double tmax = (bounds[1 - sign[0]].getX() - ray.position.getX()) * inv_direction.getX();
  double tymin = (bounds[sign[1]].getY() - ray.position.getY()) * inv_direction.getY();
  double tymax = (bounds[1 - sign[1]].getY() - ray.position.getY()) * inv_direction.getY();
  if ((tmin > tymax) || (tymin > tmax))
    return false;
  if (tymin > tmin)
    tmin = tymin;
  if (tymax < tmax)
    tmax = tymax;
  double tzmin = (bounds[sign[2]].getZ() - ray.position.getZ()) * inv_direction.getZ();
  double tzmax = (bounds[1 - sign[2]].getZ() - ray.position.getZ()) * inv_direction.getZ();
  if ((tmin > tzmax) || (tzmin > tmax))
    return false;
  if (tzmin > tmin)
    tmin = tzmin;
  if (tzmax < tmax)
    tmax = tzmax;
  return true;
}
