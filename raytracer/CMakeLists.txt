cmake_minimum_required(VERSION 2.8)
project(raytracer)

if (NOT ${CMAKE_SYSTEM_NAME} MATCHES "Windows")
  set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -pedantic -std=c++14 -g -O3")
  set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")
  if (DEFINED ENV{CXX})
    set(CMAKE_CXX_COMPILER "$ENV{CXX}")
  else ()
    set(CMAKE_CXX_COMPILER "clang++")
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -stdlib=libc++")
  endif ()
else ()
  set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} /O2 /GL /MP /openmp")
endif ()

set(BASE_SOURCE_FILES
    src/main.cc
    src/vector3.cc
    src/color.cc
    src/shape.cc
    src/sphere.cc
    src/box.cc
    src/plane.cc
    src/mesh.cc
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

    src/octree/octree.cc
    src/octree/leaf.cc
  )

include_directories(include)

add_executable(raytracer ${BASE_SOURCE_FILES})
