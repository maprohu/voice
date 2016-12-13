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
public class read_local_ext_features_rp extends Structure<read_local_ext_features_rp, read_local_ext_features_rp.ByValue, read_local_ext_features_rp.ByReference > {
	public byte status;
	public byte page_num;
	public byte max_page_num;
	/** C type : uint8_t[8] */
	public byte[] features = new byte[8];
	public read_local_ext_features_rp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "page_num", "max_page_num", "features");
	}
	/** @param features C type : uint8_t[8] */
	public read_local_ext_features_rp(byte status, byte page_num, byte max_page_num, byte features[]) {
		super();
		this.status = status;
		this.page_num = page_num;
		this.max_page_num = max_page_num;
		if ((features.length != this.features.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.features = features;
	}
	public read_local_ext_features_rp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected read_local_ext_features_rp newInstance() { return new read_local_ext_features_rp(); }
	public static read_local_ext_features_rp[] newArray(int arrayLength) {
		return Structure.newArray(read_local_ext_features_rp.class, arrayLength);
	}
	public static class ByReference extends read_local_ext_features_rp implements Structure.ByReference {
		
	};
	public static class ByValue extends read_local_ext_features_rp implements Structure.ByValue {
		
	};
}
