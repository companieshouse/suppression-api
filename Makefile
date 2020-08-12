artifact_name       := suppression-api

.PHONY: all
all: build

.PHONY: clean
clean:
	mvn clean
	rm -f ./$(artifact_name).jar
	rm -f ./$(artifact_name)-*.zip
	rm -rf ./build-*
	rm -f ./build.log

.PHONY: build
build:
	mvn compile

.PHONY: dev
dev: clean
	mvn package -DskipTests=true
	cp target/$(artifact_name)-unversioned.jar $(artifact_name).jar

