package voice.modules

import java.io.File

import maven.modules.builder.Module.ConfiguredModule
import maven.modules.builder.{Module, ModuleContainer, NamedModule, PlacedRoot}

/**
  * Created by pappmar on 29/08/2016.
  */
object RunVoice {

  val RootDir = new File("../voice")

  val Roots = Seq[PlacedRoot](
    VoiceModules.Root -> RootDir
  )

  val Modules = Seq[ConfiguredModule](
    VoiceModules.Modules,
    VoiceModules.Core,
    VoiceModules.Audio,
    VoiceModules.Sandbox,
    VoiceModules.Tools,
    VoiceModules.Packaging,
    VoiceModules.Android,
    VoiceModules.Standalone,
    VoiceModules.Testing,
    VoiceModules.Swing,
    VoiceRpiModules.Home,
    VoiceRpiModules.Mobile,
    VoiceRpiModules.Exec,
    VoiceRpiModules.Core
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
