package voice.linux.jna.bluetooth;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h:2336</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class hci_conn_info extends Structure<hci_conn_info, hci_conn_info.ByValue, hci_conn_info.ByReference > {
	public short handle;
	/** C type : bdaddr_t */
	public bdaddr_t bdaddr;
	public byte type;
	public byte out;
	public short state;
	public int link_mode;
	public hci_conn_info() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("handle", "bdaddr", "type", "out", "state", "link_mode");
	}
	/** @param bdaddr C type : bdaddr_t */
	public hci_conn_info(short handle, bdaddr_t bdaddr, byte type, byte out, short state, int link_mode) {
		super();
		this.handle = handle;
		this.bdaddr = bdaddr;
		this.type = type;
		this.out = out;
		this.state = state;
		this.link_mode = link_mode;
	}
	public hci_conn_info(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected hci_conn_info newInstance() { return new hci_conn_info(); }
	public static hci_conn_info[] newArray(int arrayLength) {
		return Structure.newArray(hci_conn_info.class, arrayLength);
	}
	public static class ByReference extends hci_conn_info implements Structure.ByReference {
		
	};
	public static class ByValue extends hci_conn_info implements Structure.ByValue {
		
	};
}
