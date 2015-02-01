package com.github.moleksyuk.plugin

class SemanticVersionBuilder {

    private int major
    private int minor
    private int patch
    private String preRelease

    private SemanticVersionBuilder() {}

    public static SemanticVersionBuilder builder() {
        return new SemanticVersionBuilder()
    }

    public SemanticVersionBuilder setMajor(int major) {
        if (major < 0) throw new IllegalArgumentException('major can not be negative')
        this.major = major
        return this
    }

    public SemanticVersionBuilder setMinor(int minor) {
        if (minor < 0) throw new IllegalArgumentException('minor can not be negative')
        this.minor = minor
        return this
    }

    public SemanticVersionBuilder setPatch(int patch) {
        if (patch < 0) throw new IllegalArgumentException('patch can not be negative')
        this.patch = patch
        return this
    }

    public SemanticVersionBuilder setPreRelease(String preRelease) {
        this.preRelease = preRelease
        return this
    }

    public String build() {
        if (preRelease?.trim()) {
            return "${major}.${minor}.${patch}-${preRelease.trim()}"
        } else {
            return "${major}.${minor}.${patch}"
        }
    }
}
