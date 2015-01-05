package com.github.moleksyuk.vcs.plugin

import com.github.moleksyuk.vcs.VcsCommandExecutor
import com.github.moleksyuk.vcs.VcsTypeDynamicResolver
import com.github.moleksyuk.vcs.version.builder.SemanticVersionBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project

class VcsVersionBuilderPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create("scmVersionBuilder", VcsVersionBuilderPluginExtension)
        project.task('buildSemanticVersion') << {
            def scmType = new VcsTypeDynamicResolver(project.projectDir).resolveScmType()

            VcsCommandExecutor scmCommandExecutor = new VcsCommandExecutor(scmType.command)
            int patch = scmCommandExecutor.execute()

            SemanticVersionBuilder semanticVersionBuilder = new SemanticVersionBuilder()
            semanticVersionBuilder.setMajor(project.scmVersionBuilder.major)
            semanticVersionBuilder.setMinor(project.scmVersionBuilder.minor)
            semanticVersionBuilder.setPatch(patch)
            semanticVersionBuilder.setPreRelease(project.scmVersionBuilder.preRelease)

            project.setVersion(semanticVersionBuilder.buildVersion())
        }
    }
}