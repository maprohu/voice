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
public class evt_hardware_error extends Structure {
	public byte code;
	public evt_hardware_error() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("code");
	}
	public evt_hardware_error(byte code) {
		super();
		this.code = code;
	}
	public evt_hardware_error(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends evt_hardware_error implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_hardware_error implements Structure.ByValue {
		
	};
}
