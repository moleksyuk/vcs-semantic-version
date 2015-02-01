package com.github.moleksyuk.vcs

import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.impl.Accurev
import com.github.moleksyuk.vcs.impl.Git
import com.github.moleksyuk.vcs.impl.Mercurial
import com.github.moleksyuk.vcs.impl.Svn
import org.gradle.api.Project

import static com.github.moleksyuk.vcs.VcsType.*

class VcsFactory {
    private VcsFactory() {}

    static Vcs createVcs(Project project, VcsType vcsType) {
        if (!project) throw new IllegalArgumentException('project can not be null')
        if (!vcsType) throw new IllegalArgumentException('vcsType can not be null')

        switch (vcsType) {
            case ACCUREV:
                return new Accurev(project.semanticVersion.accurev.stream)
            case GIT:
                return new Git()
            case MERCURIAL:
                return new Mercurial()
            case SVN:
                return new Svn()
            default:
                throw new SemanticVersionGradleScriptException("Unable to create Vcs for VcsType: '${vcsType}'.")
        }
    }
}
