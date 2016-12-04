#pragma once

#include "vector3.hh"
#include <istream>

class Camera
{
public:
  friend std::istream& operator>>(std::istream& is, Camera& camera);

  Vector3 pos;
  Vector3 u;
  Vector3 v;
};

std::istream& operator>>(std::istream& is, Camera& camera);
