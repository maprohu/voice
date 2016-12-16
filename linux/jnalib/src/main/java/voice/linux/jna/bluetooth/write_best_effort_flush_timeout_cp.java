package voice.linux.jna.bluetooth;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class write_best_effort_flush_timeout_cp extends Structure {
	public short handle;
	public int timeout;
	public write_best_effort_flush_timeout_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("handle", "timeout");
	}
	public write_best_effort_flush_timeout_cp(short handle, int timeout) {
		super();
		this.handle = handle;
		this.timeout = timeout;
	}
	public write_best_effort_flush_timeout_cp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends write_best_effort_flush_timeout_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends write_best_effort_flush_timeout_cp implements Structure.ByValue {
		
	};
}
