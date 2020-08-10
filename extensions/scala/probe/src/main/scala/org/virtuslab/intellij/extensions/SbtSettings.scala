package org.virtuslab.intellij.extensions

import java.util.Optional

import com.intellij.openapi.externalSystem.model.ProjectSystemId
import com.intellij.openapi.externalSystem.util.ExternalSystemApiUtil
import com.intellij.openapi.project.Project
import org.jetbrains.sbt.project.settings.{SbtProjectSettings => SbtProjectSettingsFromPlugin}
import org.virtuslab.handlers.{BackgroundTasks, Projects}
import org.virtuslab.ideprobe.protocol.{ProjectRef, Setting}

object SbtSettings {
  def getProjectSettings(ref: ProjectRef): SbtProjectSettings = {
    val project = Projects.resolve(ref)
    val sbtSettings = getSbtSettings(project)

    SbtProjectSettings(
      useSbtShellForImport = sbtSettings.getUseSbtShellForImport,
      useSbtShellForBuild = sbtSettings.getUseSbtShellForBuild,
      allowSbtVersionOverride = sbtSettings.getAllowSbtVersionOverride
    )
  }

  def changeProjectSettings(ref: ProjectRef, toSet: SbtProjectSettingsChangeRequest): Unit =
    BackgroundTasks.withAwaitNone {
      val project = Projects.resolve(ref)
      val sbtSettings = getSbtSettings(project)

      def setSetting[A](setting: Setting[A])(f: (SbtProjectSettingsFromPlugin, A) => Unit): Unit = {
        setting.foreach(value => f(sbtSettings, value))
      }

      setSetting(toSet.useSbtShellForImport)(_.setUseSbtShellForImport(_))
      setSetting(toSet.useSbtShellForBuild)(_.setUseSbtShellForBuild(_))
      setSetting(toSet.allowSbtVersionOverride)(_.setAllowSbtVersionOverride(_))
    }

  private def getSbtSettings(project: Project) = {
    ExternalSystemApiUtil
      .getSettings(project, new ProjectSystemId("SBT", "sbt"))
      .asInstanceOf[SbtProjectSettingsFromPlugin]
  }

  private implicit class OptionalOps[A <: AnyRef](o: Optional[A]) {
    def asScala: Option[A] = Option(o.orElse(null.asInstanceOf[A]))
  }

  private implicit class OptionOps[A](o: Option[A]) {
    def asJava: Optional[A] = o.fold(Optional.empty[A])(Optional.of)
  }
}