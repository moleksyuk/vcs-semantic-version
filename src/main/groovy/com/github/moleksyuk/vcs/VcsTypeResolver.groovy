package com.github.moleksyuk.vcs

class VcsTypeResolver {

    private final File projectFolder

    VcsTypeResolver(File projectFolder) {
        this.projectFolder = projectFolder
    }

    VcsType resolveVcsType() {
        def directories = []
        def files = []
        projectFolder.eachFile {
            it.isDirectory() ? directories.add(it.name) : files.add(it.name)
        }

        if (directories.contains('.git')) {
            return VcsType.GIT
        }

        throw new RuntimeException("Unable to resolve type of VCS for current project.")
    }
}
