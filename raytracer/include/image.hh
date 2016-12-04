#pragma once

#include "color.hh"

#include <cstddef>
#include <vector>
#include <string>

class Image
{
public:
  Image(size_t width, size_t height);
  void save(const std::string& filename);

private:
  size_t width;
  size_t height;
  std::vector<std::vector<Color>> pixels;
};
