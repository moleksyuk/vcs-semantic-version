package com.github.moleksyuk.scm

class ScmTypeDynamicResolver {

    private final File projectFolder

    ScmTypeDynamicResolver(File projectFolder) {
        this.projectFolder = projectFolder
    }

    ScmType resolveScmType() {
        projectFolder.eachDir {

        }

        throw new ScmVersionBuilderException("Unable to resolve type of VCS for current project.")
    }
}
