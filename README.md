[![Build Status](https://travis-ci.org/CIDARLAB/Cello-v2-webapp.svg?branch=develop)](https://travis-ci.org/CIDARLAB/Cello-v2-webapp)

# 1. About

This is a webapp designed to support the [Cello v2](https://github.com/CIDARLAB/Cello-v2) genetic circuit design software.
In the future this webapp will run on [cellocad.org](http://www.cellocad.org), meaning installation will be unnecessary for most users.

# 2. Installation

## Options

If you wish to create your own instance of the webapp, you have a few options.

1. Get the Docker image from Docker hub and run it.
2. Install the runtime dependencies, download a JAR file from the releases page of this repository, run the JAR file.
2. Install the developer dependencies, clone the contents of this repository, and build the webapp from source.

Each of these procedures is described below.

## (Option 1) Docker

Download and install Docker.

  + Linux: check your package manager / distribution instructions.
  + Mac: [Instructions](https://docs.docker.com/docker-for-mac/install/)
  + Windows: [Instructions](https://docs.docker.com/docker-for-windows/install/) (Windows 10) / [Instructions](https://docs.docker.com/toolbox/toolbox_install_windows/) (Windows 7/8)
  
Pull and run the image:

    docker run -p 8080:8080 -v /local/path/to/db:/data/db -v /local/path/to/projects:/root/projects -t cidarlab/cello-webapp
	
Note the two `/local/path/to` directories. You will need to replace these with folders on your machine of your choosing. You can omit these `-v` clauses, but then the user database and projects storage will be destroyed when you stop the container.

Navigate to <http://localhost:8080> to use.

## (Option 2) Prepackaged JAR file

### Runtime dependencies

  + Java JRE 8 (Oracle, OpenJDK)
  + [MongoDB](https://www.mongodb.com/)
  + [Yosys](http://www.clifford.at/yosys/)
    - Linux: check your package manager
    - Mac OSX: `brew install yosys`
    - Windows:
      * Download yosys-win32-mxebin-0.8.zip here: <http://www.clifford.at/yosys/download.html>
      * Unzip anywhere, add the folder containing yosys.exe to `%Path%`
  + [Graphviz](http://www.graphviz.org/)
    - Linux: check your package manager
    - Mac OSX: `brew install graphviz`
    - Windows:
      * Download and install the latest msi, e.g. graphviz-2.38.msi, here: <https://graphviz.gitlab.io/_pages/Download/Download_windows.html>
      * Add the folder containing dot.exe, e.g. `C:\Program Files (x86)\Graphvix2.38\bin`, to `%Path%`
  + *The latest JAR from the releases page of this repository.* Note that the [Cello v2 CAD tool itself](https://github.com/CIDARLAB/Cello-v2) is bundled with the JAR you download.

### Usage

Ensure that MongoDB is running, and then run the JAR file with:

    java -jar <file>
  
## (Option 3) Building from source

### Developer dependencies

  + Java JDK 8 (Oracle, OpenJDK)
  + [Maven](https://maven.apache.org/)
  + [MongoDB](https://www.mongodb.com/)
  + [Yosys](http://www.clifford.at/yosys/) (see above)
  + [Graphviz](http://www.graphviz.org/) (see above)
  + [Cello v2](https://github.com/CIDARLAB/Cello-v2)

### Usage

Clone the repository.

    git clone https://github.com/CIDARLAB/Cello-v2-webapp.git
	
Initialize GUI submodule.

    cd Cello-v2-webapp/cello-webapp/src/main/resources/static
	git submodule init
	git submodule update

Build.

    cd Cello-v2-webapp/cello-webapp
    mvn clean install

Run.

    mvn spring-boot:run
