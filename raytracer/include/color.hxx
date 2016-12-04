#pragma once
#include "color.hh"

inline Color Color::operator+(const Color& c) const
{
  return Color(r + c.r, g + c.g, b + c.b);
}

inline bool Color::operator==(const Color& other) const
{
  return r == other.r && g == other.g && b == other.b;
}

inline bool Color::operator!=(const Color& other) const
{
  return !(*this == other);
}
