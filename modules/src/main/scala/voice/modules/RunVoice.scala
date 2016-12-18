package voice.modules

import java.io.File

import mvnmod.builder.Module.ConfiguredModule
import mvnmod.builder.{Module, ModuleContainer, NamedModule, PlacedRoot}


/**
  * Created by pappmar on 29/08/2016.
  */

object Place {
  val RootPath = Seq("..", "voice")
}
object RunVoice {

  val RootDir = new File(Place.RootPath.mkString("/"))

  val Roots = Seq[PlacedRoot](
    VoiceModules.Root -> RootDir
  )

  val Modules = Seq[ConfiguredModule](
    VoiceModules.Modules,
    VoiceModules.Core,
//    VoiceModules.Akka,
//    VoiceModules.Audio,
    VoiceModules.Sandbox,
    VoiceModules.Storage,
    VoiceModules.Tools,
    VoiceModules.Common,
    VoiceModules.Jni,
    VoiceModules.Environment,
    VoiceApiModules.UpdateClientInfo,
    VoiceModules.Central,
    VoiceModules.Client,
    VoiceModules.Request,
//    VoiceModules.Packaging,
//    VoiceModules.Android,
//    VoiceModules.Standalone,
//    VoiceModules.Jni,
    VoiceModules.Testing,
//    VoiceModules.Swing,
    VoiceRpiModules.Home,
    VoiceRpiModules.Mobile,
    VoiceRpiModules.Exec,
    VoiceRequestModules.Central,
    VoiceRequestModules.CompileRpi,
    VoiceRequestModules.Common,
    VoiceRequestModules.JnaRequests,
    LinuxModules.Common,
    LinuxModules.Generator,
    LinuxModules.JnaLib,
    LinuxModules.Testing,
    VoiceAndroidModules.App,
    VoiceAndroidModules.Packaging
//    VoiceRpiModules.Core
  )

  def main(args: Array[String]): Unit = {

    Module.generate(
      Roots,
      Modules
    )

  }

  def projectDir(module: ModuleContainer) : File = {
    module.path.tail.foldLeft(RootDir)(new File(_, _))
  }

  def projectDir(module: NamedModule) : File = {
    new File(projectDir(module.container), module.name)
  }


}
