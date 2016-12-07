#include "image.hh"

#include <fstream>
#include <iostream>

const bool anti_aliasing = false;

Image::Image(size_t width, size_t height)
      : width(width)
      , height(height)
      , pixels(height)
{
  for (size_t i = 0; i < height; i++)
    pixels[i] = std::vector<Color>(width);
}

Color aver(const Color& c1, const Color& c2, const Color& c3,
           const Color& c4)
{
  return (c1 + c2 + c3 + c4) * 0.25;
}

Image Image::reduce_mat() const
{
  Image image(width / 2, height / 2);
  for (size_t i = 0; i < image.height; ++i)
    for (size_t j = 0; j < image.width; ++j)
      image[i][j] = aver(get(i * 2, j * 2), get(i * 2, j * 2 + 1),
                         get(i * 2 + 1, j * 2), get(i * 2 + 1, j * 2 + 1));
  return image;
}


void Image::save(const std::string& filename) const
{
  std::ofstream file(filename);
  if (!file)
  {
    std::cerr << "Impossible to open `" << filename << "` for writing."
              << std::endl;
    return;
  }

  Image img;
  if (anti_aliasing)
    img = reduce_mat();
  else
    img = *this;

  file << "P3" << std::endl
       << img.width << ' ' << img.height << std::endl
       << 255 << std::endl;
  for (const std::vector<Color>& row: img.pixels)
  {
    for (size_t i = 0; i < img.width - 1; i++)
      file << row[i] << ' ';
    file << row[img.width - 1] << std::endl;
  }
}
