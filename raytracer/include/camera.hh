#pragma once

#include "vector3.hh"
#include <istream>

class Camera
{
public:
  friend std::istream& operator>>(std::istream& is, Camera& camera);
  friend std::ostream& operator<<(std::ostream& os, const Camera& value);

  Vector3 pos;
  Vector3 u;
  Vector3 v;
};

std::istream& operator>>(std::istream& is, Camera& camera);
std::ostream& operator<<(std::ostream& os, const Camera& value);
