# 1. About

This is a webapp designed to support the [Cello2](https://github.com/CIDARLAB/Cello2) genetic circuit design software.

# 2. Requirements

For standard usage, install the dependencies below:

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

## For developers, or to build the latest version from source

Install the dependencies below:

  + Java JDK 8 (Oracle, OpenJDK)
  + [Maven](https://maven.apache.org/)
  + [MongoDB](https://www.mongodb.com/)
  + [Yosys](http://www.clifford.at/yosys/) (see above)
  + [Graphviz](http://www.graphviz.org/) (see above)

# 3. Usage

Download the latest jar from the [releases page](https://github.com/CIDARLAB/Cello2-webapp/releases).
Run the application with:

    java -jar <file>

where `<file>` is the name of the jar file you downloaded.

## For developers, or to build the latest version from source

Clone the repository.

    git clone https://github.com/CIDARLAB/Cello2.git

Build.

    cd Cello2-webapp/cello2-webapp
    mvn clean install

Run.

    mvn spring-boot:run
