.PHONY: all clean build test install help

all: build test

clean:
	@echo "Cleans the build directory using 'mvn clean'."
	mvn clean

build:
	@echo "Builds the project package, skipping tests."
	mvn clean package -DskipTests

test:
	@echo "Compiles and runs unit tests for the project."
	mvn clean compile test

install:
	@echo "Runs 'mvn clean install' to build and install artifacts locally."
	mvn clean install

resolveDependencies:
	@echo "Resolving dependencies"
	mvn dependency:resolve