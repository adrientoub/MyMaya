#pragma once

#include "vector3.hh"
#include "color.hh"

struct PointLight
{
  friend std::istream& operator>>(std::istream& is, PointLight& value);

  Vector3 pos;
  Color color;
};

std::istream& operator>>(std::istream& is, PointLight& value);
