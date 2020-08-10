package org.virtuslab.intellij.extensions

import java.nio.file.Path

import org.virtuslab.ideprobe.ProbeDriver
import org.virtuslab.ideprobe.protocol.ProjectRef

object SbtProbeDriver {
  val pluginId = "org.virtuslab.ideprobe.scalaplugin"

  def apply(driver: ProbeDriver): SbtProbeDriver = driver.as(pluginId, new SbtProbeDriver(_))
}

final class SbtProbeDriver(val driver: ProbeDriver) extends AnyVal {
  def importProject(path: Path, settings: SbtProjectSettingsChangeRequest): ProjectRef = {
    driver.send(SbtEndpoints.ImportSbtProject, (path, settings))
  }

  def getSbtProjectSettings(project: ProjectRef = ProjectRef.Default): SbtProjectSettings = {
    driver.send(SbtEndpoints.GetSbtProjectSettings, project)
  }

  def setSbtProjectSettings(
      settings: SbtProjectSettingsChangeRequest,
      project: ProjectRef = ProjectRef.Default
  ): Unit = {
    driver.send(SbtEndpoints.ChangeSbtProjectSettings, (project, settings))
  }

//  def compileAllTargets(): Unit = {
//    driver.invokeAction("com.twitter.intellij.pants.compiler.actions.PantsCompileAllTargetsAction")
//    val compiledNotification = driver.awaitNotification("Compile message")
//
//    if (compiledNotification.severity != IdeNotification.Severity.Info) {
//      throw new IllegalStateException("Compilation failed")
//    }
//  }
}
