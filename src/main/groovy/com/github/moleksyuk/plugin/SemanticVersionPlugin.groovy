package com.github.moleksyuk.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException

class SemanticVersionPlugin implements Plugin<Project> {

    void apply(Project project) {

        try {
            // Workflow for populating project.version during apply plugin
            project.logger.debug "Try 'on apply' workflow."

            SemanticVersionPluginTask.populateProjectVersion(project)
        } catch (UnknownDomainObjectException ex) {
            // Workflow for populating project.version with buildSemanticVersion task
            project.logger.debug "'On apply' workflow not configured. Configure 'buildSemanticVersion' task."

            project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
            project.task(type: SemanticVersionPluginTask, SemanticVersionPluginTask.NAME)
        }
    }
}