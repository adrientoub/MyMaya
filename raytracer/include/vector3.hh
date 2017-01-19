#pragma once

#include <ostream>

class Vector3
{
public:
  Vector3() = default;
  Vector3(double x, double y, double z);
  inline Vector3 normalize() const;
  inline double dot_product(const Vector3& v) const;
  inline double norm() const;

  inline Vector3 operator*(const Vector3& v) const;
  inline Vector3 operator*(double scale) const;
  inline Vector3 operator+(const Vector3& v) const;
  inline Vector3 operator-(const Vector3& v) const;
  inline Vector3 operator-() const;
  inline bool operator==(const Vector3& vect) const;
  inline bool operator<(const Vector3& vect) const;
  inline bool operator!=(const Vector3& vect) const;
  inline explicit operator bool() const;
  friend Vector3 operator/(const double& d, const Vector3& vector3);
  friend std::ostream& operator<<(std::ostream& os, const Vector3& vect);
  friend std::istream& operator>>(std::istream& is, Vector3& vector3);

  inline double getX() const;
  inline double getY() const;
  inline double getZ() const;

private:
  double x_ = 0.;
  double y_ = 0.;
  double z_ = 0.;
};

inline Vector3 operator/(const double& d, const Vector3& vector3);
std::ostream& operator<<(std::ostream& os, const Vector3& vect);
std::istream& operator>>(std::istream& is, Vector3& vector3);

#include "vector3.hxx"
