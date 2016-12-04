#pragma once

#include <ostream>
#include <istream>

class Color
{
public:
  Color() = default;
  Color(int red, int green, int blue);
  Color operator*(double f) const;
  Color operator*(const Color& c) const;
  inline Color operator+(const Color& c) const;
  friend std::ostream& operator<<(std::ostream& os, const Color& color);
  friend std::istream& operator>>(std::istream& is, Color& color);

private:
  double r;
  double g;
  double b;
};

std::ostream& operator<<(std::ostream& os, const Color& color);
std::istream& operator>>(std::istream& is, Color& color);

#include "color.hxx"
