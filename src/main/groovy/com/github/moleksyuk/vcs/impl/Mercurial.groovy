package com.github.moleksyuk.vcs.impl

import com.github.moleksyuk.vcs.cmd.parser.VcsCommandOutputParser
import com.github.moleksyuk.vcs.cmd.parser.impl.CommonOutputParser
import com.github.moleksyuk.vcs.Vcs
import com.github.moleksyuk.vcs.VcsType

class Mercurial implements Vcs {

    @Override
    VcsType getType() {
        return VcsType.MERCURIAL
    }

    @Override
    String getCommand() {
        return 'hg'
    }

    @Override
    List<String> getCommandArguments() {
        return ['id', '--num', '--rev', 'tip']
    }

    @Override
    VcsCommandOutputParser getCommandOutputParser() {
        return new CommonOutputParser()
    }
}
