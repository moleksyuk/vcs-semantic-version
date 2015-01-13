package com.github.moleksyuk.vcs.parser

import com.github.moleksyuk.SemanticVersionGradleScriptException
import org.hamcrest.Matchers
import org.junit.Test

import static org.junit.Assert.assertThat

public class AccurevOutputParserTest {

    @Test
    public void testParseValidStringFormat() throws Exception {
        // GIVEN
        def output = 'transaction 9619; chstream; 2015/01/12 13:48:11 ; user: devavratt.bagayat'
        def parser = new AccurevOutputParser()

        // WHEN
        def actual = parser.parse(output)

        // THEN
        assertThat(actual, Matchers.equalTo(9619))
    }

    @Test(expected = SemanticVersionGradleScriptException)
    public void testParseInvalidStringFormat() throws Exception {
        // GIVEN
        def output = '''Principal:\t(not logged in)
                            Host:\t\tnb-myol.kyiv.ciklum.net
                            Server name:\taccurev
                            Port:\t\t5050
                            DB Encoding:\tUnicode
                            ACCUREV_BIN:\tC:/Program Files (x86)/AccuRev/bin
                            Client time:\t2015/01/12 17:15:57 FLE Standard Time (1421075757)
                            Server time:\t2015/01/12 17:15:57 FLE Standard Time (1421075757)'''
        def parser = new AccurevOutputParser()

        // WHEN
        parser.parse(output)

        // THEN
    }
}