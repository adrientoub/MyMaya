#pragma once

#include "vector3.hh"
#include "color.hh"
#include "light.hh"

struct PointLight: public Light
{
  PointLight() = default;
  friend std::istream& operator>>(std::istream& is, PointLight& value);

  Vector3 pos;
};

std::istream& operator>>(std::istream& is, PointLight& value);
