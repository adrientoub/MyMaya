#include "color.hh"
#include <algorithm>

std::istream& operator>>(std::istream& is, Color& color)
{
  int red;
  int green;
  int blue;
  is >> red >> green >> blue;
  color = Color(red, green, blue);
  return is;
}
