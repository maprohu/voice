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
public class sniff_subrating_cp extends Structure {
	public short handle;
	public short max_latency;
	public short min_remote_timeout;
	public short min_local_timeout;
	public sniff_subrating_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("handle", "max_latency", "min_remote_timeout", "min_local_timeout");
	}
	public sniff_subrating_cp(short handle, short max_latency, short min_remote_timeout, short min_local_timeout) {
		super();
		this.handle = handle;
		this.max_latency = max_latency;
		this.min_remote_timeout = min_remote_timeout;
		this.min_local_timeout = min_local_timeout;
	}
	public sniff_subrating_cp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends sniff_subrating_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends sniff_subrating_cp implements Structure.ByValue {
		
	};
}
