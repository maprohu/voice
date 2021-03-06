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
public class le_set_advertising_parameters_cp extends Structure {
	public short min_interval;
	public short max_interval;
	public byte advtype;
	public byte own_bdaddr_type;
	public byte direct_bdaddr_type;
	/** C type : bdaddr_t */
	public bdaddr_t direct_bdaddr;
	public byte chan_map;
	public byte filter;
	public le_set_advertising_parameters_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("min_interval", "max_interval", "advtype", "own_bdaddr_type", "direct_bdaddr_type", "direct_bdaddr", "chan_map", "filter");
	}
	/** @param direct_bdaddr C type : bdaddr_t */
	public le_set_advertising_parameters_cp(short min_interval, short max_interval, byte advtype, byte own_bdaddr_type, byte direct_bdaddr_type, bdaddr_t direct_bdaddr, byte chan_map, byte filter) {
		super();
		this.min_interval = min_interval;
		this.max_interval = max_interval;
		this.advtype = advtype;
		this.own_bdaddr_type = own_bdaddr_type;
		this.direct_bdaddr_type = direct_bdaddr_type;
		this.direct_bdaddr = direct_bdaddr;
		this.chan_map = chan_map;
		this.filter = filter;
	}
	public le_set_advertising_parameters_cp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends le_set_advertising_parameters_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends le_set_advertising_parameters_cp implements Structure.ByValue {
		
	};
}
