#include "point_light.hh"
#include <string>
#include <iomanip>

std::istream& operator>>(std::istream& is, PointLight& value)
{
  return is >> std::quoted(value.name) >> value.pos >> value.color;
}

std::ostream& operator<<(std::ostream& os, const PointLight& value)
{
  return os << "PointLight: " << value.name << " Pos=" << value.pos
            << " Color: " << value.color;
}
