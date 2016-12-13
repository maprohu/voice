package voice.linux.jna.bluetooth;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class evt_keypress_notify extends Structure<evt_keypress_notify, evt_keypress_notify.ByValue, evt_keypress_notify.ByReference > {
	/** C type : bdaddr_t */
	public bdaddr_t bdaddr;
	public byte type;
	public evt_keypress_notify() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("bdaddr", "type");
	}
	/** @param bdaddr C type : bdaddr_t */
	public evt_keypress_notify(bdaddr_t bdaddr, byte type) {
		super();
		this.bdaddr = bdaddr;
		this.type = type;
	}
	public evt_keypress_notify(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected evt_keypress_notify newInstance() { return new evt_keypress_notify(); }
	public static evt_keypress_notify[] newArray(int arrayLength) {
		return Structure.newArray(evt_keypress_notify.class, arrayLength);
	}
	public static class ByReference extends evt_keypress_notify implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_keypress_notify implements Structure.ByValue {
		
	};
}
