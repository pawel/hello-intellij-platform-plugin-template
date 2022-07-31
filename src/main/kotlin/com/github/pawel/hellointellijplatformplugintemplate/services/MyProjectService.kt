package com.github.pawel.hellointellijplatformplugintemplate.services

import com.intellij.openapi.project.Project
import com.github.pawel.hellointellijplatformplugintemplate.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
