package com.github.moleksyuk.util

import com.github.moleksyuk.util.SemanticVersionBuilder
import org.junit.Test

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

public class SemanticVersionBuilderTest {

    @Test
    public void testBuildVersion() throws Exception {
        // GIVEN
        SemanticVersionBuilder semanticVersionBuilder = new SemanticVersionBuilder();
        semanticVersionBuilder.setMajor(1);
        semanticVersionBuilder.setMinor(2);
        semanticVersionBuilder.setPatch(3);
        semanticVersionBuilder.setPreRelease("alpha.1");

        // WHEN
        String actual = semanticVersionBuilder.buildVersion();

        // THEN
        assertThat(actual, equalTo("1.2.3-alpha.1"));
    }

    @Test
    public void testBuildVersionWithWhiteSpacesPreRelease() throws Exception {
        // GIVEN
        SemanticVersionBuilder semanticVersionBuilder = new SemanticVersionBuilder();
        semanticVersionBuilder.setMajor(1);
        semanticVersionBuilder.setMinor(2);
        semanticVersionBuilder.setPatch(3);
        semanticVersionBuilder.setPreRelease("   alpha.1   ");

        // WHEN
        String actual = semanticVersionBuilder.buildVersion();

        // THEN
        assertThat(actual, equalTo("1.2.3-alpha.1"));
    }

    @Test
    public void testBuildVersionWithNullPreRelease() throws Exception {
        // GIVEN
        SemanticVersionBuilder semanticVersionBuilder = new SemanticVersionBuilder();
        semanticVersionBuilder.setMajor(1);
        semanticVersionBuilder.setMinor(2);
        semanticVersionBuilder.setPatch(3);

        // WHEN
        String actual = semanticVersionBuilder.buildVersion();

        // THEN
        assertThat(actual, equalTo("1.2.3"));
    }

    @Test
    public void testBuildVersionWithEmptyPreRelease() throws Exception {
        // GIVEN
        SemanticVersionBuilder semanticVersionBuilder = new SemanticVersionBuilder();
        semanticVersionBuilder.setMajor(1);
        semanticVersionBuilder.setMinor(2);
        semanticVersionBuilder.setPatch(3);
        semanticVersionBuilder.setPreRelease("");

        // WHEN
        String actual = semanticVersionBuilder.buildVersion();

        // THEN
        assertThat(actual, equalTo("1.2.3"));
    }

    @Test
    public void testBuildVersionWithOnlyWhiteSpacesPreRelease() throws Exception {
        // GIVEN
        SemanticVersionBuilder semanticVersionBuilder = new SemanticVersionBuilder();
        semanticVersionBuilder.setMajor(1);
        semanticVersionBuilder.setMinor(2);
        semanticVersionBuilder.setPatch(3);
        semanticVersionBuilder.setPreRelease("      ");

        // WHEN
        String actual = semanticVersionBuilder.buildVersion();

        // THEN
        assertThat(actual, equalTo("1.2.3"));
    }

    @Test
    public void testBuildVersionWithoutAnyParams() throws Exception {
        // GIVEN
        SemanticVersionBuilder semanticVersionBuilder = new SemanticVersionBuilder();

        // WHEN
        String actual = semanticVersionBuilder.buildVersion();

        // THEN
        assertThat(actual, equalTo("0.0.0"));
    }
}