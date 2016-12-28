all: _build
	cd _build && make -j7

check: all
	./_build/raytracer check/transparency.in test.ppm && convert test.ppm test.jpg

_build:
	mkdir -p _build
	cd _build && cmake ..
