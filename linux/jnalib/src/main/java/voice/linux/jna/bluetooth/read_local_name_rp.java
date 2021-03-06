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
public class read_local_name_rp extends Structure {
	public byte status;
	/** C type : uint8_t[248] */
	public byte[] name = new byte[248];
	public read_local_name_rp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "name");
	}
	/** @param name C type : uint8_t[248] */
	public read_local_name_rp(byte status, byte name[]) {
		super();
		this.status = status;
		if ((name.length != this.name.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.name = name;
	}
	public read_local_name_rp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends read_local_name_rp implements Structure.ByReference {
		
	};
	public static class ByValue extends read_local_name_rp implements Structure.ByValue {
		
	};
}
