#pragma once
#include "color.hh"
#include <algorithm>

inline Color::Color(int red, int green, int blue)
      : r(red / 255.), g(green / 255.), b(blue / 255.)
{}

inline Color::Color(double red, double green, double blue)
      : r(red), g(green), b(blue)
{}

inline Color Color::operator+(const Color& c) const
{
  return Color(r + c.r, g + c.g, b + c.b);
}

inline Color Color::operator+=(const Color& other)
{
  r += other.r;
  g += other.g;
  b += other.b;
  return *this;
}

inline bool Color::operator==(const Color& other) const
{
  return r == other.r && g == other.g && b == other.b;
}

inline bool Color::operator!=(const Color& other) const
{
  return !(*this == other);
}

inline Color Color::operator*(double f) const
{
  return Color(std::max(0., std::min(1., r * f)),
               std::max(0., std::min(1., g * f)),
               std::max(0., std::min(1., b * f)));
}

inline Color Color::operator*(const Color& c) const
{
  return Color(std::max(0., std::min(1., r * c.r)),
               std::max(0., std::min(1., g * c.g)),
               std::max(0., std::min(1.,  b * c.b)));
}
