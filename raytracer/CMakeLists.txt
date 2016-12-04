cmake_minimum_required(VERSION 2.8)
project(textmining-typometer)
set(CMAKE_CXX_COMPILER "g++")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -pedantic -std=c++14 -g")

set(BASE_SOURCE_FILES
    src/main.cc
    src/parser.cc
    src/vector3.cc
    src/color.cc
  )

include_directories(include)

add_executable(raytracer ${BASE_SOURCE_FILES})
