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
public class write_link_policy_rp extends Structure<write_link_policy_rp, write_link_policy_rp.ByValue, write_link_policy_rp.ByReference > {
	public byte status;
	public short handle;
	public write_link_policy_rp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "handle");
	}
	public write_link_policy_rp(byte status, short handle) {
		super();
		this.status = status;
		this.handle = handle;
	}
	public write_link_policy_rp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected write_link_policy_rp newInstance() { return new write_link_policy_rp(); }
	public static write_link_policy_rp[] newArray(int arrayLength) {
		return Structure.newArray(write_link_policy_rp.class, arrayLength);
	}
	public static class ByReference extends write_link_policy_rp implements Structure.ByReference {
		
	};
	public static class ByValue extends write_link_policy_rp implements Structure.ByValue {
		
	};
}
