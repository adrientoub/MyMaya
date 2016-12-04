#pragma once

#include <vector>

#include "camera.hh"
#include "image.hh"
#include "shape.hh"
#include "point_light.hh"

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

  friend std::istream& operator>>(std::istream& is, Input& value);

private:
  size_t width;
  size_t height;
  size_t ttl = 3;

  Camera camera;

  const std::string filename;

  Image image;

  std::vector<std::shared_ptr<Shape>> shapes;

  std::vector<PointLight> point_lights;
  // struct dlight_list *dlights;
  // struct alight *alight;
};

std::istream& operator>>(std::istream& is, Input& value);
