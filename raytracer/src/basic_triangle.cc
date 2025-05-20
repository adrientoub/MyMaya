#include "basic_triangle.hh"

std::istream& operator>>(std::istream& is, BasicTriangle& triangle)
{
  return is >> triangle.a >> triangle.b >> triangle.c;
}

std::ostream& operator<<(std::ostream& os, const BasicTriangle& triangle)
{
  return os << triangle.a << " " << triangle.b << " " << triangle.c;
}
