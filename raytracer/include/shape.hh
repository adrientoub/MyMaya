#pragma once

#include "attributes.hh"
#include "color.hh"
#include "vector3.hh"
#include "ray.hh"

#include <vector>

struct DirectionalLight;

class Shape
{
public:
  virtual ~Shape() = default;
  Shape() = default;
  Shape(const Vector3& pos, const Attributes& attr, const Color& color);
  virtual Vector3 intersect(const Ray& ray) const = 0;
  inline const Attributes& get_attributes() const;
  void apply_lights(const Input& file, Color& r, const Vector3& intersect,
                    const Vector3& direction, size_t ttl) const;
  friend std::ostream& operator<<(std::ostream& os, const Shape& shape);

  using shape_double = std::pair<std::shared_ptr<Shape>, double>;
  static inline void sort_shape_double(std::vector<shape_double>& v);

protected:
  virtual std::ostream& display(std::ostream& os) const = 0;
  virtual Vector3 normal_vect(const Vector3& intersect) const = 0;
  virtual Vector3 normal_vect_point(const Vector3& intersect) const;

  std::string name;
  Vector3 pos;
  Attributes attr;
  Color color;

private:
  Vector3 refraction_vector(const Vector3& incidence,
                            const Vector3& normal) const;
  void refract(const Input& file, Color& refract_color,
               const Vector3& intersect, const Vector3& normal,
               const Vector3& direction, const size_t ttl) const;
  void reflect(const Input& file, Color& out_color, const Vector3& intersect,
               const Vector3& normal, size_t ttl) const;
  void apply_ambiant_light(const Input& file, Color& out_color) const;
  void apply_directional_lights(const Input& file, Color& out_color,
                                const Vector3& intersect) const;
  double get_directional_shadows(const Input& file,
                                 const Vector3& intersect,
                                 const DirectionalLight& dl) const;
  void apply_point_lights(const Input& file, Color& out_color,
                          const Vector3& intersect,
                          const Vector3& normal) const;
};

std::ostream& operator<<(std::ostream& os, const Shape& shape);

#include "shape.hxx"
