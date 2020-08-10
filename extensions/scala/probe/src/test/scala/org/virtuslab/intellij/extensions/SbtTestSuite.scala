package org.virtuslab.intellij.extensions

import org.virtuslab.ideprobe.{BuildInfo, ProbeDriver}
import org.virtuslab.ideprobe.dependencies.Plugin

class SbtTestSuite {

  val scalaProbePlugin: Plugin = Plugin.Bundled(s"ideprobe-scalaplugin-${BuildInfo.version}.zip")

//  override def transformFixture(fixture: IntelliJFixture): IntelliJFixture = {
//    fixture
//      .copy(plugins = scalaProbePlugin +: fixture.plugins)
//      .withAfterWorkspaceSetup(SbtSetup.overridePantsVersion)
//  }

  implicit def sbtProbeDriver(driver: ProbeDriver): SbtProbeDriver = SbtProbeDriver(driver)
}
