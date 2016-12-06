cmake_minimum_required(VERSION 2.8)
project(raytracer)
set(CMAKE_CXX_COMPILER "clang++")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -pedantic -std=c++14 -g -O3")

if (NOT ${CMAKE_SYSTEM_NAME} MATCHES "Windows")
  set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra -stdlib=libc++")
endif()

set(BASE_SOURCE_FILES
    src/main.cc
    src/vector3.cc
    src/color.cc
    src/shape.cc
    src/sphere.cc
    src/plane.cc
    src/triangle.cc
    src/image.cc
    src/input.cc
    src/attributes.cc
    src/camera.cc
    src/ray.cc
    src/light.cc
    src/point_light.cc
    src/ambiant_light.cc
    src/directional_light.cc
  )

include_directories(include)

add_executable(raytracer ${BASE_SOURCE_FILES})
