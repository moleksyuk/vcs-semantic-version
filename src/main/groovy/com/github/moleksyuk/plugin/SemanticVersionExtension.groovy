package com.github.moleksyuk.plugin

import org.gradle.util.ConfigureUtil


class SemanticVersionExtension {
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
