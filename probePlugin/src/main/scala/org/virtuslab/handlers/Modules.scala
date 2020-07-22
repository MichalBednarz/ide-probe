package org.virtuslab.handlers

import java.nio.file.{Path, Paths}

import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ModuleRootManager
import org.jetbrains.jps.model.ex.JpsElementTypeBase
import org.jetbrains.jps.model.java.JavaSourceRootType._
import org.jetbrains.jps.model.java.JavaResourceRootType._
import org.jetbrains.jps.model.module.JpsModuleSourceRootType
import org.virtuslab.ideprobe.protocol.{ModuleRef, ProjectRef}

import scala.collection.mutable

object Modules extends IntelliJApi {
  def resolve(module: ModuleRef): (Project, Module) = {
    val project = Projects.resolve(module.project)
    val modules = ModuleManager.getInstance(project).getModules

    modules.find(_.getName == module.name) match {
      case Some(module) =>
        (project, module)
      case None =>
        val helpMessage =
          if (modules.isEmpty) "There are no open modules"
          else s"Available modules are: ${modules.map(_.getName).mkString(",")}"

        error(s"Could not find module [${module.name}] inside project [${module.name}]. $helpMessage")

    }
  }

  def sdk(moduleRef: ModuleRef): Option[String] = {
    val (_, module) = resolve(moduleRef)
    val sdk = ModuleRootManager.getInstance(module).getSdk
    Option(sdk).map(_.getName)
  }

  def sources(moduleRef: ModuleRef): Array[Path] = contentRoots(moduleRef, SOURCE)
  def resources(moduleRef: ModuleRef): Array[Path] = contentRoots(moduleRef, RESOURCE)
  def testSources(moduleRef: ModuleRef): Array[Path] = contentRoots(moduleRef, TEST_SOURCE)
  def testResources(moduleRef: ModuleRef): Array[Path] = contentRoots(moduleRef, TEST_RESOURCE)

  private def contentRoots(moduleRef: ModuleRef, kind: JpsModuleSourceRootType[_]): Array[Path] = {
    import org.virtuslab.ideprobe.Extensions._
    val (_, module) = resolve(moduleRef)
    val roots = ModuleRootManager.getInstance(module)
    roots.getSourceRoots(kind).asScala.map(_.getPath).map(Paths.get(_)).toArray
  }

  def dependencies(moduleRef: ModuleRef): Array[ModuleRef] = {
    val (_, module) = resolve(moduleRef)
    val dependencies = ModuleRootManager.getInstance(module).getDependencies
    dependencies.map { module =>
      val project = module.getProject
      ModuleRef(module.getName, ProjectRef(project.getName))
    }
  }
}
