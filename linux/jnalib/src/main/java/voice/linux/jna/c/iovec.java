package voice.linux.jna.c;
import com.ochafik.lang.jnaerator.runtime.NativeSize;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
/**
 * Structure for scatter/gather I/O.<br>
 * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\bits\\uio.h:18</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class iovec extends Structure<iovec, iovec.ByValue, iovec.ByReference > {
	/**
	 * Pointer to data.<br>
	 * C type : void*
	 */
	public Pointer iov_base;
	/** Length of data. */
	public NativeSize iov_len;
	public iovec() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("iov_base", "iov_len");
	}
	/**
	 * @param iov_base Pointer to data.<br>
	 * C type : void*<br>
	 * @param iov_len Length of data.
	 */
	public iovec(Pointer iov_base, NativeSize iov_len) {
		super();
		this.iov_base = iov_base;
		this.iov_len = iov_len;
	}
	public iovec(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected iovec newInstance() { return new iovec(); }
	public static iovec[] newArray(int arrayLength) {
		return Structure.newArray(iovec.class, arrayLength);
	}
	public static class ByReference extends iovec implements Structure.ByReference {
		
	};
	public static class ByValue extends iovec implements Structure.ByValue {
		
	};
}
