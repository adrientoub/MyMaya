#include "ray.hh"
#include "input.hh"

Ray::Ray(const Vector3& direction, const Vector3& position)
    : direction(direction), position(position)
{}

Color Ray::cast(const Input* file, Color& color, int ttl) const
{
  if (ttl == 0)
    return color;
  ttl--;

  std::shared_ptr<Shape> min_shape = nullptr;
  Vector3 intersect;
  double min_dist = cast_shapes(file, &min_shape, intersect);

  Color c;
  if (min_dist != INFINITY)
    return min_shape->apply_point_lights(file, c, intersect, ttl);
  return c;
}

double Ray::cast_shapes(const Input* file,
                        std::shared_ptr<Shape>* min_shape,
                        Vector3& v) const
{
  double min_dist = INFINITY;
  for (const std::shared_ptr<Shape>& shape: file->get_shapes())
  {
    Vector3 vect = shape->intersect(*this);
    if (vect == Vector3())
      continue;
    double norm = vect.norm();
    if (norm < 0)
      continue;
    min_dist = std::min(norm, min_dist);
    if (min_dist == norm)
    {
      *min_shape = shape;
      v = vect;
    }
  }
  return min_dist;
}
