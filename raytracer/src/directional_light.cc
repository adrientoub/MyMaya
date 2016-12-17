#include "directional_light.hh"
#include <iomanip>

std::istream& operator>>(std::istream& is, DirectionalLight& value)
{
  return is >> std::quoted(value.name) >> value.dir >> value.color;
}

std::ostream& operator<<(std::ostream& os, const DirectionalLight& value)
{
  return os << "DirectionalLight: " << value.name << " Dir=" << value.dir
            << " Color " << value.color;
}
