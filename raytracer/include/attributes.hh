#pragma once

#include <istream>

struct Attributes
{
public:
  friend std::istream& operator>>(std::istream& is, Attributes& attr);
  friend std::ostream& operator<<(std::ostream& os, const Attributes& sphere);

  // Diffusion coeficient 0 < diff < 1
  double diff;
  // Reflection coeficient 0 < refl < 1
  double refl;
  // Specular coeficient 0 < spec < 1
  double spec;
  // Shininess, used for specular light > 0
  double shin;
  // Refraction coeficient (angle) > 0
  double refr;
  // Opacity 0 < opac < 1
  double opac;
};

std::istream& operator>>(std::istream& is, Attributes& attr);
std::ostream& operator<<(std::ostream& os, const Attributes& attr);
