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
public class evt_link_key_notify extends Structure {
	/** C type : bdaddr_t */
	public bdaddr_t bdaddr;
	/** C type : uint8_t[16] */
	public byte[] link_key = new byte[16];
	public byte key_type;
	public evt_link_key_notify() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("bdaddr", "link_key", "key_type");
	}
	/**
	 * @param bdaddr C type : bdaddr_t<br>
	 * @param link_key C type : uint8_t[16]
	 */
	public evt_link_key_notify(bdaddr_t bdaddr, byte link_key[], byte key_type) {
		super();
		this.bdaddr = bdaddr;
		if ((link_key.length != this.link_key.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.link_key = link_key;
		this.key_type = key_type;
	}
	public evt_link_key_notify(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends evt_link_key_notify implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_link_key_notify implements Structure.ByValue {
		
	};
}
