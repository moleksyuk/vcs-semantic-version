package com.github.moleksyuk.vcs.version.builder

class SemanticVersionBuilder {
    private int major;
    private int minor;
    private int patch;
    private String preRelease;


    SemanticVersionBuilder setMajor(int major) {
        this.major = major
        return this
    }

    SemanticVersionBuilder setMinor(int minor) {
        this.minor = minor
        return this
    }

    SemanticVersionBuilder setPatch(int patch) {
        this.patch = patch
        return this
    }

    SemanticVersionBuilder setPreRelease(String preRelease) {
        this.preRelease = preRelease
        return this
    }

    String buildVersion() {
        if (preRelease?.trim()) {
            "${major}.${minor}.${patch}-${preRelease.trim()}"
        } else {
            "${major}.${minor}.${patch}"
        }
    }
}
