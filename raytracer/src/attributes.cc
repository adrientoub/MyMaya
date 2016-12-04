#include "attributes.hh"

std::istream& operator>>(std::istream& is, Attributes& attr)
{
  return is >> attr.diff >> attr.refl >> attr.spec >> attr.shin >> attr.refr
            >> attr.opac;
}
