package com.github.moleksyuk.scm.plugin

import com.github.moleksyuk.scm.ScmCommandExecutor
import com.github.moleksyuk.scm.ScmTypeDynamicResolver
import com.github.moleksyuk.scm.version.builder.SemanticVersionBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project

class ScmVersionBuilderPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create("scmVersionBuilder", ScmVersionBuilderPluginExtension)
        project.task('buildSemanticVersion') << {
            def scmType = new ScmTypeDynamicResolver(project.projectDir).resolveScmType()

            ScmCommandExecutor scmCommandExecutor = new ScmCommandExecutor(scmType.command)
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