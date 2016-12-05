#pragma once

#include <vector>
#include <memory>

#include "camera.hh"
#include "image.hh"
#include "shape.hh"
#include "point_light.hh"
#include "ambiant_light.hh"
#include "directional_light.hh"

#ifndef M_PI
# define M_PI 3.14159265359
#endif

class Input
{
public:
  Input(const std::string& filename);
  void save() const;
  void calculate();
  inline std::vector<std::shared_ptr<Shape>> get_shapes()
  {
    return shapes;
  }
  inline const std::vector<std::shared_ptr<Shape>> get_shapes() const
  {
    return shapes;
  }
  inline std::vector<PointLight> get_point_lights()
  {
    return point_lights;
  }
  inline const std::vector<PointLight> get_point_lights() const
  {
    return point_lights;
  }
  inline std::vector<DirectionalLight> get_directional_lights()
  {
    return directional_lights;
  }
  inline const std::vector<DirectionalLight> get_directional_lights() const
  {
    return directional_lights;
  }
  inline AmbiantLight get_ambiant_light()
  {
    return ambiant_light;
  }
  inline const AmbiantLight get_ambiant_light() const
  {
    return ambiant_light;
  }

  friend std::istream& operator>>(std::istream& is, Input& value);
  friend std::ostream& operator<<(std::ostream& os, const Input& value);

private:
  void parse_screen(std::istream& is);
  void parse_sphere(std::istream& is);
  void parse_directional_light(std::istream& is);
  void parse_point_light(std::istream& is);

  size_t width;
  size_t height;
  size_t ttl = 3;

  Camera camera;

  const std::string filename;

  Image image;

  std::vector<std::shared_ptr<Shape>> shapes;

  std::vector<PointLight> point_lights;
  std::vector<DirectionalLight> directional_lights;
  AmbiantLight ambiant_light;
};

std::istream& operator>>(std::istream& is, Input& value);
std::ostream& operator<<(std::ostream& is, const Input& value);
