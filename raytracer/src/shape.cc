#include "shape.hh"
#include "input.hh"

Shape::Shape(const Vector3& pos, const Attributes& attr, const Color& color)
      : pos(pos), attr(attr), color(color)
{}

std::ostream& operator<<(std::ostream& os, const Shape& shape)
{
  return shape.display(os);
}

Color& Shape::apply_ambiant_light(const Input& file, Color& out_color)
{
  return out_color = out_color + file.get_ambiant_light().color * color;
}
