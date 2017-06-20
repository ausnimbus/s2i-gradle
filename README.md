# AusNimbus Builder for Gradle (WIP) [![Build Status](https://travis-ci.org/ausnimbus/s2i-gradle.svg?branch=master)](https://travis-ci.org/ausnimbus/s2i-gradle) [![Docker Repository on Quay](https://quay.io/repository/ausnimbus/s2i-gradle/status "Docker Repository on Quay")](https://quay.io/repository/ausnimbus/s2i-gradle)

[![Gradle](https://user-images.githubusercontent.com/2239920/27293069-b1d1474e-5558-11e7-900b-8394f7a82c0a.jpg)](https://www.ausnimbus.com.au/)

The [AusNimbus](https://www.ausnimbus.com.au/) builder for Gradle provides a fast, secure and reliable [Grails](https://www.ausnimbus.com.au/languages/grails-hosting/) and [Gradle hosting](https://www.ausnimbus.com.au/languages/java-hosting/) environment.

The default version of Gradle will be used unless a [Gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) is found. It is recommended to include the `gradlew` in your repository.

Web processes must bind to port `8080`, and only the HTTP protocol is permitted for incoming connections.

## Environment Variables

* **GRADLE_BUILD_TASK**
  * The parameter passed to the gradle build process. By default it is automatically detected:
  * Spring Boot: `build -x test`
  * Ratpack: `installDist -x test`
  * Default: `stage`

# Versions

- jdk8
- jdk9 (experimental)
