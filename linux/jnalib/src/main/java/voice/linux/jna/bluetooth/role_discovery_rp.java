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
public class role_discovery_rp extends Structure<role_discovery_rp, role_discovery_rp.ByValue, role_discovery_rp.ByReference > {
	public byte status;
	public short handle;
	public byte role;
	public role_discovery_rp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "handle", "role");
	}
	public role_discovery_rp(byte status, short handle, byte role) {
		super();
		this.status = status;
		this.handle = handle;
		this.role = role;
	}
	public role_discovery_rp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected role_discovery_rp newInstance() { return new role_discovery_rp(); }
	public static role_discovery_rp[] newArray(int arrayLength) {
		return Structure.newArray(role_discovery_rp.class, arrayLength);
	}
	public static class ByReference extends role_discovery_rp implements Structure.ByReference {
		
	};
	public static class ByValue extends role_discovery_rp implements Structure.ByValue {
		
	};
}
