#vcs-version-builder

Gradle plugin for building project semantic version based on this [specification](http://semver.org/).

System|Status
---|---
**Latest release:**   |[![GitHub version](https://badge.fury.io/gh/moleksyuk%2Fvcs-semantic-version.svg)](http://badge.fury.io/gh/moleksyuk%2Fvcs-semantic-version)
**License:**   |[![License](http://img.shields.io/:license-mit-blue.svg)](http://doge.mit-license.org)
**Continuous integration:**   |[![Build Status](https://travis-ci.org/moleksyuk/vcs-semantic-version.svg?branch=master)](https://travis-ci.org/moleksyuk/vcs-semantic-version)
**Code coverage:**   |[![Coverage Status](https://img.shields.io/coveralls/moleksyuk/vcs-semantic-version.svg)](https://coveralls.io/r/moleksyuk/vcs-semantic-version)
**Dependencies:**   |[![Dependency Status](https://www.versioneye.com/user/projects/54ad0e61b6c7ffd180000150/badge.svg?style=flat)](https://www.versioneye.com/user/projects/54ad0e61b6c7ffd180000150)

## How it works
According to [semantic versioning](http://semver.org/):

```
Given a version number MAJOR.MINOR.PATCH, increment the:

MAJOR version when you make incompatible API changes,
MINOR version when you add functionality in a backwards-compatible manner, and
PATCH version when you make backwards-compatible bug fixes.
Additional labels for pre-release and build metadata are available as extensions to the MAJOR.MINOR.PATCH format.
```
This plugin calculates **PATCH** part of semantic version automatically based on the latest revision number of VCS used in project.

## How to use
todo

## Supported VCS:
### Accurev
Command used to get latest revision in accurev:
```
todo
```

### Git
Command used to get number of commits becase revision number is not numeric in git:
```
git rev-list HEAD --count
```

### Mercurial
Command used to get latest revision in mercurial:
```
hg id --num --rev tip
```

### Subversion
Command used to get latest revision in subversion:
```
svnversion .
```
