package com.github.moleksyuk.plugin

import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.VcsCommandExecutor
import com.github.moleksyuk.vcs.VcsCommandPostProcessorFactory
import com.github.moleksyuk.vcs.VcsTypeFactory
import com.github.moleksyuk.vcs.type.Accurev
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

class SemanticVersionPluginTask extends DefaultTask {

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
        def vcsType = VcsTypeFactory.createVcsType(project)
        logger.quiet "Detected version control system: '${vcsType}'"

        if (vcsType instanceof Accurev && !accurevStream?.trim()) {
            throw new SemanticVersionGradleScriptException("accurevStream must be specified for 'ACCUREV' version control system.")
        }

        def postProcessor = VcsCommandPostProcessorFactory.createVcsCommandPostProcessor(vcsType)
        Integer patch = new VcsCommandExecutor(project, vcsType, postProcessor).execute()

        if (preRelease?.trim()) {
            project.setVersion("${major}.${minor}.${patch}-${preRelease.trim()}")
        } else {
            project.setVersion("${major}.${minor}.${patch}")
        }

        logger.quiet "Set project version: '${project.version}'"
    }
}
