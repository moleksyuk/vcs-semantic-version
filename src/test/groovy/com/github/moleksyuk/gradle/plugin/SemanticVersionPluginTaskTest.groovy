package com.github.moleksyuk.gradle.plugin

import com.github.moleksyuk.gradle.AbstractIntegrationTest
import com.github.moleksyuk.gradle.SemanticVersionGradleScriptException
import org.gradle.api.Project
import org.gradle.api.tasks.TaskValidationException
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Ignore
import org.junit.Test

import static org.junit.Assert.assertThat
import static org.junit.Assert.fail

public class SemanticVersionPluginTaskTest extends AbstractIntegrationTest {

    @Test(expected = TaskValidationException)
    public void testMissedMajorProperty() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'com.github.moleksyuk.gradle.vcs-semantic-version'
        project.tasks.buildSemanticVersion.minor = 2;

        // WHEN
        project.tasks.buildSemanticVersion.execute()

        // THEN
    }

    @Test(expected = TaskValidationException)
    public void testMissedMinorProperty() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'com.github.moleksyuk.gradle.vcs-semantic-version'
        project.tasks.buildSemanticVersion.major = 1;

        // WHEN
        project.tasks.buildSemanticVersion.execute()

        // THEN
    }

    @Test
    public void testBuildSemanticVersionWithPreRelease() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(GIT_REPOSITORY_PATH)).build()
        project.apply plugin: 'com.github.moleksyuk.gradle.vcs-semantic-version'
        project.tasks.buildSemanticVersion.major = 1;
        project.tasks.buildSemanticVersion.minor = 2;
        project.tasks.buildSemanticVersion.preRelease = 'SNAPSHOT';

        // WHEN
        project.tasks.buildSemanticVersion.execute()

        // THEN
        assertThat(project.version.toString(), Matchers.equalTo('1.2.5-SNAPSHOT'))
    }

    @Ignore
    @Test
    public void testBuildSemanticVersionForAccurevRepository() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(MERCURIAL_REPOSITORY_PATH)).build()
        project.apply plugin: 'com.github.moleksyuk.gradle.vcs-semantic-version'
        project.tasks.buildSemanticVersion.major = 1;
        project.tasks.buildSemanticVersion.minor = 2;

        // WHEN
        project.tasks.buildSemanticVersion.execute()

        // THEN
        assertThat(project.version.toString(), Matchers.equalTo('1.2.2'))
    }

    @Test
    public void testBuildSemanticVersionForGitRepository() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(GIT_REPOSITORY_PATH)).build()
        project.apply plugin: 'com.github.moleksyuk.gradle.vcs-semantic-version'
        project.tasks.buildSemanticVersion.major = 1;
        project.tasks.buildSemanticVersion.minor = 2;

        // WHEN
        project.tasks.buildSemanticVersion.execute()

        // THEN
        assertThat(project.version.toString(), Matchers.equalTo('1.2.5'))
    }

    @Ignore
    @Test
    public void testBuildSemanticVersionForSvnRepository() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(SVN_REPOSITORY_PATH)).build()
        project.apply plugin: 'com.github.moleksyuk.gradle.vcs-semantic-version'
        project.tasks.buildSemanticVersion.major = 1;
        project.tasks.buildSemanticVersion.minor = 2;

        // WHEN
        project.tasks.buildSemanticVersion.execute()

        // THEN
        assertThat(project.version.toString(), Matchers.equalTo('1.2.1'))
    }

    @Test
    public void testBuildSemanticVersionForMercurialRepository() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(MERCURIAL_REPOSITORY_PATH)).build()
        project.apply plugin: 'com.github.moleksyuk.gradle.vcs-semantic-version'
        project.tasks.buildSemanticVersion.major = 1;
        project.tasks.buildSemanticVersion.minor = 2;

        // WHEN
        project.tasks.buildSemanticVersion.execute()

        // THEN
        assertThat(project.version.toString(), Matchers.equalTo('1.2.2'))
    }

    @Test
    public void testBuildSemanticVersionForUnknownRepository() throws Exception {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(UNKNOWN_REPOSITORY_PATH)).build()
        project.apply plugin: 'com.github.moleksyuk.gradle.vcs-semantic-version'
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