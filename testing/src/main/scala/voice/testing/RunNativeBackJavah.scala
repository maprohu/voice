package voice.testing

import voice.jni.NativeBack

/**
  * Created by pappmar on 06/12/2016.
  */
object RunNativeBackJavah {
  def main(args: Array[String]): Unit = {
    import ammonite.ops._

    import ImplicitWd._

//    %("javah", "-h")

    %(
      "javah",
      "-cp", "../voice/jni/target/classes",
      "-o", "../voice/jni/src/main/c/nativeback.h",
      classOf[NativeBack].getName
    )
    %(
      "gcc",
      "-c", "../voice/jni/src/main/c/nativeback.c",
      "-IC:\\Oracle\\wls\\jdk8\\include",
      "-IC:\\Oracle\\wls\\jdk8\\include\\win32",
      "-D", """__int64=long long""",
//      "-mno-cygwin",
      "-Wl,--add-stdcall-alias",
      "-shared",
//      "-o", "../voice/jni/target/nativeback.dll"
      "-o", "c:/oracle/temp/nativeback.dll"
    )

  }

}
