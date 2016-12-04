#pragma once

class Shape
{
public:
  Shape() = default;
  Shape(const Vector3& pos, const Attributes& attr, const Color& color);
protected:
  Vector3 pos;
  Attributes attr;
  Color color;
};
