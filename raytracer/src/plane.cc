#include "plane.hh"
#include "input.hh"

#include <iostream>
#include <iomanip>

Plane::Plane(const Vector3& pos, const Attributes& attr, const Color& color,
             double d)
      : Shape(pos, attr, color), d(d)
{}

Vector3 Plane::intersect(const Ray& ray) const
{
  const Vector3 r = ray.direction.normalize();
  const Vector3 p = pos.normalize();
  const double p_cal = p.dot_product(ray.position) + d;
  const double r_cal = p.dot_product(r);
  if (r_cal != 0)
  {
    const double t0 = -p_cal / r_cal;

    if (0 < t0)
      return ray.position + r * t0;
  }
  return Vector3();
}

std::istream& operator>>(std::istream& is, Plane& plane)
{
  return is >> std::quoted(plane.name) >> plane.pos >> plane.d >> plane.attr
            >> plane.color;
}

std::ostream& operator<<(std::ostream& os, const Plane& plane)
{
  return os << "Plane: " << plane.name << " Pos: " << plane.pos << " "
            << plane.d << " " << plane.attr << " Color: " << plane.color;
}

Vector3 Plane::normal_vect(const Vector3&) const
{
  return pos;
}

std::ostream& Plane::display(std::ostream& os) const
{
  return os << *this;
}
