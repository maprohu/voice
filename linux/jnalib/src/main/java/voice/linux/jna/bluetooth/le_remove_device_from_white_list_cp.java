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
public class le_remove_device_from_white_list_cp extends Structure {
	public byte bdaddr_type;
	/** C type : bdaddr_t */
	public bdaddr_t bdaddr;
	public le_remove_device_from_white_list_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("bdaddr_type", "bdaddr");
	}
	/** @param bdaddr C type : bdaddr_t */
	public le_remove_device_from_white_list_cp(byte bdaddr_type, bdaddr_t bdaddr) {
		super();
		this.bdaddr_type = bdaddr_type;
		this.bdaddr = bdaddr;
	}
	public le_remove_device_from_white_list_cp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends le_remove_device_from_white_list_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends le_remove_device_from_white_list_cp implements Structure.ByValue {
		
	};
}
