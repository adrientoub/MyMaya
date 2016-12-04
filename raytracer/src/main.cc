#include <fstream>
#include <iostream>
#include <string>

#include "input.hh"

int main(int argc, char **argv)
{
  if (argc < 3)
  {
    std::cerr << "Usage: " << argv[0] << " [INPUT] [OUTPUT]" << std::endl;
    return 1;
  }
  Input input(argv[2]);

  std::ifstream file(argv[1]);
  file >> input;
  input.calculate();
  input.save();
}
