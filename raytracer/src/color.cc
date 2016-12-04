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

Color Color::operator*(const Color& c) const
{
  return Color(r * c.r, g * c.g, b * c.b);
}

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
