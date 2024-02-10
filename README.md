# PetStore Tests
This repo contains CRUD tests as a scenario test, which cover communication between backend components of PetStore site. 
Also, it has single tests for individual endpoints including negative cases.   

## Local Environment Setup

### Install
1. JDK 19+ ([download](https://www.oracle.com/java/technologies/downloads/)).
2. Apache Maven 3.8.1+ ([download](https://maven.apache.org/download.cgi)).

### Set environment variables

**Linux/macOS**

Add the following lines to your shell `.profile` file in `$HOME` directory (e.g. `~/.bash_profile` for Bash):
```bash
export M2_HOME="<maven_directory_path>"
export JAVA_HOME="<jdk_directory_path>"
export PATH="$M2_HOME/bin:$JAVA_HOME/bin:$PATH"
```

**Windows**

Open `Power User Task Menu -> System -> Advanced system settings -> Advanced tab -> Environment Variables`.

In *System variables* section:
- Add/set `JAVA_HOME` variable to `<jdk_directory_path>`
- Add/set `M2_HOME` variable to `<maven_directory_path>`
- Prepend `%M2_HOME%;%JAVA_HOME%;` to `Path` variable


### Clone this repository
```bash
$ git clone https://github.com/CAYLMZ/BackendTestAutomation.git
```

## Running Tests

The tests can be run using your local Maven installation or in Docker container.

Open the project root directory in terminal/command line.

### Maven

```bash
$ mvn test
```
