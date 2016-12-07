#include "attributes.hh"

static void between_zero_one(double& value)
{
  value = std::max(0., std::min(1., value));
}

static void positive(double& value)
{
  value = std::max(0., value);
}

std::istream& operator>>(std::istream& is, Attributes& attr)
{
  return is >> attr.diff >> attr.refl >> attr.spec >> attr.shin >> attr.refr
            >> attr.opac;
  between_zero_one(attr.diff);
  between_zero_one(attr.refl);
  between_zero_one(attr.spec);
  positive(attr.shin);
  positive(attr.refr);
  between_zero_one(attr.opac);
}

std::ostream& operator<<(std::ostream& os, const Attributes& attr)
{
  return os << "(diff=" << attr.diff << " refl=" << attr.refl << " spec="
            << attr.spec << " shin=" << attr.shin << " refr=" << attr.refr
            << " opac=" << attr.opac << ')';
}
