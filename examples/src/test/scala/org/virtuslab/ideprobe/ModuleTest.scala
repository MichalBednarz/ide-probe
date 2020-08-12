package org.virtuslab.ideprobe

import org.junit.Assert
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.virtuslab.ideprobe.protocol.{JUnitRunConfiguration, ModuleRef, Setting}
import org.virtuslab.intellij.scala.SbtTestSuite
import org.virtuslab.intellij.scala.protocol.SbtProjectSettingsChangeRequest

class ModuleTest extends SbtTestSuite {
  @ParameterizedTest
  @ValueSource(
    strings = Array("projects/shapeless/ideprobe.conf", "projects/cats/ideprobe.conf", "projects/dokka/ideprobe.conf")
  )
  def verifyModulesPresent(configName: String): Unit = fixtureFromConfig(configName).run { intelliJ =>
    deleteIdeaSettings(intelliJ)
    intelliJ.probe.openProject(intelliJ.workspace)
    intelliJ.probe.setSbtProjectSettings(
      SbtProjectSettingsChangeRequest(
        useSbtShellForImport = Setting.Changed(true),
        useSbtShellForBuild = Setting.Changed(true),
        allowSbtVersionOverride = Setting.Changed(false)
      )
    )
    val project = intelliJ.probe.projectModel()
    val projectModules = project.modules.map(_.name)
    val modulesFromConfig = intelliJ.config[Seq[String]]("modules.verify")

    val missingModules = modulesFromConfig.diff(projectModules)
    Assert.assertTrue(s"Modules $missingModules are missing", missingModules.isEmpty)
  }

  @ParameterizedTest
  @ValueSource(
    strings = Array("projects/shapeless/ideprobe.conf", "projects/cats/ideprobe.conf", "projects/dokka/ideprobe.conf")
  )
  def runTestsInModules(configName: String): Unit = fixtureFromConfig(configName).run { intelliJ =>
    deleteIdeaSettings(intelliJ)
    intelliJ.probe.openProject(intelliJ.workspace)
    val modulesFromConfig = intelliJ.config[Seq[String]]("modules.test")
    val moduleRefs = modulesFromConfig.map(ModuleRef(_))
    val runConfigs = moduleRefs.map(JUnitRunConfiguration.module)
    val result = runConfigs.map(config => config.module -> intelliJ.probe.run(config)).toMap

    Assert.assertTrue(s"Tests in modules ${result.values} failed", result.values.forall(_.isSuccess))
  }
}
