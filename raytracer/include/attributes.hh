#pragma once

#include <istream>

struct Attributes
{
public:
  friend std::istream& operator>>(std::istream& is, Attributes& attr);

  double diff;
  double refl;
  double spec;
  double shin;
  double refr;
  double opac;
};

std::istream& operator>>(std::istream& is, Attributes& attr);
