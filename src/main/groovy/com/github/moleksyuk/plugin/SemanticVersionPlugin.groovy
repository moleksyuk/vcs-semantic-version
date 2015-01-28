package com.github.moleksyuk.plugin

import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.VcsCommandExecutor
import com.github.moleksyuk.vcs.VcsFactory
import com.github.moleksyuk.vcs.VcsType
import org.gradle.BuildAdapter
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.invocation.Gradle

class SemanticVersionPlugin implements Plugin<Project> {

    void apply(Project project) {
        def extension = project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)

        checkRequired('major', extension.major)
        checkRequired('minor', extension.minor)

        def vcs = VcsFactory.createVcs(project)
        project.logger.quiet "Detected version control system: '${vcs.type}'"

        if (VcsType.ACCUREV.equals(vcs.type)/* && !accurevStream?.trim()*/) {
            //throw new SemanticVersionGradleScriptException("accurev stream must be specified for 'ACCUREV' version control system")
            checkRequired('accurev.stream', extension.accurev.stream)
        }

        Integer patch = new VcsCommandExecutor(project, vcs).execute()

        if (preRelease?.trim()) {
            project.setVersion("${major}.${minor}.${patch}-${preRelease.trim()}")
        } else {
            project.setVersion("${major}.${minor}.${patch}")
        }

        project.logger.quiet "Set project version: '${project.version}'"

    }

    private static void checkRequired(propertyName, propertyValue) {
        if (!propertyValue || !propertyValue?.trim()) {
            throw new SemanticVersionGradleScriptException("'${propertyName}' is required.")
        }
    }
}