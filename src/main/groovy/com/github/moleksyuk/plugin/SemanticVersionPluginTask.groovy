package com.github.moleksyuk.plugin

import com.github.moleksyuk.util.SemanticVersionBuilder
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
        println 'execute buildSemanticVersion'
        def vcsType = new VcsTypeResolver(project.projectDir).resolveVcsType()

        def commandExecutor = new VcsCommandExecutor(vcsType.command)
        int patch = commandExecutor.execute()

        SemanticVersionBuilder semanticVersionBuilder = new SemanticVersionBuilder()
        semanticVersionBuilder.setMajor(major)
        semanticVersionBuilder.setMinor(minor)
        semanticVersionBuilder.setPatch(patch)
        semanticVersionBuilder.setPreRelease(preRelease)
        def version = semanticVersionBuilder.buildVersion()
        project.setVersion(version)
        println "project version ${project.version}"
    }

}
