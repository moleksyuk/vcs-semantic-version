package com.github.moleksyuk.plugin

import com.github.moleksyuk.AbstractIntegrationTest
import com.github.moleksyuk.SemanticVersionGradleScriptException
import org.gradle.api.Project
import org.gradle.api.tasks.TaskValidationException
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat
import static org.junit.Assert.fail

public class SemanticVersionPluginTaskTest extends AbstractIntegrationTest {

    @Test(expected = TaskValidationException)
    public void testMissedMajorProperty() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: SemanticVersionPlugin
        project.tasks.buildSemanticVersion.minor = 2;

        // WHEN
        project.tasks.buildSemanticVersion.execute()

        // THEN
    }

    @Test(expected = TaskValidationException)
    public void testMissedMinorProperty() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: SemanticVersionPlugin
        project.tasks.buildSemanticVersion.major = 1;

        // WHEN
        project.tasks.buildSemanticVersion.execute()

        // THEN
    }

    @Test
    public void testBuildSemanticVersionWithoutPreRelease() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(GIT_REPOSITORY_PATH)).build()
        project.apply plugin: SemanticVersionPlugin
        project.tasks.buildSemanticVersion.major = 1;
        project.tasks.buildSemanticVersion.minor = 2;

        // WHEN
        project.tasks.buildSemanticVersion.execute()

        // THEN
        assertThat(project.version.toString(), Matchers.equalTo('1.2.5'))
    }

    @Test
    public void testBuildSemanticVersionWithPreRelease() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(GIT_REPOSITORY_PATH)).build()
        project.apply plugin: SemanticVersionPlugin
        project.tasks.buildSemanticVersion.major = 1;
        project.tasks.buildSemanticVersion.minor = 2;
        project.tasks.buildSemanticVersion.preRelease = 'SNAPSHOT';

        // WHEN
        project.tasks.buildSemanticVersion.execute()

        // THEN
        assertThat(project.version.toString(), Matchers.equalTo('1.2.5-SNAPSHOT'))
    }

    @Test
    public void testBuildSemanticVersionForUnknownRepository() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(UNKNOWN_REPOSITORY_PATH)).build()
        project.apply plugin: SemanticVersionPlugin
        project.tasks.buildSemanticVersion.major = 1;
        project.tasks.buildSemanticVersion.minor = 2;

        // WHEN
        try {
            project.tasks.buildSemanticVersion.execute()
            fail()
        } catch (def e) {
            // THEN
            assertThat(e.cause, Matchers.instanceOf(SemanticVersionGradleScriptException))
        }
    }
}