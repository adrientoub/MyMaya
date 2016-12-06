#include "triangle.hh"
#include "input.hh"

#include <iostream>

Triangle::Triangle(const Vector3& a, const Vector3& b, const Vector3& c,
                   const Attributes& attr, const Color& color)
      : Shape(a, attr, color), b(b), c(c)
{}

Vector3 Triangle::intersect(const Ray& ray)
{
  Vector3& a = pos;
  Vector3 ab = (b - a).normalize();
  Vector3 ac = (c - a).normalize();
  Vector3 n = ab * ac;
  double d = -1 * n.dot_product(a);
  double t0 = (n.dot_product(ray.position) + d)
               * (-1 / n.dot_product(ray.direction));
  Vector3 p = ray.position + ray.direction * t0;
  Vector3 ap = (p - a).normalize();
  double s = ap.dot_product(ac);
  double r = ap.dot_product(ab);

  if ((0 <= r && r <= 1) || (0 <= s && s <= 1)
      || (0 <= (r + s) && (r + s) <= 1))
    return p;
  else
    return Vector3();
}

std::istream& operator>>(std::istream& is, Triangle& triangle)
{
  return is >> triangle.pos >> triangle.b >> triangle.c >> triangle.attr
            >> triangle.color;
}

std::ostream& operator<<(std::ostream& os, const Triangle& triangle)
{
  return os << "Triangle: Pos: " << triangle.pos << " " << triangle.b << " "
            << triangle.c << " " << triangle.attr << " Color: "
            << triangle.color;
}

Color& Triangle::apply_directional_lights(const Input& file, Color& out_color,
                                       const Vector3&)
{
  Vector3 normal = normal_vect();
  for (const DirectionalLight& dl : file.get_directional_lights())
  {
    double ln = dl.dir.dot_product(normal.normalize());
    double ld = attr.diff * ln;

    Color c = dl.color * color;
    out_color = out_color + c * ld;
  }
  out_color = apply_ambiant_light(file, out_color);

  return out_color;
}

Vector3 Triangle::normal_vect() const
{
  const Vector3& a = pos;
  Vector3 ab = b - a;
  Vector3 ac = c - a;
  return ab * ac;
}

Color& Triangle::apply_point_lights(const Input& file,
                                    Color& out_color,
                                    const Vector3& intersect,
                                    size_t ttl)
{
  Vector3 normal = normal_vect();
  for (const PointLight& pl: file.get_point_lights())
  {
    Vector3 l = pl.pos - intersect;
    double ln = l.normalize().dot_product(normal);
    double ld = ln * attr.diff * l.norm();
    Color c = pl.color * color;
    out_color = out_color + c * ld;
  }
  Ray new_ray(intersect - normal * 2 * normal.dot_product(intersect),
              intersect);
  Color res;
  if (attr.refl > 0)
  {
    new_ray.cast(file, res, ttl);
    out_color = out_color + res * attr.refl * 0.1;
  }

  out_color = apply_directional_lights(file, out_color, intersect);
  return out_color;
}

std::ostream& Triangle::display(std::ostream& os) const
{
  return os << *this;
}
