package com.github.moleksyuk.vcs

import com.github.moleksyuk.vcs.cmd.parser.VcsCommandOutputParser

interface Vcs {

    VcsType getType()

    String getCommand()

    List<String> getCommandArguments()

    VcsCommandOutputParser getCommandOutputParser()
}