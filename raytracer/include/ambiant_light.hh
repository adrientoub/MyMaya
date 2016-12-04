#pragma once

#include "light.hh"

struct AmbiantLight: public Light
{
  AmbiantLight() = default;
  AmbiantLight(const Color& c);

  friend std::istream& operator>>(std::istream& is, AmbiantLight& value);
  friend std::ostream& operator<<(std::ostream& os, const AmbiantLight& value);
};

std::istream& operator>>(std::istream& is, AmbiantLight& value);
std::ostream& operator<<(std::ostream& os, const AmbiantLight& value);
