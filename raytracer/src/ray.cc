#include "ray.hh"
#include "input.hh"

const double epsilon = 0.0001;

Ray::Ray(const Vector3& direction, const Vector3& position)
    : direction(direction), position(position)
{}

void Ray::cast(const Input& file, Color& color, size_t ttl) const
{
  if (ttl == 0)
    return;
  ttl--;

  std::shared_ptr<Shape> min_shape = nullptr;
  Vector3 intersect;

  if (cast_shapes(file, min_shape, intersect))
    min_shape->apply_point_lights(file, color, intersect, direction, ttl);
}

bool Ray::cast_shapes(const Input& file,
                      std::shared_ptr<Shape>& min_shape,
                      Vector3& intersect) const
{
  double min_dist = INFINITY;
  for (const std::shared_ptr<Shape>& shape: file.get_shapes())
  {
    Vector3 vect = shape->intersect(*this);
    if (!vect)
      continue;
    double norm = (vect - position).norm();

    if (min_dist > norm)
    {
      min_dist = norm;
      min_shape = shape;
      intersect = vect;
    }
  }
  return min_dist != INFINITY;
}
