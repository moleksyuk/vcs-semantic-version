package com.github.moleksyuk.plugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class SemanticVersionPluginTest {

    @Test
    public void testApply() {
        // GIVEN
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: SemanticVersionPlugin
        project.extensions.semanticVersion.major = 1
        project.extensions.semanticVersion.minor = 2
        project.extensions.semanticVersion.preRelease = 'preRelease'
        project.extensions.semanticVersion.accurev.stream = 'stream'

        // WHEN
        def gradle = project.getGradle()
        gradle.listenerManager.allListeners*.projectsEvaluated gradle
        SemanticVersionPluginTask task = project.tasks.findByName(SemanticVersionPluginTask.NAME)

        // THEN
        assertThat(task, Matchers.instanceOf(SemanticVersionPluginTask))
        assertThat(task.major, Matchers.equalTo(1))
        assertThat(task.minor, Matchers.equalTo(2))
        assertThat(task.preRelease, Matchers.equalTo('preRelease'))
        assertThat(task.accurevStream, Matchers.equalTo('stream'))
    }
}