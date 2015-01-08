package com.github.moleksyuk.plugin

import com.github.moleksyuk.vcs.VcsCommandExecutor
import com.github.moleksyuk.vcs.VcsTypeResolver
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
        def vcsType = new VcsTypeResolver(project.projectDir).resolveVcsType()
        logger.info "Detected version control system: '${vcsType}'"

        def commandExecutor = new VcsCommandExecutor(vcsType.command)
        int patch = commandExecutor.execute()

        if (preRelease?.trim()) {
            project.setVersion("${major}.${minor}.${patch}-${preRelease.trim()}")
        } else {
            project.setVersion("${major}.${minor}.${patch}")
        }

        logger.quiet "Set project version: '${project.version}'"
    }

}
