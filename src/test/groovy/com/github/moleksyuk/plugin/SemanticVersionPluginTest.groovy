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

        // WHEN
        project.apply plugin: SemanticVersionPlugin

        // THEN
        assertThat(project.tasks.buildSemanticVersion, Matchers.instanceOf(SemanticVersionPluginTask))
    }
}