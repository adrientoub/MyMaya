#include "point_light.hh"

std::istream& operator>>(std::istream& is, PointLight& value)
{
  return is >> value.pos >> value.color;
}

std::ostream& operator<<(std::ostream& os, const PointLight& value)
{
  return os << "PointLight: Pos=" << value.pos << " Color: " << value.color;
}
