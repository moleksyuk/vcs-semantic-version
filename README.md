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
### CASE#1: Set project.version using buildSemanticVersion task

#### 1.1. Apply the `com.github.moleksyuk.vcs-semantic-version` plugin to your gradle project.

**Gradle >= 2.1**

```groovy
plugins {
  id 'com.github.moleksyuk.vcs-semantic-version' version '1.1.0'
}
```

**Gradle < 2.1**
```groovy
apply plugin: 'com.github.moleksyuk.vcs-semantic-version'

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.github.moleksyuk:vcs-semantic-version:1.1.0'
    }
}
```

#### 1.2. Configure vcs-semantic-version plugin
Configure the plugin through the `semanticVersion` extension.

```groovy
semanticVersion {
    major = 1 // required
    minor = 0 // required
    preRelease = 'beta' // optional
    accurev.stream = 'MyStream' // required only for ACCUREV VCS
}
```

#### 1.3. Setup dependency
Specify task that should depends on `buildSemanticVersion` task.

**For example:**
```groovy
jar.dependsOn buildSemanticVersion
```

### CASE#2: Set project.version before all possible tasks
There are some tasks in plugins that use `project.version`.

For example: 
* plugin: `java` task: `jar` - builds artifact with specified version
* plugin: `sonar-runner` task: `sonarRunner` - publishes code quality results into SonarQube
* plugin: `maven` task: `uploadArchives` - publishes artifact to Maven repository
* plugin: `maven-publish` task: `publish` - publishes artifact to Maven repository

Having multiple plugins that depend on `project.version` property requires to define dependency for each task:

```groovy
jar.dependsOn buildSemanticVersion
tasks.sonarRunner.dependsOn buildSemanticVersion
....
```

More over `maven-publish` plugin has [issue](http://forums.gradle.org/gradle/topics/maven_publish_and_dynamically_setting_the_version_results_in_build_failure) with dynamically populated `project.version` property.

To fix all these troubles use such configuration.

#### 2.1. Define dependency for `com.github.moleksyuk.vcs-semantic-version` plugin to your gradle project.

```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.github.moleksyuk:vcs-semantic-version:1.1.0'
    }
}
```

#### 2.2. Create semanticVersion extension manually
```groovy
import com.github.moleksyuk.plugin.SemanticVersionPluginExtension
project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
```

#### 2.3. Init semanticVersion extension
```groovy
semanticVersion {
    major = 1
    minor = 2
}
```

#### 2.4. Apply vcs-semantic-version plugin
```groovy
apply plugin: 'com.github.moleksyuk.vcs-semantic-version'
```

The complete example:

```groovy
import com.github.moleksyuk.plugin.SemanticVersionPluginExtension
project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
semanticVersion {
    major = 1
    minor = 2
}
apply plugin: 'com.github.moleksyuk.vcs-semantic-version'
apply plugin: 'maven-publish' // must be after vcs-semantic-version

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.github.moleksyuk:vcs-semantic-version:1.1.0'
    }
}
```

*NOTE: Keep in mind that all dependent plugins on `project.version` should be applied after row:*

```groovy
apply plugin: 'com.github.moleksyuk.vcs-semantic-version'
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
