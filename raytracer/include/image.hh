#pragma once

#include "color.hh"

#include <cstddef>
#include <vector>
#include <string>

extern const bool anti_aliasing;

class Image
{
public:
  Image() = default;
  Image(size_t width, size_t height);
  void save(const std::string& filename) const;
  inline const Color& get(size_t x, size_t y) const;
  inline Color& get(size_t x, size_t y);
  inline std::vector<Color>& operator[](size_t x);
  Image reduce_mat() const;

private:
  size_t width;
  size_t height;
  std::vector<std::vector<Color>> pixels;
};

#include "image.hxx"
