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
public class read_scan_enable_rp extends Structure {
	public byte status;
	public byte enable;
	public read_scan_enable_rp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "enable");
	}
	public read_scan_enable_rp(byte status, byte enable) {
		super();
		this.status = status;
		this.enable = enable;
	}
	public read_scan_enable_rp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends read_scan_enable_rp implements Structure.ByReference {
		
	};
	public static class ByValue extends read_scan_enable_rp implements Structure.ByValue {
		
	};
}
