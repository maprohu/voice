package voice.linux.jna.c;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\bits\fcntl.h:43</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class flock64 extends Structure {
	/** Type of lock: F_RDLCK, F_WRLCK, or F_UNLCK. */
	public short l_type;
	/** Where `l_start' is relative to (like `lseek'). */
	public short l_whence;
	/**
	 * Offset where the lock begins.<br>
	 * C type : __off64_t
	 */
	public long l_start;
	/**
	 * Size of the locked area; zero means until EOF.<br>
	 * C type : __off64_t
	 */
	public long l_len;
	/**
	 * Process holding the lock.<br>
	 * C type : __pid_t
	 */
	public int l_pid;
	public flock64() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("l_type", "l_whence", "l_start", "l_len", "l_pid");
	}
	/**
	 * @param l_type Type of lock: F_RDLCK, F_WRLCK, or F_UNLCK.<br>
	 * @param l_whence Where `l_start' is relative to (like `lseek').<br>
	 * @param l_start Offset where the lock begins.<br>
	 * C type : __off64_t<br>
	 * @param l_len Size of the locked area; zero means until EOF.<br>
	 * C type : __off64_t<br>
	 * @param l_pid Process holding the lock.<br>
	 * C type : __pid_t
	 */
	public flock64(short l_type, short l_whence, long l_start, long l_len, int l_pid) {
		super();
		this.l_type = l_type;
		this.l_whence = l_whence;
		this.l_start = l_start;
		this.l_len = l_len;
		this.l_pid = l_pid;
	}
	public flock64(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends flock64 implements Structure.ByReference {
		
	};
	public static class ByValue extends flock64 implements Structure.ByValue {
		
	};
}
