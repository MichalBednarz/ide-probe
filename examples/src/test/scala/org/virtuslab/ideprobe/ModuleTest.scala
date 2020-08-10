package org.virtuslab.ideprobe

import java.nio.file.Files
import java.util.concurrent.Executors

import org.junit.Assert
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.virtuslab.ideprobe.Extensions._
import org.virtuslab.ideprobe.protocol.{BuildScope, JUnitRunConfiguration, ModuleRef, Setting}
import org.virtuslab.intellij.extensions.{SbtProbeDriver, SbtProjectSettingsChangeRequest}

import scala.concurrent.ExecutionContext

class ModuleTest extends RobotExtensions {
  protected implicit val ec: ExecutionContext = ExecutionContext.fromExecutorService(Executors.newCachedThreadPool())

  implicit def pantsProbeDriver(driver: ProbeDriver): SbtProbeDriver = SbtProbeDriver(driver)

  private def fixtureFromConfig(configName: String): IntelliJFixture =
    IntelliJFixture.fromConfig(Config.fromClasspath(configName))

  /**
   * The presence of .idea can prevent automatic import of gradle project
   */
  private def deleteIdeaSettings(intelliJ: RunningIntelliJFixture) = {
    val path = intelliJ.workspace.resolve(".idea")
    Option.when(Files.exists(path))(path.delete())
  }

  @ParameterizedTest
  @ValueSource(
//    strings = Array("projects/shapeless/ideprobe.conf", "projects/cats/ideprobe.conf", "projects/dokka/ideprobe.conf")
    strings = Array("projects/http4s/ideprobe.conf")
  )
  def verifyModulesPresent(configName: String): Unit = fixtureFromConfig(configName).run { intelliJ =>
    deleteIdeaSettings(intelliJ)
    intelliJ.probe.setSbtProjectSettings(
      SbtProjectSettingsChangeRequest(
        useSbtShellForImport = Setting.Changed(true),
        useSbtShellForBuild = Setting.Changed(true),
        allowSbtVersionOverride = Setting.Changed(false)
      )
    )

    intelliJ.probe.openProject(intelliJ.workspace)
    val project = intelliJ.probe.projectModel()
    val projectModules = project.modules.map(_.name)
    val modulesFromConfig = intelliJ.config[Seq[String]]("modules.verify")

    val missingModules = modulesFromConfig.diff(projectModules)
    Assert.assertTrue(s"Modules $missingModules are missing", missingModules.isEmpty)
  }

//  @ParameterizedTest
//  @ValueSource(
//    strings = Array("projects/metals/ideprobe.conf", "projects/cats/ideprobe.conf", "projects/dokka/ideprobe.conf")
//  )
//  def runTestsInModules(configName: String): Unit = fixtureFromConfig(configName).run { intelliJ =>
//    deleteIdeaSettings(intelliJ)
//    val projectRef = intelliJ.probe.openProject(intelliJ.workspace)
//    val modulesFromConfig = intelliJ.config[Seq[String]]("modules.test")
//    val moduleRefs = modulesFromConfig.map(ModuleRef(_))
//    val runConfigs = moduleRefs.map(JUnitRunConfiguration.module)
//    val buildRes = intelliJ.probe.build(BuildScope.modules(projectRef, modulesFromConfig: _*))
//    Assert.assertTrue("Module build failed", !buildRes.hasErrors)
//
//    val result = runConfigs.map(config => config.module -> intelliJ.probe.run(config)).toMap
//    Assert.assertTrue(s"Tests in modules ${result.values} failed", result.values.forall(_.isSuccess))
//  }
}
