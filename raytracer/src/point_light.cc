#include "point_light.hh"

std::istream& operator>>(std::istream& is, PointLight& value)
{
  return is >> value.pos >> value.color;
}
