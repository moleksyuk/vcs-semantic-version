package com.github.moleksyuk.vcs.impl

import com.github.moleksyuk.vcs.cmd.parser.VcsCommandOutputParser
import com.github.moleksyuk.vcs.cmd.parser.impl.CommonOutputParser
import com.github.moleksyuk.vcs.Vcs
import com.github.moleksyuk.vcs.VcsType

class Git implements Vcs {

    @Override
    VcsType getType() {
        return VcsType.GIT
    }

    @Override
    String getCommand() {
        return 'git'
    }

    @Override
    List<String> getCommandArguments() {
        return ['rev-list', 'HEAD', '--count']
    }

    @Override
    VcsCommandOutputParser getCommandOutputParser() {
        return new CommonOutputParser()
    }
}
