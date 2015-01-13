package com.github.moleksyuk.vcs.type

import com.github.moleksyuk.vcs.VcsType

class Mercurial implements VcsType {

    @Override
    String getCommand() {
        'hg'
    }

    @Override
    List<String> getCommandArguments() {
        ['id', '--num', '--rev', 'tip']
    }

    @Override
    String toString() {
        return 'MERCURIAL'
    }
}
