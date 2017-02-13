#include "vector3.hh"

#include <istream>

std::ostream& operator<<(std::ostream& os, const Vector3& vect)
{
  return os << '(' << vect.x_ << ", " << vect.y_ << ", " << vect.z_ << ')';
}

std::istream& operator>>(std::istream& is, Vector3& vector3)
{
  return is >> vector3.x_ >> vector3.y_ >> vector3.z_;
}

bool Vector3::inside(const std::array<Vector3, 2> bounds) const
{
  return x_ >= bounds[0].x_ && x_ <= bounds[1].x_ &&
         y_ >= bounds[0].y_ && y_ <= bounds[1].y_ &&
         z_ >= bounds[0].z_ && z_ <= bounds[1].z_;
}
