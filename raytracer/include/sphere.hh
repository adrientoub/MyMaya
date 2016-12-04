#pragma once

#include "attributes.hh"
#include "color.hh"
#include "vector3.hh"

struct Sphere
{
  double radius;
  Vector3 pos;
  Attributes attr;
  Color color;
};
