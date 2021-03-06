package voice.linux.jna.bluetooth;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h:2361</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class hci_conn_info_req extends Structure {
	/** C type : bdaddr_t */
	public bdaddr_t bdaddr;
	public byte type;
	/** C type : hci_conn_info[0] */
	public hci_conn_info[] conn_info = new hci_conn_info[0];
	public hci_conn_info_req() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("bdaddr", "type", "conn_info");
	}
	/**
	 * @param bdaddr C type : bdaddr_t<br>
	 * @param conn_info C type : hci_conn_info[0]
	 */
	public hci_conn_info_req(bdaddr_t bdaddr, byte type, hci_conn_info conn_info[]) {
		super();
		this.bdaddr = bdaddr;
		this.type = type;
		if ((conn_info.length != this.conn_info.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.conn_info = conn_info;
	}
	public hci_conn_info_req(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends hci_conn_info_req implements Structure.ByReference {
		
	};
	public static class ByValue extends hci_conn_info_req implements Structure.ByValue {
		
	};
}
