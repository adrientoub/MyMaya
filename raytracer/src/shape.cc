#include "shape.hh"

Shape::Shape(const Vector3& pos, const Attributes& attr, const Color& color)
      : pos(pos), attr(attr), color(color)
{}

std::ostream& operator<<(std::ostream& os, const Shape& shape)
{
  return shape.display(os);
}
