package com.gomvp.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class DependenciesPlugin implements Plugin<Project> {

    public static final String s = "123"
    void apply(Project project) {
        project.extensions.create('libs', Libs)
        project.extensions.create('Versions', Version)
        project.extensions.create('Configs', Config)

        project.extensions.dds = '123'
        //这里实现plugin的逻辑
        //巴拉巴拉巴拉
        println "hello, this is cooker plugin!"
        //cooker-plugin
        //比如这里加一个简单的task
        project.task('cooker-test-task') << {
            println deps.libs.okhttp
        }
    }
}