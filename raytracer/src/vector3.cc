#include "vector3.hh"

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
