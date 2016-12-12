package voice.linux.jna.c;
import org.bridj.BridJ;
import org.bridj.CRuntime;
import org.bridj.ann.Library;
import org.bridj.ann.Runtime;
/**
 * Wrapper for library <b>c</b><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("c") 
@Runtime(CRuntime.class) 
public class CLibrary {
	static {
		BridJ.register();
	}
	/** No more receptions. */
	public static final int SHUT_RD = 0;
	/** No more transmissions. */
	public static final int SHUT_WR = 1;
	/** No more receptions or transmissions. */
	public static final int SHUT_RDWR = 2;
	/** <i>native declaration : linux\generator\src\main\c\\usr\include\arm-linux-gnueabihf\sys\\uio.h</i> */
	public static final int _SYS_UIO_H = (int)1;
	/** <i>native declaration : linux\generator\src\main\c\\usr\include\arm-linux-gnueabihf\sys\socket.h</i> */
	public static final int _SYS_SOCKET_H = (int)1;
	/**
	 * Original signature : <code>int boo()</code><br>
	 * <i>native declaration : linux\generator\src\main\c\\usr\include\arm-linux-gnueabihf\sys\socket.h:71</i>
	 */
	public static native int boo();
	/** Undefined type */
	public static interface msghdr {
		
	};
}
