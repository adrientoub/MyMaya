#include "image.hh"

#include <fstream>
#include <iostream>

Image::Image(size_t width, size_t height)
      : width(width)
      , height(height)
      , pixels(height)
{
  for (size_t i = 0; i < height; i++)
    pixels[i] = std::vector<Color>(width);
}

void Image::save(const std::string& filename)
{
  std::ofstream file(filename);
  if (!file)
  {
    std::cerr << "Impossible to open `" << filename << "` for writing." << std::endl;
    return;
  }

  file << "P3" << std::endl
       << width << ' ' << height << std::endl
       << 255 << std::endl;
  for (const std::vector<Color>& row: pixels)
  {
    for (size_t i = 0; i < width - 1; i++)
      file << row[i] << ' ';
    file << row[width - 1] << std::endl;
  }
}
