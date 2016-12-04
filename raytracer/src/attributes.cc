#include "attributes.hh"

std::istream& operator>>(std::istream& is, Attributes& attr)
{
  return is >> attr.diff >> attr.refl >> attr.spec >> attr.shin >> attr.refr
            >> attr.opac;
}

std::ostream& operator<<(std::ostream& os, const Attributes& attr)
{
  return os << "(diff=" << attr.diff << " refl=" << attr.refl << " spec="
            << attr.spec << " shin=" << attr.shin << " refr=" << attr.refr
            << " opac=" << attr.opac << ')';
}
