#pragma once

#include <array>

#include "vector3.hh"

class Node
{
public:
  virtual ~Node() = default;

protected:
  // can be recalculated
  std::array<Vector3, 2> bounds;
};
