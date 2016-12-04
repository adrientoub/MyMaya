#pragma once
#include "color.hh"

inline Color Color::operator+(const Color& c) const
{
  return Color(r + c.r, g + c.g, b + c.b);
}
