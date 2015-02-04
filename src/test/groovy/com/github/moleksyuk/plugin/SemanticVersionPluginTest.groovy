package com.github.moleksyuk.plugin

import com.github.moleksyuk.AbstractIntegrationTest
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class SemanticVersionPluginTest extends AbstractIntegrationTest {

    @Test
    public void testApplyOnApplyWorkflow() {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(GIT_REPOSITORY_PATH)).build()
        project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
        project.extensions.semanticVersion.major = 1
        project.extensions.semanticVersion.minor = 2

        // WHEN
        project.apply plugin: SemanticVersionPlugin

        // THEN
        assertThat(project.version, Matchers.equalTo('1.2.5'))
    }

    @Test
    public void testApplyOnTaskWorkflow() {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(GIT_REPOSITORY_PATH)).build()

        // WHEN
        project.apply plugin: SemanticVersionPlugin

        // THEN
        SemanticVersionPluginTask task = project.tasks.findByName(SemanticVersionPluginTask.NAME)
        assertThat(task, Matchers.instanceOf(SemanticVersionPluginTask))

        // WHEN
        project.extensions.semanticVersion.major = 1
        project.extensions.semanticVersion.minor = 2

        task.execute()

        // THEN
        assertThat(project.version, Matchers.equalTo('1.2.5'))
    }
}