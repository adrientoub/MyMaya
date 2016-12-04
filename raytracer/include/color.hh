#pragma once

#include <ostream>

class Color
{
public:
  Color() = default;
  Color(int red, int green, int blue);
  Color operator*(double f) const;
  friend std::ostream& operator<<(std::ostream& os, const Color& color);

private:
  double r;
  double g;
  double b;
};

std::ostream& operator<<(std::ostream& os, const Color& color);
