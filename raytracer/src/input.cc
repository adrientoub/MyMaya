#include "input.hh"
#include "sphere.hh"

#include <iostream>
#include <cmath>

Input::Input(const std::string& filename)
      : filename(filename)
{}

void Input::calculate()
{
  Vector3 u = camera.u.normalize();
  Vector3 v = camera.v.normalize();

  Vector3 w = u * v;
  double fov = (45 * M_PI / 180);
  double l = (width / 2) / (tan(fov / 2));
  Vector3 c = camera.pos + w * l;

  long half_width = width / 2;
  long half_height = height / 2;

#pragma omp parallel for
  for (long i = -half_width; i < half_width; ++i)
  {
    for (long j = -half_height; j < half_height; ++j)
    {
      Vector3 pos = u * i + v * j;
      pos = pos + c;
      Ray ray(pos - camera.pos, camera.pos);
      ray.cast(this, image.get(i + width / 2, j + height / 2), ttl);
    }
  }
}

std::istream& operator>>(std::istream& is, Input& input)
{
  std::string field;
  while (is >> field)
  {
    if (field == "screen")
    {
      is >> input.width >> input.height;
      input.image = Image(input.width, input.height);
    }
    else if (field == "camera")
      is >> input.camera;
    else if (field == "sphere")
    {
      Sphere* sphere = new Sphere();
      std::shared_ptr<Shape> ptr(sphere);
      is >> *sphere;
    }
    else
      std::cerr << "Unknown field `" << field << '`' << std::endl;
  }
  return is;
}

void Input::save() const
{
  image.save(filename);
}
