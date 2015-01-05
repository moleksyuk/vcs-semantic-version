package com.github.moleksyuk.scm.version.builder;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class VersionBuilderTest {

    @Test
    public void testBuildVersion() throws Exception {
        // GIVEN
        int major = 1;
        int minor = 2;
        int patch = 3;
        String preRelease = "alpha.1";

        // WHEN
        String actual = VersionBuilder.buildVersion(major, minor, patch, preRelease);

        // THEN
        assertThat(actual, equalTo("1.2.3-alpha.1"));
    }

    @Test
    public void testBuildVersionWithWhiteSpacesPreRelease() throws Exception {
        // GIVEN
        int major = 1;
        int minor = 2;
        int patch = 3;
        String preRelease = "  alpha.1   ";

        // WHEN
        String actual = VersionBuilder.buildVersion(major, minor, patch, preRelease);

        // THEN
        assertThat(actual, equalTo("1.2.3-alpha.1"));
    }

    @Test
    public void testBuildVersionWithNullPreRelease() throws Exception {
        // GIVEN
        int major = 1;
        int minor = 2;
        int patch = 3;
        String preRelease = null;

        // WHEN
        String actual = VersionBuilder.buildVersion(major, minor, patch, preRelease);

        // THEN
        assertThat(actual, equalTo("1.2.3"));
    }

    @Test
    public void testBuildVersionWithEmptyPreRelease() throws Exception {
        // GIVEN
        int major = 1;
        int minor = 2;
        int patch = 3;
        String preRelease = "";

        // WHEN
        String actual = VersionBuilder.buildVersion(major, minor, patch, preRelease);

        // THEN
        assertThat(actual, equalTo("1.2.3"));
    }

    @Test
    public void testBuildVersionWithOnlyWhiteSpacesPreRelease() throws Exception {
        // GIVEN
        int major = 1;
        int minor = 2;
        int patch = 3;
        String preRelease = "       ";

        // WHEN
        String actual = VersionBuilder.buildVersion(major, minor, patch, preRelease);

        // THEN
        assertThat(actual, equalTo("1.2.3"));
    }
}