#pragma once

#include "vector3.hh"
#include <cmath>

inline double Vector3::dot_product(const Vector3& v) const
{
  return x_ * v.x_ + y_ * v.y_ + z_ * v.z_;
}

inline Vector3 Vector3::normalize() const
{
  double norm = this->norm();
  return Vector3(x_ / norm, y_ / norm, y_ / norm);
}

inline Vector3 Vector3::operator*(const Vector3& v) const
{
  return Vector3(y_ * v.z_ - z_ * v.y_,
                 z_ * v.x_ - x_ * v.z_,
                 x_ * v.y_ - y_ * v.x_);
}

inline Vector3 Vector3::operator*(double scale) const
{
  return Vector3(scale * x_,
                 scale * y_,
                 scale * z_);
}

inline Vector3 Vector3::operator+(const Vector3& v) const
{
  return Vector3(x_ + v.x_, y_ + v.y_, z_ + v.z_);
}

inline Vector3 Vector3::operator-(const Vector3& v) const
{
  return Vector3(x_ - v.x_, y_ - v.y_, z_ - v.z_);
}

double Vector3::norm() const
{
  return std::sqrt(std::pow(x_, 2) + std::pow(y_, 2) + std::pow(z_, 2));
}
