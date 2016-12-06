package voice.jni;

/**
 * Created by pappmar on 06/12/2016.
 */
public class NativeBack {

    public native void recall(String value);

    public static void callback(String value) {
        System.out.println(value);
    }

}
