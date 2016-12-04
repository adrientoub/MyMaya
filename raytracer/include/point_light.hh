#pragma once

#include "vector3.hh"
#include "color.hh"
#include "light.hh"

struct PointLight: public Light
{
  PointLight() = default;
  friend std::istream& operator>>(std::istream& is, PointLight& value);
  friend std::ostream& operator<<(std::ostream& os, const PointLight& value);

  Vector3 pos;
};

std::istream& operator>>(std::istream& is, PointLight& value);
std::ostream& operator<<(std::ostream& os, const PointLight& value);
