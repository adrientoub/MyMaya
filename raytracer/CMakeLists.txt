cmake_minimum_required(VERSION 2.8)
project(textmining-typometer)
set(CMAKE_CXX_COMPILER "clang++")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra -pedantic -std=c++14 -g -stdlib=libc++")

set(BASE_SOURCE_FILES
    src/main.cc
    src/vector3.cc
    src/color.cc
    src/shape.cc
    src/sphere.cc
    src/image.cc
    src/input.cc
    src/attributes.cc
    src/camera.cc
    src/ray.cc
  )

include_directories(include)

add_executable(raytracer ${BASE_SOURCE_FILES})
