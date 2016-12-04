#include "ambiant_light.hh"

AmbiantLight::AmbiantLight(const Color& c)
             : Light(c)
{}

std::istream& operator>>(std::istream& is, AmbiantLight& value)
{
  return is >> value.color;
}

std::ostream& operator<<(std::ostream& os, const AmbiantLight& value)
{
  return os << "AmbiantLight: " << value.color;
}
