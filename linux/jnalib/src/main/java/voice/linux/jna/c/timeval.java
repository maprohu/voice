package voice.linux.jna.c;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * A time value that is accurate to the nearest<br>
 * microsecond but also has a range of years.<br>
 * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\bits\time.h:4</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class timeval extends Structure {
	/**
	 * Seconds.<br>
	 * C type : __time_t
	 */
	public long tv_sec;
	/**
	 * Microseconds.<br>
	 * C type : __suseconds_t
	 */
	public long tv_usec;
	public timeval() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("tv_sec", "tv_usec");
	}
	/**
	 * @param tv_sec Seconds.<br>
	 * C type : __time_t<br>
	 * @param tv_usec Microseconds.<br>
	 * C type : __suseconds_t
	 */
	public timeval(long tv_sec, long tv_usec) {
		super();
		this.tv_sec = tv_sec;
		this.tv_usec = tv_usec;
	}
	public timeval(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends timeval implements Structure.ByReference {
		
	};
	public static class ByValue extends timeval implements Structure.ByValue {
		
	};
}
