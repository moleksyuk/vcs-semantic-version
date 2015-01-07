package com.github.moleksyuk.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class SemanticVersionPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create('semanticVersion', SemanticVersionPluginExtension)
        project.task('buildSemanticVersion', type: SemanticVersionPluginTask)
    }

}