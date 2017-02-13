#pragma once

#include <ostream>
#include <istream>

class Color
{
public:
  Color() = default;
  inline Color(int red, int green, int blue);
  inline Color(double red, double green, double blue);
  inline Color operator*(double f) const;
  inline Color operator*(const Color& c) const;
  inline Color operator+(const Color& c) const;
  inline Color operator+=(const Color& other);
  inline bool operator==(const Color& other) const;
  inline bool operator!=(const Color& other) const;
  friend std::ostream& operator<<(std::ostream& os, const Color& color);
  friend std::istream& operator>>(std::istream& is, Color& color);

private:
  double r = 0.;
  double g = 0.;
  double b = 0.;
};

inline std::ostream& operator<<(std::ostream& os, const Color& color);
std::istream& operator>>(std::istream& is, Color& color);

#include "color.hxx"
