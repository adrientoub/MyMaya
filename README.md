[![Build Status][travis-image]][travis-url]

# MyMaya
A simple 3D computer graphics software.

This project is divided in two parts:
* The GUI written in Java containing:
  * A WYSIWIG 3D editor to model your scenes
  * An interface to create and edit `.mymel` scripts: MyMaya's own script language to generate scenes
* The raytracer application written in C++
  * Taking `.mymel` scripts as input and generating images
  * It aims to be fast at generating those scenes

## Installation

### Dependencies

* clang/gcc with c++14 compatibility
* Java 8.0

### Building the raytracer

```
cd src
make
```

## Usage

### Run the GUI application

#### In Intellij IDEA

* Open the source repository with Intellij IDEA
* On the top-right, Edit configurations
* Add New configuration: 'Application'
* Point the Main class option to src/Main.java file
* Save it, and run it

#### In CLI

```
cd src
javac Main.java
java Main
```

### Run the Raytracer application

When you created a `.mymel` script with the GUI application, just run `./raytracer/_build/raytracer input.mymel out.ppm`. This will generate a ppm file. To convert it to jpg: `convert out.ppm out.jpg`.

## GUI Commands

#todo

[travis-url]: https://travis-ci.com/adrientoub/MyMaya
[travis-image]: https://travis-ci.com/adrientoub/MyMaya.svg?token=JzsZbq1sQfwhFpuF1GXJ&branch=master
