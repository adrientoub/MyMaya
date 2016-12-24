[![Build Status][travis-image]][travis-url]

# MyMaya
A simple 3D computer graphics software.

This project is divided in two parts:
* The GUI application written in Java consists of:
  * A WYSIWIG 3D editor to model your scenes
    * Each scene can be saved to a `.scene` file for further image generation.
  * It's own script language to create sequences of scenes: `mymel`.
    * You can also save/load `mymel` script files.
* The raytracer application written in C++:
  * Taking `.scene` scripts as input and generating the corresponding images.


## Installation

### Dependencies

* clang/gcc with C++14 compatibility
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
