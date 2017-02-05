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
