package com.github.moleksyuk.vcs.type

import com.github.moleksyuk.vcs.VcsType

class Accurev implements VcsType {

    @Override
    String getCommand() {
        'accurev'
    }

    @Override
    List<String> getCommandArguments() {
        ['hist', '-t', 'highest', '-ft']
    }

    @Override
    String toString() {
        return 'ACCUREV'
    }
}
