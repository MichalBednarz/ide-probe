package org.virtuslab.intellij.extensions

import org.junit.Assert.{assertFalse, assertTrue}
import org.junit.Test
import org.virtuslab.ideprobe.protocol.Setting

class SbtSettingsTest extends SbtTestSuite {
//  @Test def importProjectWithCustomSettings(): Unit = {
//    fixtureFromConfig().run { intelliJ =>
//      val importSettings =
//        SbtProjectSettingsChangeRequest(
//          useSbtShellForImport = Setting.Changed(true),
//          useSbtShellForBuild = Setting.Changed(true),
//          allowSbtVersionOverride = Setting.Changed(false)
//        )
//
//      val projectRoot = intelliJ.workspace.resolve(intelliJ.config[String]("targetPath"))
//      val project = intelliJ.probe.importProject(projectRoot, importSettings)
//
//      val initialSettings = intelliJ.probe.getPantsProjectSettings()
//
//      assertTrue("'Load sources and docs for libs' was not set", initialSettings.loadSourcesAndDocsForLibs)
//      assertTrue("'Use IntelliJ compiler' was not set", initialSettings.useIntellijCompiler)
//
//      intelliJ.probe.setPantsProjectSettings(
//        PantsProjectSettingsChangeRequest(useIntellijCompiler = Setting.Changed(false))
//      )
//
//      val finalSettings = intelliJ.probe.getPantsProjectSettings()
//      assertFalse("'Load sources and docs for libs' was not unset", finalSettings.useIntellijCompiler)
//    }
//  }
}
