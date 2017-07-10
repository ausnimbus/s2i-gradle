# AusNimbus Builder for Gradle [![Build Status](https://travis-ci.org/ausnimbus/s2i-gradle.svg?branch=master)](https://travis-ci.org/ausnimbus/s2i-gradle) [![Docker Repository on Quay](https://quay.io/repository/ausnimbus/s2i-gradle/status "Docker Repository on Quay")](https://quay.io/repository/ausnimbus/s2i-gradle)

[![Gradle](https://user-images.githubusercontent.com/2239920/27293069-b1d1474e-5558-11e7-900b-8394f7a82c0a.jpg)](https://www.ausnimbus.com.au/)

The [AusNimbus](https://www.ausnimbus.com.au/) builder for Gradle provides a fast, secure and reliable [Grails](https://www.ausnimbus.com.au/languages/grails-hosting/) and [Gradle hosting](https://www.ausnimbus.com.au/languages/java-hosting/) environment.

This document describes the behaviour and environment configuration when running your Gradle apps on AusNimbus.

## Table of Contents

- [Runtime Environments](#runtime-environments)
- [Web Process](#web-process)
- [Dependency Management](#dependency-management)
- [Extending](#extending)
  - [Build Stage (assemble)](#build-stage-assemble)
  - [Runtime Stage (run)](#runtime-stage-run)
  - [Persistent Environment Variables](#persistent-environment-variables)
- [Debug Mode](#debug-mode)

## Runtime Environments

AusNimbus supports the latest stable Java release.

The currently supported versions are `jdk8`

The most recent version of Gradle will be used unless a [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) is found.

To use the Gradle wrapper you need to include the `gradlew` file in the root of your repository. It is highly recommended you use `gradlew`.

## Web Process

Your application's web processes must bind to port `8080`.

AusNimbus handles SSL termination at the load balancer.

## Dependency Management

`gradle build` is used to build your application. The builder will attempt to detect the task required based on your application.

Application | Task
------------|-------------
Spring Boot | `build -x test`
Ratpack     | `installDist -x test`
Default     | `stage`

You are able to pass your own parameters using the following environment variable:

NAME              | Description
------------------|-------------
GRADLE_BUILD_TASK | Parameters passed to `gradle build`

## Extending

AusNimbus builders are split into two stages:

- Build
- Runtime

Both stages are completely extensible, allowing you to customize or completely overwrite each stage.

### Build Stage (assemble)

If you want to customize the build stage, you need to add the executable `.s2i/bin/assemble` file in your repository.

This file should contain the logic required to build and install any dependencies your application requires.

If you only want to extend the build stage, you may use this example:

```sh
#!/bin/bash

echo "Logic to include before"

# Run the default builder logic
. /usr/libexec/s2i/assemble

echo "Logic to include after"
```

### Runtime Stage (run)

If you only want to change the executed command for the run stage you may the following environment variable.

NAME        | Description
------------|-------------
APP_RUN     | Define a custom command to start your application.

**NOTE:** `APP_RUN` will overwrite any builder's runtime configuration (including the [Debug Mode](#debug-mode) section)

Alternatively you may customize or overwrite the entire runtime stage by including the executable file `.s2i/bin/run`

This file should contain the logic required to execute your application.

If you only want to extend the run stage, you may use this example:

```sh
#!/bin/bash

echo "Logic to include before"

# Run the default builder logic
. /usr/libexec/s2i/run
```

As the run script executes every time your application is deployed, scaled or restarted it's recommended to keep avoid including complex logic which may delay the start-up process of your application.

### Persistent Environment Variables

The recommend approach is to set your environment variables in the AusNimbus dashboard.

However it is possible to store environment variables in code using the `.s2i/environment` file.

The file expects a key=value format eg.

```
KEY=VALUE
FOO=BAR
```

## Debug Mode

The AusNimbus builder provides a convenient environment variable to help you debug your application.

NAME        | Description
------------|-------------
DEBUG       | Set to "TRUE" to enable Debug Mode


Web processes must bind to port `8080` and only the HTTP protocol is permitted for incoming connections.
