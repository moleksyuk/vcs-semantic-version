package com.github.moleksyuk.vcs.postprocessor

import com.github.moleksyuk.SemanticVersionGradleScriptException


class AccurevPostProcessor extends AbstractPostProcessor {

    @Override
    Integer postProcess(String commandOutput) {
        if (commandOutput.startsWith('transaction')) {
            super.postProcess(commandOutput.split(';')[0].split(' ')[1])
        } else {
            throw new SemanticVersionGradleScriptException("Expected string with format 'transaction [transactionId]; promote; [timestamp] ; user: [username]' but was: '${commandOutput}'");
        }
    }
}
