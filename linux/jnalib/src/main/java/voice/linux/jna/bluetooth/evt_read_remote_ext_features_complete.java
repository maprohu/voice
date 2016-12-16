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
public class evt_read_remote_ext_features_complete extends Structure {
	public byte status;
	public short handle;
	public byte page_num;
	public byte max_page_num;
	/** C type : uint8_t[8] */
	public byte[] features = new byte[8];
	public evt_read_remote_ext_features_complete() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "handle", "page_num", "max_page_num", "features");
	}
	/** @param features C type : uint8_t[8] */
	public evt_read_remote_ext_features_complete(byte status, short handle, byte page_num, byte max_page_num, byte features[]) {
		super();
		this.status = status;
		this.handle = handle;
		this.page_num = page_num;
		this.max_page_num = max_page_num;
		if ((features.length != this.features.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.features = features;
	}
	public evt_read_remote_ext_features_complete(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends evt_read_remote_ext_features_complete implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_read_remote_ext_features_complete implements Structure.ByValue {
		
	};
}
