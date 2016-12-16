package voice.common.linux.c;

import com.ochafik.lang.jnaerator.runtime.LibraryExtractor;
import com.ochafik.lang.jnaerator.runtime.MangledFunctionMapper;
import com.sun.jna.*;

import java.nio.IntBuffer;

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
    int bind(int __fd, Structure.ByReference __addr, int __len);
    /**
     * Await a connection on socket FD.<br>
     * When a connection arrives, open a new socket to communicate with it,<br>
     * set *ADDR (which is *ADDR_LEN bytes long) to the address of the connecting<br>
     * peer and *ADDR_LEN to the address's actual length, and return the<br>
     * new socket's descriptor, or -1 for errors.<br>
     * This function is a cancellation point and therefore not marked with<br>
     * __THROW.<br>
     * Original signature : <code>int accept(int, sockaddr*, socklen_t*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\sys\socket.h:238</i>
     */
    int accept(int __fd, Structure __addr, IntBuffer __addr_len);
    /**
     * Put the local address of FD into *ADDR and its length in *LEN.<br>
     * Original signature : <code>int getsockname(int, sockaddr*, socklen_t*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\sys\socket.h:122</i>
     */
    int getsockname(int __fd, Structure __addr, IntBuffer __len);
    /**
     * Open a connection on socket FD to peer at ADDR (which LEN bytes long).<br>
     * For connectionless socket types, just set the default address to send to<br>
     * and the only address from which to accept transmissions.<br>
     * Return 0 on success, -1 for errors.<br>
     * This function is a cancellation point and therefore not marked with<br>
     * __THROW.<br>
     * Original signature : <code>int connect(int, sockaddr*, socklen_t)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\sys\socket.h:132</i>
     */
    int connect(int __fd, Structure __addr, int __len);
    /**
     * Perform the I/O control operation specified by REQUEST on FD.<br>
     * One argument may follow; its presence and type depend on REQUEST.<br>
     * Return value depends on REQUEST.  Usually -1 indicates error.<br>
     * Original signature : <code>int ioctl(int, unsigned long long, null)</code><br>
     * <i>native declaration : arm-linux-gnueabihf\sys\ioctl.h:39</i>
     */
    int ioctl(int __fd, long __request, Pointer __data);
}
