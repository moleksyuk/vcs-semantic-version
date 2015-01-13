package com.github.moleksyuk.vcs

import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.parser.AccurevOutputParser
import com.github.moleksyuk.vcs.parser.BasicOutputParser
import org.gradle.api.Project

class VcsFactory {
    private VcsFactory() {}

    static Vcs createVcs(Project project) {
        def directories = []
        def files = []
        project.projectDir.eachFile {
            it.isDirectory() ? directories.add(it.name) : files.add(it.name)
        }

        if (directories.contains('.git')) {
            return new Vcs(VcsType.GIT, 'git', ['rev-list', 'HEAD', '--count'], new BasicOutputParser())
        }

        if (directories.contains('.svn')) {
            return new Vcs(VcsType.SVN, 'svnversion', ['.'], new BasicOutputParser())
        }

        if (directories.contains('.hg')) {
            return new Vcs(VcsType.MERCURIAL, 'hg', ['id', '--num', '--rev', 'tip'], new BasicOutputParser())
        }

        // Must be the last
        if (files.contains('.acignore')) {
            return new Vcs(VcsType.ACCUREV, 'accurev', ['hist', '-ft', '-t', 'highest', '-s', project.semanticVersion.accurev.stream], new AccurevOutputParser())
        }

        throw new SemanticVersionGradleScriptException("Unable to resolve type of VCS for current project.")
    }
}
