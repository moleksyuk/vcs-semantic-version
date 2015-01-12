package com.github.moleksyuk.vcs.postprocessor

import com.github.moleksyuk.SemanticVersionGradleScriptException
import com.github.moleksyuk.vcs.VcsCommandPostProcessor


abstract class AbstractPostProcessor implements VcsCommandPostProcessor {

    @Override
    Integer postProcess(String commandOutput) {
        try {
            commandOutput.toInteger()
        } catch (def e) {
            throw new SemanticVersionGradleScriptException("Unable to convert '${commandOutput}' to Integer.", e)
        }
    }
}
