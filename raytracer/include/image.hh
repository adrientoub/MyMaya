#pragma once

#include "color.hh"

#include <cstddef>
#include <vector>
#include <string>

class Image
{
public:
  Image() = default;
  Image(size_t width, size_t height);
  void save(const std::string& filename) const;
  inline const Color& get(size_t x, size_t y) const;
  inline Color& get(size_t x, size_t y);

private:
  size_t width;
  size_t height;
  std::vector<std::vector<Color>> pixels;
};

#include "image.hxx"
