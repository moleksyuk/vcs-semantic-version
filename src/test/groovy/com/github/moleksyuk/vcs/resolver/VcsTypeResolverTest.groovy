package com.github.moleksyuk.vcs.resolver

import com.github.moleksyuk.AbstractIntegrationTest
import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.VcsType
import org.junit.Test

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

class VcsTypeResolverTest extends AbstractIntegrationTest {

    @Test(expected = IllegalArgumentException)
    public void testResolveIfProjectDirIsNull() {
        // GIVEN
        def projectDir = null

        // WHEN
        VcsTypeResolver.resolve(projectDir)

        // THEN
    }

    @Test
    public void testResolveGit() {
        // GIVEN
        def projectDir = new File(GIT_REPOSITORY_PATH)

        // WHEN
        VcsType actual = VcsTypeResolver.resolve(projectDir)

        // THEN
        assertThat(actual, equalTo(VcsType.GIT))
    }

    @Test
    public void testResolveSvn() {
        // GIVEN
        def projectDir = new File(SVN_REPOSITORY_PATH)

        // WHEN
        VcsType actual = VcsTypeResolver.resolve(projectDir)

        // THEN
        assertThat(actual, equalTo(VcsType.SVN))
    }

    @Test
    public void testResolveMercurial() {
        // GIVEN
        def projectDir = new File(MERCURIAL_REPOSITORY_PATH)

        // WHEN
        VcsType actual = VcsTypeResolver.resolve(projectDir)

        // THEN
        assertThat(actual, equalTo(VcsType.MERCURIAL))
    }

    @Test
    public void testResolveAccurev() {
        // GIVEN
        def projectDir = new File(ACCUREV_REPOSITORY_PATH)

        // WHEN
        VcsType actual = VcsTypeResolver.resolve(projectDir)

        // THEN
        assertThat(actual, equalTo(VcsType.ACCUREV))
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testResolveUnknown() {
        // GIVEN
        def projectDir = new File(UNKNOWN_REPOSITORY_PATH)

        // WHEN
        VcsType actual = VcsTypeResolver.resolve(projectDir)

        // THEN
    }

}
