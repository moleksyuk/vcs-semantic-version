package com.github.moleksyuk.plugin

import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.VcsFactory
import com.github.moleksyuk.vcs.VcsType
import com.github.moleksyuk.vcs.cmd.VcsCommandExecutor
import com.github.moleksyuk.vcs.resolver.VcsTypeResolver
import org.gradle.api.Plugin
import org.gradle.api.Project

class SemanticVersionPlugin implements Plugin<Project> {

    void apply(Project project) {
        def extension = project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)

        checkRequired('major', extension.major)
        checkRequired('minor', extension.minor)

        def vcsType = VcsTypeResolver.resolve(project.projectDir)
        project.logger.quiet "Detected version control system: '${vcsType}'"

        if (VcsType.ACCUREV.equals(vcsType)) {
            checkRequired('accurev.stream', extension.accurev.stream)
        }

        def vcs = VcsFactory.createVcs(project, vcsType)
        Integer patch = new VcsCommandExecutor(project, vcs).execute()


        def version = SemanticVersionBuilder.builder()
                .setMajor(extension.major)
                .setMinor(extension.minor)
                .setPatch(patch)
                .setPreRelease(extension.preRelease)
                .build()

        project.setVersion(version)
        project.logger.quiet "Set project version: '${project.version}'"
    }

    private static void checkRequired(propertyName, propertyValue) {
        if (!propertyValue) {
            throw new SemanticVersionGradleScriptException("'${propertyName}' property is required.")
        } else if (propertyValue instanceof Integer && propertyValue < 0) {
            throw new SemanticVersionGradleScriptException("'${propertyName}' property can not be negative.")
        } else if (propertyValue instanceof String && !propertyValue.trim()) {
            throw new SemanticVersionGradleScriptException("'${propertyName}' property can not be empty.")
        }
    }
}