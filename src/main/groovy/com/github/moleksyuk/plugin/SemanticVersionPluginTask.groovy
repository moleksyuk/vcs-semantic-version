package com.github.moleksyuk.plugin

import com.github.moleksyuk.vcs.VcsFactory
import com.github.moleksyuk.vcs.VcsType
import com.github.moleksyuk.vcs.cmd.VcsCommandExecutor
import com.github.moleksyuk.vcs.resolver.VcsTypeResolver
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction

import static com.github.moleksyuk.util.Messages.*
import static com.github.moleksyuk.util.Preconditions.*

class SemanticVersionPluginTask extends DefaultTask {

    static final String NAME = 'buildSemanticVersion'

    @Input
    @Optional
    Integer major

    @Input
    @Optional
    Integer minor

    @Input
    @Optional
    String preRelease

    @Input
    @Optional
    String accurevStream

    @TaskAction
    def buildSemanticVersion() {
        populateProjectVersion(project)
    }

    public static void populateProjectVersion(Project project) {
        def extension = project.extensions.getByType(SemanticVersionPluginExtension)

        checkNotNull(MAJOR_PROPERTY, extension.major)
        checkNotNegative(MAJOR_PROPERTY, extension.major)

        checkNotNull(MINOR_PROPERTY, extension.minor)
        checkNotNegative(MINOR_PROPERTY, extension.minor)

        def vcsType = VcsTypeResolver.resolve(project.projectDir)
        project.logger.quiet(DETECTED_VCS_MESSAGE, vcsType)

        if (VcsType.ACCUREV.equals(vcsType)) {
            checkNotNull(ACCUREV_STREAM_PROPERTY, extension.accurev.stream)
            checkNotBlank(ACCUREV_STREAM_PROPERTY, extension.accurev.stream)
        }

        def vcs = VcsFactory.createVcs(project, vcsType)

        Integer patch = new VcsCommandExecutor(project, vcs).execute()
        checkNotNull(MINOR_PROPERTY, extension.minor)
        checkNotNegative(PATCH_PROPERTY, extension.major)

        def version = SemanticVersionBuilder.builder()
                .setMajor(extension.major)
                .setMinor(extension.minor)
                .setPatch(patch)
                .setPreRelease(extension.preRelease)
                .build()

        project.setVersion(version)
        project.logger.quiet(SET_PROJECT_VERSION_MESSAGE, project.version)
    }
}
