package org.virtuslab.ideprobe.config

import java.nio.file.Path

import org.virtuslab.ideprobe.ConfigFormat
import org.virtuslab.ideprobe.dependencies.{IntelliJVersion, Plugin, Resource}
import pureconfig.ConfigReader
import pureconfig.generic.semiauto.deriveReader

case class IntellijConfig(
    version: IntelliJVersion = IntelliJVersion.Latest,
    plugins: Seq[Plugin] = Seq.empty
)

sealed trait IntelliJConfig

object IntelliJConfig extends ConfigFormat {
  case class Default(path: Resource) extends IntelliJConfig

  case class Existing(existing: Path) extends IntelliJConfig

  implicit val intelliJConfigReader: ConfigReader[IntelliJConfig] = {
    possiblyAmbiguousAdtReader[IntelliJConfig](
      deriveReader[Default],
      deriveReader[Existing]
    )
  }
}
