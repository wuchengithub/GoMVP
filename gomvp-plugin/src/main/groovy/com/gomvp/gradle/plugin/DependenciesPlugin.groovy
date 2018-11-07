package com.gomvp.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class DependenciesPlugin implements Plugin<Project> {

    public static final String s = "123"
    void apply(Project project) {
        project.extensions.create('libs', Libs)
        project.extensions.create('Versions', Version)
        project.extensions.create('Configs', Config)
    }
}