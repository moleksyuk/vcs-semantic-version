package com.github.moleksyuk.plugin

import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

class SemanticVersionBuilderTest {

    @Test
    public void testBuildWithDefaults() {
        // GIVEN

        // WHEN
        def actual = SemanticVersionBuilder.builder().build()

        // THEN
        assertThat(actual, Matchers.equalTo('0.0.0'))
    }

    @Test
    public void testBuildWithMajor() {
        // GIVEN
        def major = 1

        // WHEN
        def actual = SemanticVersionBuilder.builder()
                .setMajor(major)
                .build()

        // THEN
        assertThat(actual, Matchers.equalTo('1.0.0'))
    }

    @Test
    public void testBuildWithMajorAndMinor() {
        // GIVEN
        def major = 1
        def minor = 2

        // WHEN
        def actual = SemanticVersionBuilder.builder()
                .setMajor(major)
                .setMinor(minor)
                .build()

        // THEN
        assertThat(actual, Matchers.equalTo('1.2.0'))
    }

    @Test
    public void testBuildWithMajorAndMinorAndPatch() {
        // GIVEN
        def major = 1
        def minor = 2
        def patch = 3

        // WHEN
        def actual = SemanticVersionBuilder.builder()
                .setMajor(major)
                .setMinor(minor)
                .setPatch(patch)
                .build()

        // THEN
        assertThat(actual, Matchers.equalTo('1.2.3'))
    }

    @Test
    public void testBuildWithMajorAndMinorAndPatchAndEmptyPreRelease() {
        // GIVEN
        def major = 1
        def minor = 2
        def patch = 3
        def preRelease = '   '

        // WHEN
        def actual = SemanticVersionBuilder.builder()
                .setMajor(major)
                .setMinor(minor)
                .setPatch(patch)
                .setPreRelease(preRelease)
                .build()

        // THEN
        assertThat(actual, Matchers.equalTo('1.2.3'))
    }

    @Test
    public void testBuildWithMajorAndMinorAndPatchAndPreRelease() {
        // GIVEN
        def major = 1
        def minor = 2
        def patch = 3
        def preRelease = 'preRelease'

        // WHEN
        def actual = SemanticVersionBuilder.builder()
                .setMajor(major)
                .setMinor(minor)
                .setPatch(patch)
                .setPreRelease(preRelease)
                .build()

        // THEN
        assertThat(actual, Matchers.equalTo('1.2.3-preRelease'))
    }
}
