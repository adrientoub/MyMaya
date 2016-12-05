#include "input.hh"
#include "sphere.hh"
#include "plane.hh"

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
      ray.cast(*this, image.get(i + half_height, j + half_width), ttl);
    }
  }
}

void Input::parse_screen(std::istream& is)
{
  is >> width >> height;
  image = Image(width, height);
}

void Input::parse_sphere(std::istream& is)
{
  Sphere* sphere = new Sphere();
  is >> *sphere;
  std::shared_ptr<Shape> ptr(sphere);
  shapes.push_back(ptr);
}

void Input::parse_plane(std::istream& is)
{
  Plane* plane = new Plane();
  is >> *plane;
  std::shared_ptr<Shape> ptr(plane);
  shapes.push_back(ptr);
}

void Input::parse_directional_light(std::istream& is)
{
  DirectionalLight dl;
  is >> dl;
  directional_lights.push_back(dl);
}

void Input::parse_point_light(std::istream& is)
{
  PointLight pl;
  is >> pl;
  point_lights.push_back(pl);
}

std::istream& operator>>(std::istream& is, Input& input)
{
  std::string field;
  while (is >> field)
  {
    if (field == "screen")
      input.parse_screen(is);
    else if (field == "camera")
      is >> input.camera;
    else if (field == "sphere")
      input.parse_sphere(is);
    else if (field == "plane")
      input.parse_plane(is);
    else if (field == "plight")
      input.parse_point_light(is);
    else if (field == "dlight")
      input.parse_directional_light(is);
    else if (field == "alight")
      is >> input.ambiant_light;
    else
      std::cerr << "Unknown field `" << field << '`' << std::endl;
  }
  return is;
}

std::ostream& operator<<(std::ostream& os, const Input& value)
{
  os << "w: " << value.width << " h: " << value.height << std::endl;
  os << value.camera << std::endl;
  for (const auto& shape: value.shapes)
    os << *shape << std::endl;
  for (const auto& point_light: value.point_lights)
    os << point_light << std::endl;
  for (const auto& directional_light: value.directional_lights)
    os << directional_light << std::endl;
  os << value.ambiant_light;
  return os;
}

void Input::save() const
{
  image.save(filename);
}
