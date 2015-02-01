package com.github.moleksyuk.vcs.impl

import com.github.moleksyuk.vcs.Vcs
import com.github.moleksyuk.vcs.VcsType
import com.github.moleksyuk.vcs.cmd.parser.VcsCommandOutputParser
import com.github.moleksyuk.vcs.cmd.parser.impl.AccurevOutputParser

class Accurev implements Vcs {

    private final List<String> commandArguments;

    public Accurev(String stream) {
        if (!stream?.trim()) throw IllegalArgumentException('stream can not be null')
        this.commandArguments = ['hist', '-ft', '-t', 'highest', '-s', stream]
    }

    @Override
    VcsType getType() {
        return VcsType.ACCUREV
    }

    @Override
    String getCommand() {
        return 'accurev'
    }

    @Override
    List<String> getCommandArguments() {
        return commandArguments
    }

    @Override
    VcsCommandOutputParser getCommandOutputParser() {
        return new AccurevOutputParser()
    }
}
