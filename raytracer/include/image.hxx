#include "image.hh"

inline const Color& Image::get(size_t x, size_t y) const
{
  return pixels[x][y];
}

inline Color& Image::get(size_t x, size_t y)
{
  return pixels[x][y];
}
