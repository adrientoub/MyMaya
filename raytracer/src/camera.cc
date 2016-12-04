#include "camera.hh"

std::istream& operator>>(std::istream& is, Camera& camera)
{
  return is >> camera.pos >> camera.u >> camera.v;
}

std::ostream& operator<<(std::ostream& os, const Camera& value)
{
  return os << "Camera: pos=" << value.pos << " u=" << value.u << " v="
            << value.v;
}
