package com.github.moleksyuk.plugin

import com.github.moleksyuk.vcs.VcsCommandExecutor
import com.github.moleksyuk.vcs.VcsCommandPostProcessorFactory
import com.github.moleksyuk.vcs.VcsTypeFactory
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

    @TaskAction
    def buildSemanticVersion() {
        def vcsType = VcsTypeFactory.createVcsType(project)
        logger.quiet "Detected version control system: '${vcsType}'"

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
