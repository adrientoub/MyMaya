# MyMaya

[![Build Status][travis-image]][travis-url]

## Description

A simple 3D computer graphics software.

This project is divided in two parts:
* The GUI application written in Java consisting of:
  - A WYSIWIG 3D editor to model your scenes
    * You can add primitives or complex meshes from .obj files
    * Each scene can be saved to a `.scene` file for further image generation.
  - Its own scripting language
    * Able to edit the scene
    * Each visual action is recorded and added to history in a repeatable form
    * Can be loaded from file to generate complexe scenes and annimation
    * [For more information about the language](SCRIPT.md)
* The raytracer application written in C++:
  - Taking scenes as input and generating the corresponding image in PPM.

## Compatibility

Supported platforms are:

- Windows x64 (tested on Windows 10)
- Linux x64

## Raytracer

### Dependencies

* A C++ compiler with C++14 compatibility
  - If the compiler supports OpenMP it will be used to speed up the renders
* Cmake

This was tested on:
* Windows 10 with VS2017
* Ubuntu with clang++ 3.8
* Ubuntu with g++ 4.9, 5.0 and 6.0

### Build

#### Windows

To compile the Raytracer on Windows you can use Cmake to generate a Visual
Studio Solution or just simply use the Open Directory feature of VS2017.

When the project is open in Visual Studio we recommend to compile using the
Release preset (instead of Debug) in order to get the best performance
possible.

#### Linux

```bash
cd raytracer
make
```

### Usage

After creating a scene file with the GUI application, it is possible to run the
raytracer to make a realistic render of this scene.

```bash
./raytracer/_build/raytracer your_input.scene out.ppm
```

This will generate a [PPM](https://fr.wikipedia.org/wiki/Portable_pixmap) file.

You can view this PPM file using diverse image viewer or convert it to popular
image formats using a program like ImageMagick. With ImageMagick you can just
launch:

```bash
convert out.ppm out.jpg
```
To convert the output image to JPG.


## GUI

### Dependencies

* Java 8.0 with JavaFX support

### Build

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

### Usage

TODO

[travis-url]: https://travis-ci.com/adrientoub/MyMaya
[travis-image]: https://travis-ci.com/adrientoub/MyMaya.svg?token=JzsZbq1sQfwhFpuF1GXJ&branch=master
