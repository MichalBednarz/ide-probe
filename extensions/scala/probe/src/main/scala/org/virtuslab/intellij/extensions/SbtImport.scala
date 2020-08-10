package org.virtuslab.intellij.extensions

import java.nio.file.Path

import com.intellij.openapi.externalSystem.service.project.wizard.SelectExternalProjectStep
import com.intellij.projectImport.{ImportChooserStep, ProjectImportProvider}
import com.intellij.ui.CheckBoxList
import com.intellij.ui.components.{JBCheckBox, JBList, JBRadioButton}
import javax.swing.JSpinner
import org.virtuslab.handlers.{IntelliJApi, Projects}
import org.virtuslab.ideprobe.protocol.{ProjectRef, Setting}
import org.virtuslab.ideprobe.protocol.Setting.{Changed, Unchanged}

class SbtImport extends IntelliJApi {
//  def importProject(path: Path, settings: SbtProjectSettingsChangeRequest): ProjectRef = {
//    Projects.importFromSources(
//      path, {
//        case step: ImportChooserStep =>
//          selectSbtImportModel(step)
//        case step: SelectExternalProjectStep =>
//          configureSbtSettings(step, settings)
//      }
//    )
//  }
//
//  private def selectSbtImportModel(step: ImportChooserStep): Unit = {
//    val importCheckbox = step.field[JBRadioButton]("importFrom")
//    importCheckbox.setSelected(true)
//
//    val providersList = step.field[JBList[ProjectImportProvider]]("list")
//    val pants = providersList.items
//      .collectFirst { case p: PantsProjectImportProvider => p }
//      .getOrElse(error(s"Could not find pants import provider. Available providers are ${providersList.items}"))
//    providersList.setSelectedValue(pants, false)
//  }
//
//  private def configureSbtSettings(
//      step: SelectExternalProjectStep,
//      settings: SbtProjectSettingsChangeRequest
//  ): Unit = {
//    val importFromPantsControl = step.field[ImportFromPantsControl]("control")
//    val pantsProjectSettingsControl =
//      importFromPantsControl.field[PantsProjectSettingsControl]("projectSettingsControl")
//
//    def checkBox(setting: Setting[Boolean], checkBoxName: String): Unit = {
//      setting.foreach { value =>
//        val cb = pantsProjectSettingsControl.field[JBCheckBox](checkBoxName)
//        cb.setSelected(value)
//      }
//    }
//
//    def checkBoxList(setting: Setting[Seq[String]], listName: String): Unit = {
//      setting.foreach { toCheck =>
//        val list = pantsProjectSettingsControl.field[CheckBoxList[String]](listName)
//        list.items.foreach { item =>
//          list.setItemSelected(item, toCheck.contains(item))
//        }
//      }
//    }
//
//    def intSpinner(setting: Setting[Int], spinnerName: String): Unit = {
//      setting.foreach { value =>
//        val spinner = pantsProjectSettingsControl.field[JSpinner](spinnerName)
//        spinner.setValue(value)
//      }
//    }
//
//    checkBox(settings.incrementalProjectImportDepth.map(_.isDefined), "enableIncrementalImportCheckBox")
//    checkBox(settings.loadSourcesAndDocsForLibs, "libsWithSourcesCheckBox")
//    checkBox(settings.useIdeaProjectJdk, "useIdeaProjectJdkCheckBox")
//    checkBox(settings.importSourceDepsAsJars, "importSourceDepsAsJarsCheckBox")
//    checkBox(settings.useIntellijCompiler, "useIntellijCompilerCheckBox")
//    checkBoxList(settings.selectedTargets, "targetSpecsBox")
//    intSpinner(settings.incrementalProjectImportDepth.flatten, "incrementalImportDepth")
//  }
//
//  private implicit class OptionalSetting[A](val s: Setting[Option[A]]) extends AnyVal {
//    def flatten: Setting[A] = s match {
//      case Unchanged            => Unchanged
//      case Changed(Some(value)) => Changed(value)
//      case Changed(None)        => Unchanged
//    }
//  }
}
