package com.github.moleksyuk.vcs.parser

import com.github.moleksyuk.SemanticVersionGradleScriptException

class BasicOutputParser implements VcsCommandOutputParser {

    @Override
    Integer parse(String commandOutput) {
        try {
            commandOutput.toInteger()
        } catch (def e) {
            throw new SemanticVersionGradleScriptException("Unable to convert '${commandOutput}' to Integer.", e)
        }
    }
}
