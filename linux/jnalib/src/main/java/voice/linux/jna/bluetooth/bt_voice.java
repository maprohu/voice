package voice.linux.jna.bluetooth;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * AMP Preferred<br>
 *   Allow use of AMP controllers<br>
 *   If the L2CAP channel is currently on BR/EDR and AMP controller<br>
 *     resources are available, initiate a channel move to AMP.<br>
 *   Channel move requests from the remote device are allowed.<br>
 *   If the L2CAP socket has not been connected yet, try to create<br>
 *     and configure the channel directly on an AMP controller rather<br>
 *     than BR/EDR.<br>
 * <i>native declaration : bluetooth\bluetooth.h:112</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class bt_voice extends Structure {
	public short setting;
	public bt_voice() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("setting");
	}
	public bt_voice(short setting) {
		super();
		this.setting = setting;
	}
	public bt_voice(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends bt_voice implements Structure.ByReference {
		
	};
	public static class ByValue extends bt_voice implements Structure.ByValue {
		
	};
}
