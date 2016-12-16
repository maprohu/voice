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
public class evt_return_link_keys extends Structure {
	public byte num_keys;
	public evt_return_link_keys() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("num_keys");
	}
	public evt_return_link_keys(byte num_keys) {
		super();
		this.num_keys = num_keys;
	}
	public evt_return_link_keys(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends evt_return_link_keys implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_return_link_keys implements Structure.ByValue {
		
	};
}
