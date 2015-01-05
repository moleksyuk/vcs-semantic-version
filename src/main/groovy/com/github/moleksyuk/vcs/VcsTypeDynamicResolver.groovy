package com.github.moleksyuk.vcs

class VcsTypeDynamicResolver {

    private final File projectFolder

    VcsTypeDynamicResolver(File projectFolder) {
        this.projectFolder = projectFolder
    }

    VcsType resolveScmType() {
        projectFolder.eachDir {

        }

        throw new RuntimeException("Unable to resolve type of VCS for current project.")
    }
}
