#pragma once

#include <ostream>

class Vector3
{
public:
  Vector3() = default;
  Vector3(double x, double y, double z);
  inline Vector3 normalize() const;
  inline double dot_product(const Vector3& v) const;
  inline Vector3 operator*(const Vector3& v) const;
  inline Vector3 operator*(double scale) const;
  inline Vector3 operator+(const Vector3& v) const;
  inline Vector3 operator-(const Vector3& v) const;
  bool operator==(const Vector3& vect) const;
  friend std::ostream& operator<<(std::ostream& os, const Vector3& vect);

private:
  double x_;
  double y_;
  double z_;
};

std::ostream& operator<<(std::ostream& os, const Vector3& vect);

#include "vector3.hxx"
