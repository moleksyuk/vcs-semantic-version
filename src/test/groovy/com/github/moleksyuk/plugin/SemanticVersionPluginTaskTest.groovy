package com.github.moleksyuk.plugin

import com.github.moleksyuk.AbstractIntegrationTest
import com.github.moleksyuk.SemanticVersionGradleScriptException
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat


class SemanticVersionPluginTaskTest extends AbstractIntegrationTest {

    @Test(expected = SemanticVersionGradleScriptException)
    public void testPopulateProjectVersionIfMajorIsMissed() {
        // GIVEN
        Project project = ProjectBuilder.builder().build()
        project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)

        // WHEN
        try {
            SemanticVersionPluginTask.populateProjectVersion(project)
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'major' property is required."))
            throw e
        }
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testPopulateProjectVersionIfMajorIsNegative() {
        // GIVEN
        Project project = ProjectBuilder.builder().build()
        def extension = project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
        extension.major = -1

        // WHEN
        try {
            SemanticVersionPluginTask.populateProjectVersion(project)
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'major' property can not be negative."))
            throw e
        }
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testPopulateProjectVersionIfMinorIsMissed() {
        // GIVEN
        Project project = ProjectBuilder.builder().build()
        def extension = project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
        extension.major = 1

        // WHEN
        try {
            SemanticVersionPluginTask.populateProjectVersion(project)
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'minor' property is required."))
            throw e
        }
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testPopulateProjectVersionIfMinorIsNegative() {
        // GIVEN
        Project project = ProjectBuilder.builder().build()
        def extension = project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
        extension.major = 1
        extension.minor = -2

        // WHEN
        try {
            SemanticVersionPluginTask.populateProjectVersion(project)
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'minor' property can not be negative."))
            throw e
        }
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testPopulateProjectVersionIfAccurevStreamIsMissed() {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(ACCUREV_REPOSITORY_PATH)).build()
        def extension = project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
        extension.major = 1
        extension.minor = 2

        // WHEN
        try {
            SemanticVersionPluginTask.populateProjectVersion(project)
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'accurev.stream' property is required."))
            throw e
        }
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testPopulateProjectVersionIfAccurevStreamIsBlank() {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(ACCUREV_REPOSITORY_PATH)).build()
        def extension = project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
        extension.major = 1
        extension.minor = 2
        extension.accurev({ stream = ' ' })

        // WHEN
        try {
            SemanticVersionPluginTask.populateProjectVersion(project)
        } catch (SemanticVersionGradleScriptException e) {
            // THEN
            assertThat(e.getMessage(), Matchers.equalTo("'accurev.stream' property can not be blank."))
            throw e
        }
    }

    @Test
    public void testPopulateProjectVersionAgainstGit() {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(GIT_REPOSITORY_PATH)).build()
        def extension = project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
        extension.major = 1
        extension.minor = 2

        // WHEN
        SemanticVersionPluginTask.populateProjectVersion(project)
        // THEN
        assertThat(project.version, Matchers.equalTo('1.2.5'))
    }

    @Test
    public void testPopulateProjectVersionWithPreReleaseAgainstGit() {
        // GIVEN
        Project project = ProjectBuilder.builder().withProjectDir(new File(GIT_REPOSITORY_PATH)).build()
        def extension = project.extensions.create(SemanticVersionPluginExtension.NAME, SemanticVersionPluginExtension)
        extension.major = 1
        extension.minor = 2
        extension.preRelease = 'beta'

        // WHEN
        SemanticVersionPluginTask.populateProjectVersion(project)
        // THEN
        assertThat(project.version, Matchers.equalTo('1.2.5-beta'))
    }
}
