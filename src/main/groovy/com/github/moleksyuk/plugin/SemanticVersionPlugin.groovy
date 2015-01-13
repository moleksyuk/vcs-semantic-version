package com.github.moleksyuk.plugin

import org.gradle.BuildAdapter
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.invocation.Gradle

class SemanticVersionPlugin implements Plugin<Project> {

    void apply(Project project) {
        def extension = project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
        SemanticVersionPluginTask buildSemanticVersion = project.task(type: SemanticVersionPluginTask, SemanticVersionPluginTask.NAME)

        def projectAdapter = [
                buildSemanticVersion: buildSemanticVersion,
                projectsEvaluated   : { Gradle gradle ->
                    buildSemanticVersion.with {
                        major = extension.major
                        minor = extension.minor
                        preRelease = extension.preRelease
                        accurevStream = extension.accurev.stream
                    }
                }
        ] as BuildAdapter
        project.gradle.addBuildListener(projectAdapter)
    }
}