package com.github.moleksyuk.plugin

import com.github.moleksyuk.util.SemanticVersionBuilder
import com.github.moleksyuk.vcs.VcsCommandExecutor
import com.github.moleksyuk.vcs.VcsTypeResolver
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class SemanticVersionPluginTask extends DefaultTask {

    @TaskAction
    def buildSemanticVersion() {
        def vcsType = new VcsTypeResolver(project.projectDir).resolveVcsType()

        def commandExecutor = new VcsCommandExecutor(vcsType.command)
        int patch = commandExecutor.execute()

        SemanticVersionBuilder semanticVersionBuilder = new SemanticVersionBuilder()
        semanticVersionBuilder.setMajor(project.semanticVersion.major)
        semanticVersionBuilder.setMinor(project.semanticVersion.minor)
        semanticVersionBuilder.setPatch(patch)
        semanticVersionBuilder.setPreRelease(project.semanticVersion.preRelease)

        project.setVersion(semanticVersionBuilder.buildVersion())
    }

}
