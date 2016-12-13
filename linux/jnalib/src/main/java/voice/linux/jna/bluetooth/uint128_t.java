package voice.linux.jna.bluetooth;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\bluetooth.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class uint128_t extends Structure {
	/** C type : uint8_t[16] */
	public byte[] data = new byte[16];
	public uint128_t() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("data");
	}
	/** @param data C type : uint8_t[16] */
	public uint128_t(byte data[]) {
		super();
		if ((data.length != this.data.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.data = data;
	}
	public uint128_t(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends uint128_t implements Structure.ByReference {
		
	};
	public static class ByValue extends uint128_t implements Structure.ByValue {
		
	};
}
