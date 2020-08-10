package org.virtuslab.intellij.extensions

import org.virtuslab.ProbeHandlerContributor
import org.virtuslab.ProbeHandlers.ProbeHandler

class SbtProbeHandlerContributor extends ProbeHandlerContributor {
  override def registerHandlers(handler: ProbeHandler): ProbeHandler = {
    handler
    //      .on(SbtEndpoints.ImportSbtProject)((SbtImport.importProject _).tupled)
      .on(SbtEndpoints.ChangeSbtProjectSettings)((SbtSettings.changeProjectSettings _).tupled)
      .on(SbtEndpoints.GetSbtProjectSettings)(SbtSettings.getProjectSettings)
  }
}
