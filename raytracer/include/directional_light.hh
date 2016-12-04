#include "vector3.hh"
#include "color.hh"
#include "light.hh"

struct DirectionalLight: public Light
{
  DirectionalLight() = default;
  friend std::istream& operator>>(std::istream& is, DirectionalLight& value);
  friend std::ostream& operator<<(std::ostream& os,
                                  const DirectionalLight& value);

  Vector3 dir;
};

std::istream& operator>>(std::istream& is, DirectionalLight& value);
std::ostream& operator<<(std::ostream& os, const DirectionalLight& value);
