#include "color.hh"
#include <algorithm>

Color::Color(int red, int green, int blue)
      : r(red / 255.), g(green / 255.), b(blue / 255.)
{}

Color Color::operator*(double f) const
{
  return Color(std::max(0., std::min(1., r * f)),
               std::max(0., std::min(1., g * f)),
               std::max(0., std::min(1., b * f)));
}
