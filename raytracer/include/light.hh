#pragma once

#include "color.hh"
struct Light
{
  Light() = default;
  Light(const Color& c);

  std::string name;
  Color color;
};
