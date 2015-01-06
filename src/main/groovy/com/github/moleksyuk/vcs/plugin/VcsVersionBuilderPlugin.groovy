package com.github.moleksyuk.vcs.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class VcsVersionBuilderPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.extensions.create('vcsVersionBuilder', VcsVersionBuilderPluginExtension)
        project.task('buildSemanticVersion', type: VcsVersionBuilderPluginTask)
    }

}