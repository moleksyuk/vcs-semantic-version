package com.github.moleksyuk.vcs.cmd.parser.impl

import com.github.moleksyuk.SemanticVersionGradleScriptException

class AccurevOutputParser extends CommonOutputParser {

    @Override
    Integer parse(String commandOutput) {
        if (commandOutput.startsWith('transaction')) {
            super.parse(commandOutput.split(';')[0].split(' ')[1])
        } else {
            throw new SemanticVersionGradleScriptException("Expected string with format 'transaction [transactionId]; promote; [timestamp] ; user: [username]' but was: '${commandOutput}'");
        }
    }
}
