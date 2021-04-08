package org.virtuslab.ideprobe.config

import java.net.URI
import java.nio.file.{FileSystems, Files, Path, Paths, StandardCopyOption}
import java.util.Collections

import org.virtuslab.ideprobe.Extensions.PathExtension
import org.virtuslab.ideprobe.ide.intellij.{InstalledIntelliJ, IntelliJFactory, IdeProbePaths}
import org.virtuslab.ideprobe.Shell
import org.virtuslab.ideprobe.dependencies.{IntelliJVersion, InternalPlugins, Plugin}

trait IntelliJProvider {
  def setup(paths: IdeProbePaths): InstalledIntelliJ
  def cleanup(path: Path): Unit
}

case class ExistingIntelliJ(path: Path) extends IntelliJProvider {
  override def setup(paths: IdeProbePaths): Path = path
  override def cleanup(path: Path): Unit = ()
}

case class DefaultIntelliJ(path: Path, version: IntelliJVersion, plugins: Seq[Plugin], factory: IntelliJFactory) extends IntelliJProvider {
  override def setup(paths: IdeProbePaths): InstalledIntelliJ = {
    val root = factory.createInstanceDirectory(version)

    val allPlugins = InternalPlugins.probePluginForIntelliJ(version) +: plugins

    factory.installIntelliJ(version, root)
    factory.installPlugins(allPlugins, root)

    new InstalledIntelliJ(root, paths, factory.config)
  }

  override def cleanup(path: Path): Unit = ()

}

object IntelliJProvider extends IntelliJProvider {
  def from(config: IntelliJConfig): IntelliJProvider = {
    import org.virtuslab.ideprobe.dependencies.Resource._
    config match {
      case IntelliJConfig.Existing(path) => ExistingIntelliJ(path)
      case IntelliJConfig.Default(path) => DefaultIntelliJ(path)
    }
  }
}


