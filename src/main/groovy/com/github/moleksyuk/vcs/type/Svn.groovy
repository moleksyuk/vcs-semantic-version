package com.github.moleksyuk.vcs.type

import com.github.moleksyuk.vcs.VcsType

class Svn implements VcsType {

    @Override
    String getCommand() {
        'svnversion'
    }

    @Override
    List<String> getCommandArguments() {
        ['.']
    }
}
