package com.github.moleksyuk.gradle.vcs.type

import com.github.moleksyuk.gradle.vcs.VcsType

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
