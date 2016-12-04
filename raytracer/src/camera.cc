#include "camera.hh"

std::istream& operator>>(std::istream& is, Camera& camera)
{
  return is >> camera.pos >> camera.u >> camera.v;
}
