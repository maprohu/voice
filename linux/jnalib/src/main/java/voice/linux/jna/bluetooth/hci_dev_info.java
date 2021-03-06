package voice.linux.jna.bluetooth;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h:2313</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class hci_dev_info extends Structure {
	public short dev_id;
	/** C type : char[8] */
	public byte[] name = new byte[8];
	/** C type : bdaddr_t */
	public bdaddr_t bdaddr;
	public int flags;
	public byte type;
	/** C type : uint8_t[8] */
	public byte[] features = new byte[8];
	public int pkt_type;
	public int link_policy;
	public int link_mode;
	public short acl_mtu;
	public short acl_pkts;
	public short sco_mtu;
	public short sco_pkts;
	/** C type : hci_dev_stats */
	public hci_dev_stats stat;
	public hci_dev_info() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("dev_id", "name", "bdaddr", "flags", "type", "features", "pkt_type", "link_policy", "link_mode", "acl_mtu", "acl_pkts", "sco_mtu", "sco_pkts", "stat");
	}
	public hci_dev_info(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends hci_dev_info implements Structure.ByReference {
		
	};
	public static class ByValue extends hci_dev_info implements Structure.ByValue {
		
	};
}
