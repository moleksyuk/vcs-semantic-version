package com.github.moleksyuk.gradle.vcs

import com.github.moleksyuk.gradle.SemanticVersionGradleScriptException
import com.github.moleksyuk.gradle.vcs.type.Accurev
import com.github.moleksyuk.gradle.vcs.type.Git
import com.github.moleksyuk.gradle.vcs.type.Mercurial
import com.github.moleksyuk.gradle.vcs.type.Svn
import org.gradle.api.Project

class VcsTypeFactory {
    private VcsTypeFactory() {}

    static VcsType createVcsType(Project project) {
        def directories = []
        def files = []
        project.projectDir.eachFile {
            it.isDirectory() ? directories.add(it.name) : files.add(it.name)
        }

        if (directories.contains('.git')) {
            return new Git()
        }

        if (directories.contains('.svn')) {
            return new Svn()
        }

        if (directories.contains('.hg')) {
            return new Mercurial()
        }

        // Must be the last
        if (files.contains('.acignore')) {
            return new Accurev()
        }

        throw new SemanticVersionGradleScriptException("Unable to resolve type of VCS for current project.")
    }
}
