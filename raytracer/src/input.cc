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

  long half_width = width / 2;
  long half_height = height / 2;

  Vector3 w = u * v;
  double fov = (45 * M_PI / 180);
  double l = half_width / (tan(fov / 2));
  Vector3 c = camera.pos + w * l;

// #pragma omp parallel for
  for (long i = -half_height; i < half_height; ++i)
  {
    for (long j = -half_width; j < half_width; ++j)
    {
      Vector3 pos = u * j + v * i;
      pos = pos + c;
      Ray ray(pos - camera.pos, camera.pos);
      ray.cast(this, image.get(i + half_height, j + half_width), ttl);
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
      is >> *sphere;
      std::shared_ptr<Shape> ptr(sphere);
      input.shapes.push_back(ptr);
    }
    else if (field == "plight")
    {
      PointLight pl;
      is >> pl;
      input.point_lights.push_back(pl);
    }
    else if (field == "alight")
      is >> input.ambiant_light;
    else
      std::cerr << "Unknown field `" << field << '`' << std::endl;
  }
  return is;
}

std::ostream& operator<<(std::ostream& os, const Input& value)
{
  return os << "w: " << value.width << " h: " << value.height;
}

void Input::save() const
{
  image.save(filename);
}
