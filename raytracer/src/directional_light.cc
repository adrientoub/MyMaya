#include "directional_light.hh"

std::istream& operator>>(std::istream& is, DirectionalLight& value)
{
  return is >> value.dir >> value.color;
}

std::ostream& operator<<(std::ostream& os, const DirectionalLight& value)
{
  return os << "DirectionalLight: Dir=" << value.dir << " Color "
            << value.color;
}
