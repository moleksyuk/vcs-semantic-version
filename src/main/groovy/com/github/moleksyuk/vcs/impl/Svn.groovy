package com.github.moleksyuk.vcs.impl

import com.github.moleksyuk.vcs.Vcs
import com.github.moleksyuk.vcs.VcsType
import com.github.moleksyuk.vcs.cmd.parser.VcsCommandOutputParser
import com.github.moleksyuk.vcs.cmd.parser.impl.CommonOutputParser

class Svn implements Vcs {

    @Override
    VcsType getType() {
        return VcsType.SVN
    }

    @Override
    String getCommand() {
        return 'svnversion'
    }

    @Override
    List<String> getCommandArguments() {
        return ['.']
    }

    @Override
    VcsCommandOutputParser getCommandOutputParser() {
        return new CommonOutputParser()
    }
}
