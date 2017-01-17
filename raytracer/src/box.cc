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
}

Vector3 Box::intersect(const Ray& ray)
{
  return box_intersect(bounds, ray);
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

Vector3 Box::normal_vect_point(const Vector3& intersect) const
{
  return intersect - pos;
}

Vector3 Box::normal_vect(const Vector3& intersect) const
{
  return pos - intersect;
}

std::ostream& Box::display(std::ostream& os) const
{
  return os << *this;
}

Vector3 box_intersect(const Vector3* bounds, const Ray& ray)
{
  Vector3 invdir = 1 / ray.direction.normalize();

  float tmin = (bounds[ray.direction.getX() < 0].getX() - ray.position.getX()) * invdir.getX();
  float tmax = (bounds[1 - ray.direction.getX() < 0].getX() - ray.position.getX()) * invdir.getX();
  float tymin = (bounds[ray.direction.getY() < 0].getY() - ray.position.getY()) * invdir.getY();
  float tymax = (bounds[1 - ray.direction.getY() < 0].getY() - ray.position.getY()) * invdir.getY();

  if ((tmin > tymax) || (tymin > tmax))
    return Vector3();

  tmin = std::max(tymin, tmin);
  tmax = std::min(tymax, tmax);

  float tzmin = (bounds[ray.direction.getZ() < 0].getZ() - ray.position.getZ()) * invdir.getZ();
  float tzmax = (bounds[1 - ray.direction.getZ() < 0].getZ() - ray.position.getZ()) * invdir.getZ();

  if ((tmin > tzmax) || (tzmin > tmax))
    return Vector3();

  tmin = std::max(tmin, tzmin);
  tmax = std::min(tzmax, tmax);

  double t = tmin;

  if (t < 0)
  {
    t = tmax;
    if (t < 0)
      return Vector3();
  }

  return ray.position + ray.direction * t;
}
