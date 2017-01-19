#pragma once
#include "shape.hh"

inline void Shape::sort_shape_double(std::vector<shape_double>& v)
{
  auto cmp = [](shape_double a, shape_double b) {
    return a.second < b.second;
  };
  std::sort(v.begin(), v.end(), cmp);
}

inline const Attributes& Shape::get_attributes() const
{
  return attr;
}

inline const Color& Shape::get_color() const
{
  return color;
}
