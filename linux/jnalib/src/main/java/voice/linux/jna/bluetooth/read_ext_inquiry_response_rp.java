package voice.linux.jna.bluetooth;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class read_ext_inquiry_response_rp extends Structure<read_ext_inquiry_response_rp, read_ext_inquiry_response_rp.ByValue, read_ext_inquiry_response_rp.ByReference > {
	public byte status;
	public byte fec;
	/** C type : uint8_t[240] */
	public byte[] data = new byte[240];
	public read_ext_inquiry_response_rp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "fec", "data");
	}
	/** @param data C type : uint8_t[240] */
	public read_ext_inquiry_response_rp(byte status, byte fec, byte data[]) {
		super();
		this.status = status;
		this.fec = fec;
		if ((data.length != this.data.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.data = data;
	}
	public read_ext_inquiry_response_rp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected read_ext_inquiry_response_rp newInstance() { return new read_ext_inquiry_response_rp(); }
	public static read_ext_inquiry_response_rp[] newArray(int arrayLength) {
		return Structure.newArray(read_ext_inquiry_response_rp.class, arrayLength);
	}
	public static class ByReference extends read_ext_inquiry_response_rp implements Structure.ByReference {
		
	};
	public static class ByValue extends read_ext_inquiry_response_rp implements Structure.ByValue {
		
	};
}