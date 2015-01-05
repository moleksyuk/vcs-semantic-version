package com.github.moleksyuk.scm.version.builder

class VersionBuilder {

    public static String buildVersion(int major, int minor, int patch, String preRelease) {
        if (preRelease?.trim()) {
            "${major}.${minor}.${patch}-${preRelease.trim()}"
        } else {
            "${major}.${minor}.${patch}"
        }
    }
}
