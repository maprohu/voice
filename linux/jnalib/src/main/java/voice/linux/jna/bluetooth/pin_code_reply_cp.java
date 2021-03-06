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
public class pin_code_reply_cp extends Structure {
	/** C type : bdaddr_t */
	public bdaddr_t bdaddr;
	public byte pin_len;
	/** C type : uint8_t[16] */
	public byte[] pin_code = new byte[16];
	public pin_code_reply_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("bdaddr", "pin_len", "pin_code");
	}
	/**
	 * @param bdaddr C type : bdaddr_t<br>
	 * @param pin_code C type : uint8_t[16]
	 */
	public pin_code_reply_cp(bdaddr_t bdaddr, byte pin_len, byte pin_code[]) {
		super();
		this.bdaddr = bdaddr;
		this.pin_len = pin_len;
		if ((pin_code.length != this.pin_code.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.pin_code = pin_code;
	}
	public pin_code_reply_cp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends pin_code_reply_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends pin_code_reply_cp implements Structure.ByValue {
		
	};
}
