#include "ray.hh"
#include "input.hh"

#include <cassert>

const double epsilon = 0.0001;

Ray::Ray(const Vector3& direction, const Vector3& position)
    : direction(direction), position(position)
{}

void Ray::cast(const Input& file, Color& color, size_t ttl) const
{
  if (ttl == 0)
    return;
  ttl--;

  std::vector<std::shared_ptr<Shape>> shapes;
  std::vector<Vector3> intersect;
  cast_shapes(file, shapes, intersect);
  assert(intersect.size() == shapes.size());

  double remaining_opac = 1.;
  for (size_t i = 0; i < shapes.size(); ++i)
  {
    Color new_color;
    shapes[i]->apply_point_lights(file, new_color, intersect[i], ttl);
    color = color + new_color * remaining_opac * shapes[i]->get_attributes().opac;
    remaining_opac *= (1. - shapes[i]->get_attributes().opac);
    if (remaining_opac < epsilon)
      break;
  }
}

void Ray::cast_shapes(const Input& file,
                      std::vector<std::shared_ptr<Shape>>& shapes,
                      std::vector<Vector3>& intersect) const
{
  using vect3_shape_double = std::tuple<Vector3, std::shared_ptr<Shape>, double>;

  std::vector<vect3_shape_double> intersect_pos;
  for (const std::shared_ptr<Shape>& shape: file.get_shapes())
  {
    Vector3 vect = shape->intersect(*this);
    if (vect == Vector3())
      continue;

    double norm = (vect - position).norm();
    intersect_pos.emplace_back(vect, shape, norm);
  }

  auto cmp = [](vect3_shape_double a, vect3_shape_double b) {
    return std::get<2>(a) < std::get<2>(b);
  };
  std::sort(intersect_pos.begin(), intersect_pos.end(), cmp);

  for (const auto& tuple: intersect_pos)
  {
    intersect.push_back(std::get<0>(tuple));
    shapes.push_back(std::get<1>(tuple));
  }
}
