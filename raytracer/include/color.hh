#pragma once

class Color
{
public:
  Color() = default;
  Color(int red, int green, int blue);
  Color operator*(double f) const;

private:
  double r;
  double g;
  double b;
};
