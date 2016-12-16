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
public class le_read_channel_map_rp extends Structure {
	public byte status;
	public short handle;
	/** C type : uint8_t[5] */
	public byte[] map = new byte[5];
	public le_read_channel_map_rp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "handle", "map");
	}
	/** @param map C type : uint8_t[5] */
	public le_read_channel_map_rp(byte status, short handle, byte map[]) {
		super();
		this.status = status;
		this.handle = handle;
		if ((map.length != this.map.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.map = map;
	}
	public le_read_channel_map_rp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends le_read_channel_map_rp implements Structure.ByReference {
		
	};
	public static class ByValue extends le_read_channel_map_rp implements Structure.ByValue {
		
	};
}
