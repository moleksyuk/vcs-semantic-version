package com.github.moleksyuk.vcs.cmd.parser.impl

import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.cmd.parser.VcsCommandOutputParser

class CommonOutputParser implements VcsCommandOutputParser {

    @Override
    Integer parse(String commandOutput) {
        try {
            commandOutput.toInteger()
        } catch (def e) {
            throw new SemanticVersionGradleScriptException("Unable to convert '${commandOutput}' to Integer.", e)
        }
    }
}
