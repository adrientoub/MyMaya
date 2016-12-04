#pragma once

#include "color.hh"
struct Light
{
  Light() = default;
  Light(const Color& c);

  Color color;
};
