package com.github.moleksyuk.vcs.plugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class VcsVersionBuilderPluginTest {

    @Test
    public void testApply() {
        // GIVEN
        Project project = ProjectBuilder.builder().build()

        // WHEN
        project.apply plugin: 'com.github.moleksyuk.vcs-version-builder'

        // THEN
        assertThat(project.tasks.buildSemanticVersion, Matchers.instanceOf(VcsVersionBuilderPluginTask))
    }

//    @Test
//    public void testVersionForGitRepository() throws Exception {
//        // GIVEN
//        Project project = ProjectBuilder.builder().withProjectDir(new File(GIT_REPOSITORY_PATH)).build()
//        project.apply plugin: VcsVersionBuilderPlugin
//        project.vcsVersionBuilder.with { major = 1; minor= 2}
//
//        // WHEN
//        project.tasks.buildSemanticVersion.execute()
//
//        // THEN
//        assertThat(project.version, equalTo("1.2.5"))
//    }
}