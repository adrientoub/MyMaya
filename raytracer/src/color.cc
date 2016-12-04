#include "color.hh"
#include <algorithm>

std::ostream& operator<<(std::ostream& os, const Color& c)
{
  return os << std::min(255, std::max(0, static_cast<int>(c.r * 255))) << ' '
            << std::min(255, std::max(0, static_cast<int>(c.g * 255))) << ' '
            << std::min(255, std::max(0, static_cast<int>(c.b * 255)));
}

std::istream& operator>>(std::istream& is, Color& color)
{
  int red;
  int green;
  int blue;
  is >> red >> green >> blue;
  color = Color(red, green, blue);
  return is;
}
