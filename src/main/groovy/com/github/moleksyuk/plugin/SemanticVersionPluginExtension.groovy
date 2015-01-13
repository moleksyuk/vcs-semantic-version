package com.github.moleksyuk.plugin

import org.gradle.util.ConfigureUtil


class SemanticVersionPluginExtension {

    static final String NAME = 'semanticVersion'

    def int major
    def int minor
    def String preRelease
    AccurevConfig accurev = new AccurevConfig()

    def accurev(Closure closure) {
        ConfigureUtil.configure(closure, accurev)
    }

    class AccurevConfig {
        def String stream
    }
}
