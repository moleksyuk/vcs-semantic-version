package com.github.moleksyuk.vcs.plugin

import com.github.moleksyuk.vcs.VcsTypeResolver
import com.github.moleksyuk.util.SemanticVersionBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class VcsVersionBuilderPluginTask extends DefaultTask {

    @TaskAction
    def buildSemanticVersion() {
        def vcsType = new VcsTypeResolver(project.projectDir).resolveVcsType()

        int patch = vcsType.command.execute().text.toInteger()

        SemanticVersionBuilder semanticVersionBuilder = new SemanticVersionBuilder()
        semanticVersionBuilder.setMajor(project.vcsVersionBuilder.major)
        semanticVersionBuilder.setMinor(project.vcsVersionBuilder.minor)
        semanticVersionBuilder.setPatch(patch)
        semanticVersionBuilder.setPreRelease(project.vcsVersionBuilder.preRelease)

        project.setVersion(semanticVersionBuilder.buildVersion())
    }

}
