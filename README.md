vcs-semantic-version
===================

[![GitHub version](https://badge.fury.io/gh/moleksyuk%2Fvcs-semantic-version.svg)](http://badge.fury.io/gh/moleksyuk%2Fvcs-semantic-version)
[![License](http://img.shields.io/:license-mit-blue.svg)](http://doge.mit-license.org)
[![Build Status](https://travis-ci.org/moleksyuk/vcs-semantic-version.svg?branch=master)](https://travis-ci.org/moleksyuk/vcs-semantic-version)
[![Coverage Status](https://img.shields.io/coveralls/moleksyuk/vcs-semantic-version.svg)](https://coveralls.io/r/moleksyuk/vcs-semantic-version)
[![Dependency Status](https://www.versioneye.com/user/projects/54ad0e61b6c7ffd180000150/badge.svg?style=flat)](https://www.versioneye.com/user/projects/54ad0e61b6c7ffd180000150)

[Gradle](http://www.gradle.org) plugin for populating `project.version` property based on [semantic version specification](http://semver.org/).

The **vcs-semantic-version** plugin is hosted at [Bintray's JCenter](https://bintray.com/moleksyuk/gradle-plugins/vcs-semantic-version) and [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22vcs-semantic-version%22).

## Functionality
The following functionality is provided by the vcs-semantic-version plugin:

 * Auto detects version control system of project
 * Populates `project.version` property with such pattern `MAJOR.MINOR.PATCH-PRE_RELEASE`

## How to use
#### 1. Apply vcs-semantic-version plugin
Apply the `com.github.moleksyuk.vcs-semantic-version` plugin to your Gradle plugin project.

**Gradle 2.1+**

```groovy
plugins {
  id 'com.github.moleksyuk.vcs-semantic-version' version '1.0.0'
}
```
Please refer to the [Gradle DSL PluginDependenciesSpec](http://www.gradle.org/docs/current/dsl/org.gradle.plugin.use.PluginDependenciesSpec.html) to
understand the behavior and limitations when using the new syntax to declare plugin dependencies.

#### 2. Configure vcs-semantic-version plugin
Configure the plugin through the `semanticVersion` extension.

```groovy
semanticVersion {
    major = 1 // required
    minor = 0 // required
    preRelease = 'beta' // optional
    accurev.stream = 'MyStream' // required only for ACCUREV VCS
}
```

#### 3. Setup dependency
Specify task that should depends on `buildSemanticVersion` task.

**For example:**
```groovy
jar.dependsOn buildSemanticVersion
```


## How it works
#### 1. Detects VCS for project with such rules:

 * If root project folder contains `.git` *folder* then `GIT` version control system is used
 * If root project folder contains `.hg` *folder* then `MERCURIAL` version control system is used
 * If root project folder contains `.svn` *folder* then `SUBVERSION` version control system is used
 * If non of above but contains `.acignore` *file* then `ACCUREV` version control system is used

#### 2. Gets the latest revision number from VCS

**For GIT**
```
git rev-list HEAD --count
```

**For MERCURIAL**
```
hg id --num --rev tip
```

**For SUBVERSION**
```
svnversion .
```

**For ACCUREV**
```
accurev hist -ft -t highest -s [accurev.stream]
```

#### 3. Populates project version property with such pattern:

`MAJOR.MINOR.PATCH-PRE_RELEASE` 

where:
 * `MAJOR` - specified in `semanticVersion` extension
 * `MINOR` - specified in `semanticVersion` extension
 * `PATCH` - calculated in **step#2**
 * `PRE_RELEASE` - specified in `semanticVersion` extension
