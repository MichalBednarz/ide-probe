package org.virtuslab.ideprobe.ide.intellij

import java.nio.file.Path
import org.virtuslab.ideprobe.Extensions._
import org.virtuslab.ideprobe.OS

final class IntelliJPaths(root: Path, headless: Boolean) {
  val config: Path = root.createDirectory("config")
  val system: Path = root.createDirectory("system")
  val plugins: Path = root.createDirectory("plugins")
  val logs: Path = system.createDirectory("logs")
  val bin: Path = root.resolve("bin")
  val userPrefs: Path = {
    val path = root.resolve("prefs")
    OS.Current match {
      case OS.Windows =>
      case _ => IntellijPrivacyPolicy.installAgreementIn(path)
    }
    path
  }

  val executable: Path = {
      val launcher: Path = OS.Current match {
        case OS.Windows => bin.resolve("idea.bat")
        case _ => bin.resolve("idea.sh").makeExecutable()
      }

      val command =
        if (headless) s"$launcher headless" // windows ??
        else {
          Display.Mode match {
            case Display.Native => s"$launcher"
            case Display.Xvfb if OS.Current == OS.Windows => throw new Exception("Xvfb is not supported on Windows")
            case Display.Xvfb => s"xvfb-run --server-num=${Display.XvfbDisplayId} $launcher"
          }
        }

      OS.Current match {
        case OS.Windows => launcher
        case _ => {
          val content = s"""|#!/bin/sh
              |$command "$$@"
              |""".stripMargin

          bin
            .resolve("idea")
            .write(content)
            .makeExecutable()
        }
      }
  }
}
