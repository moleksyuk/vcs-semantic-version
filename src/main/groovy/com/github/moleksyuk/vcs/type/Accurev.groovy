package com.github.moleksyuk.vcs.type

import com.github.moleksyuk.vcs.VcsType

class Accurev implements VcsType {

    @Override
    String getCommand() {
        'accurev'
    }

    @Override
    List<String> getCommandArguments() {
        // TODO: add required properties
        []
    }
}