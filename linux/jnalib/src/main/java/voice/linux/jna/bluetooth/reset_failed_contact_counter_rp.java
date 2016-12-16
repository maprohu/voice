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
public class reset_failed_contact_counter_rp extends Structure {
	public byte status;
	public short handle;
	public reset_failed_contact_counter_rp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "handle");
	}
	public reset_failed_contact_counter_rp(byte status, short handle) {
		super();
		this.status = status;
		this.handle = handle;
	}
	public reset_failed_contact_counter_rp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends reset_failed_contact_counter_rp implements Structure.ByReference {
		
	};
	public static class ByValue extends reset_failed_contact_counter_rp implements Structure.ByValue {
		
	};
}
