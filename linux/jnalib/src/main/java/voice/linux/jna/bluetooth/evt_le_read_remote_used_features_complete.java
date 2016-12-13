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
public class evt_le_read_remote_used_features_complete extends Structure<evt_le_read_remote_used_features_complete, evt_le_read_remote_used_features_complete.ByValue, evt_le_read_remote_used_features_complete.ByReference > {
	public byte status;
	public short handle;
	/** C type : uint8_t[8] */
	public byte[] features = new byte[8];
	public evt_le_read_remote_used_features_complete() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "handle", "features");
	}
	/** @param features C type : uint8_t[8] */
	public evt_le_read_remote_used_features_complete(byte status, short handle, byte features[]) {
		super();
		this.status = status;
		this.handle = handle;
		if ((features.length != this.features.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.features = features;
	}
	public evt_le_read_remote_used_features_complete(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected evt_le_read_remote_used_features_complete newInstance() { return new evt_le_read_remote_used_features_complete(); }
	public static evt_le_read_remote_used_features_complete[] newArray(int arrayLength) {
		return Structure.newArray(evt_le_read_remote_used_features_complete.class, arrayLength);
	}
	public static class ByReference extends evt_le_read_remote_used_features_complete implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_le_read_remote_used_features_complete implements Structure.ByValue {
		
	};
}
