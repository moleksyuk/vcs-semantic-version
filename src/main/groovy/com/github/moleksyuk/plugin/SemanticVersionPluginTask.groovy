package com.github.moleksyuk.plugin

import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.VcsCommandExecutor
import com.github.moleksyuk.vcs.VcsFactory
import com.github.moleksyuk.vcs.VcsType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

class SemanticVersionPluginTask extends DefaultTask {

    static final String NAME = 'buildSemanticVersion'

    @Input
    Integer major

    @Input
    Integer minor

    @Input
    @Optional
    String preRelease

    @Input
    @Optional
    String accurevStream

    @TaskAction
    def buildSemanticVersion() {
        def vcs = VcsFactory.createVcs(project)
        logger.quiet "Detected version control system: '${vcs.type}'"

        if (VcsType.ACCUREV.equals(vcs.type) && !accurevStream?.trim()) {
            throw new SemanticVersionGradleScriptException("accurev stream must be specified for 'ACCUREV' version control system")
        }

        Integer patch = new VcsCommandExecutor(project, vcs).execute()

        if (preRelease?.trim()) {
            project.setVersion("${major}.${minor}.${patch}-${preRelease.trim()}")
        } else {
            project.setVersion("${major}.${minor}.${patch}")
        }

        logger.quiet "Set project version: '${project.version}'"
    }
}
