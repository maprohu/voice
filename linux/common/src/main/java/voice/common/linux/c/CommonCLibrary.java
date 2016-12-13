package voice.common.linux.c;

import com.ochafik.lang.jnaerator.runtime.LibraryExtractor;
import com.ochafik.lang.jnaerator.runtime.MangledFunctionMapper;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.PointerByReference;

/**
 * Created by pappmar on 13/12/2016.
 */
public interface CommonCLibrary extends Library {
    public static final String JNA_LIBRARY_NAME = LibraryExtractor.getLibraryPath((com.sun.jna.Platform.isWindows() ? "msvcrt" : "c"), true, CommonCLibrary.class);
    public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(CommonCLibrary.JNA_LIBRARY_NAME, MangledFunctionMapper.DEFAULT_OPTIONS);
    public static final CommonCLibrary INSTANCE = (CommonCLibrary) Native.loadLibrary(CommonCLibrary.JNA_LIBRARY_NAME, CommonCLibrary.class, MangledFunctionMapper.DEFAULT_OPTIONS);

    /**
     * Give the socket FD the local address ADDR (which is LEN bytes long).<br>
     * Original signature : <code>int bind(int, sockaddr*, socklen_t)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\sys\socket.h:118</i>
     */
    int bind(int __fd, Pointer __addr, int __len);
}
