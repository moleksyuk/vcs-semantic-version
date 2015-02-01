package com.github.moleksyuk.vcs.resolver

import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.VcsType

class VcsTypeResolver {

    public static VcsType resolve(File projectDir) {
        if (!projectDir) throw new IllegalArgumentException('projectDir can not be null')

        def directories = []
        def files = []
        projectDir.eachFile {
            it.isDirectory() ? directories.add(it.name) : files.add(it.name)
        }

        if (directories.contains('.git')) {
            return VcsType.GIT
        }

        if (directories.contains('.svn')) {
            return VcsType.SVN
        }

        if (directories.contains('.hg')) {
            return VcsType.MERCURIAL
        }

        // Must be the last
        if (files.contains('.acignore')) {
            return VcsType.ACCUREV
        }

        throw new SemanticVersionGradleScriptException("Unable to resolve type of VCS for current project.")
    }
}
