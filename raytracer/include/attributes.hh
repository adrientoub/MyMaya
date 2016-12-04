#pragma once

#include <istream>

struct Attributes
{
public:
  friend std::istream& operator>>(std::istream& is, Attributes& attr);
  friend std::ostream& operator<<(std::ostream& os, const Attributes& sphere);

  double diff;
  double refl;
  double spec;
  double shin;
  double refr;
  double opac;
};

std::istream& operator>>(std::istream& is, Attributes& attr);
std::ostream& operator<<(std::ostream& os, const Attributes& attr);
