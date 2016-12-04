#include "vector3.hh"

#include <istream>

bool Vector3::operator==(const Vector3& other) const
{
  return x_ == other.x_ && y_ == other.y_ && z_ == other.z_;
}

Vector3::Vector3(double x, double y, double z)
        : x_(x), y_(y), z_(z)
{}

std::ostream& operator<<(std::ostream& os, const Vector3& vect)
{
  return os << '(' << vect.x_ << ", " << vect.y_ << ", " << vect.z_ << ')';
}

std::istream& operator>>(std::istream& is, Vector3& vector3)
{
  return is >> vector3.x_ >> vector3.y_ >> vector3.z_;
}
