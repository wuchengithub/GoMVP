# GoMVP
MVP for Android，Based to the AOP.


## Release

1.3.4

## proguard-rules
-keep class com.wookii.gomvp.**{*;}

## How to Use

### 1、添加依赖与插件
主工程中gradle添加:

    buildscript {
        dependencies {
            classpath 'com.android.tools.build:gradle:3.1.3'
            classpath 'com.wookii.plugin:aspectj-plugin:1.0.1'//AOP
        }
    }
    
app工程中gradle：

    apply plugin: 'com.android.application'
    //添加plugin,基于AOP
    apply plugin: 'wookii-plugin-aspectj'

app添加GoMVP依赖：

    implementation 'com.wookii.gomvp:gomvp:1.3.4'


app添加AOP配置：

    android{
        .
        .
        .
        aspectjx {
            //AOP时，排除所有package路径中包含`android.support`的class文件及库（jar文件）
            exclude 'android.support'
        }
    }
