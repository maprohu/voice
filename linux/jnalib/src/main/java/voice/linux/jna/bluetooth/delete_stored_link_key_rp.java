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
public class delete_stored_link_key_rp extends Structure<delete_stored_link_key_rp, delete_stored_link_key_rp.ByValue, delete_stored_link_key_rp.ByReference > {
	public byte status;
	public short num_keys;
	public delete_stored_link_key_rp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "num_keys");
	}
	public delete_stored_link_key_rp(byte status, short num_keys) {
		super();
		this.status = status;
		this.num_keys = num_keys;
	}
	public delete_stored_link_key_rp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected delete_stored_link_key_rp newInstance() { return new delete_stored_link_key_rp(); }
	public static delete_stored_link_key_rp[] newArray(int arrayLength) {
		return Structure.newArray(delete_stored_link_key_rp.class, arrayLength);
	}
	public static class ByReference extends delete_stored_link_key_rp implements Structure.ByReference {
		
	};
	public static class ByValue extends delete_stored_link_key_rp implements Structure.ByValue {
		
	};
}