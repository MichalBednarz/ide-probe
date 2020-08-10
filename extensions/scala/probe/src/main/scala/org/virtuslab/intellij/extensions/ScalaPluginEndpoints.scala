package org.virtuslab.intellij.extensions

import java.nio.file.Path

import org.virtuslab.ideprobe.jsonrpc.JsonRpc.Method.Request
import org.virtuslab.ideprobe.protocol.ProjectRef
import org.virtuslab.ideprobe.jsonrpc.PayloadJsonFormat._
import pureconfig.generic.auto._

object SbtEndpoints {
  val ImportSbtProject =
    Request[(Path, SbtProjectSettingsChangeRequest), ProjectRef]("sbt/project/import")

  val GetSbtProjectSettings =
    Request[ProjectRef, SbtProjectSettings]("sbt/project/settings/get")

  val ChangeSbtProjectSettings =
    Request[(ProjectRef, SbtProjectSettingsChangeRequest), Unit]("sbt/project/settings/change")
}
