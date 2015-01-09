package com.github.moleksyuk.gradle.vcs

import com.github.moleksyuk.gradle.AbstractIntegrationTest
import com.github.moleksyuk.gradle.SemanticVersionGradleScriptException
import com.github.moleksyuk.gradle.vcs.type.Accurev
import com.github.moleksyuk.gradle.vcs.type.Git
import com.github.moleksyuk.gradle.vcs.type.Mercurial
import com.github.moleksyuk.gradle.vcs.type.Svn
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class VcsTypeFactoryTest extends AbstractIntegrationTest {

    @Test
    public void testCreateAccurevVcsType() {
        // GIVEN
        def project = ProjectBuilder.builder().withProjectDir(new File(ACCUREV_REPOSITORY_PATH)).build()

        // WHEN
        def vcsType = VcsTypeFactory.createVcsType(project)

        // THEN
        assertThat(vcsType, Matchers.instanceOf(Accurev))
        assertThat(vcsType.command, Matchers.equalTo('accurev'))
        assertThat(vcsType.commandArguments, Matchers.equalTo([]))
    }

    @Test
    public void testCreateGitVcsType() {
        // GIVEN
        def project = ProjectBuilder.builder().withProjectDir(new File(GIT_REPOSITORY_PATH)).build()

        // WHEN
        def vcsType = VcsTypeFactory.createVcsType(project)

        // THEN
        assertThat(vcsType, Matchers.instanceOf(Git))
        assertThat(vcsType.command, Matchers.equalTo('git'))
        assertThat(vcsType.commandArguments, Matchers.equalTo(['rev-list', 'HEAD', '--count']))
    }

    @Test
    public void testCreateMercurialVcsType() {
        // GIVEN
        def project = ProjectBuilder.builder().withProjectDir(new File(MERCURIAL_REPOSITORY_PATH)).build()

        // WHEN
        def vcsType = VcsTypeFactory.createVcsType(project)

        // THEN
        assertThat(vcsType, Matchers.instanceOf(Mercurial))
        assertThat(vcsType.command, Matchers.equalTo('hg'))
        assertThat(vcsType.commandArguments, Matchers.equalTo(['id', '--num', '--rev', 'tip']))
    }

    @Test
    public void testCreateSvnVcsType() {
        // GIVEN
        def project = ProjectBuilder.builder().withProjectDir(new File(SVN_REPOSITORY_PATH)).build()

        // WHEN
        def vcsType = VcsTypeFactory.createVcsType(project)

        // THEN
        assertThat(vcsType, Matchers.instanceOf(Svn))
        assertThat(vcsType.command, Matchers.equalTo('svnversion'))
        assertThat(vcsType.commandArguments, Matchers.equalTo(['.']))
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testCreateUnknownVcsType() {
        // GIVEN
        def project = ProjectBuilder.builder().withProjectDir(new File(UNKNOWN_REPOSITORY_PATH)).build()

        // WHEN
        VcsTypeFactory.createVcsType(project)

        // THEN
    }
}