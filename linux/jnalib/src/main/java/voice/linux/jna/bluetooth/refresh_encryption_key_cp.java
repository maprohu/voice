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
public class refresh_encryption_key_cp extends Structure<refresh_encryption_key_cp, refresh_encryption_key_cp.ByValue, refresh_encryption_key_cp.ByReference > {
	public short handle;
	public refresh_encryption_key_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("handle");
	}
	public refresh_encryption_key_cp(short handle) {
		super();
		this.handle = handle;
	}
	public refresh_encryption_key_cp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected refresh_encryption_key_cp newInstance() { return new refresh_encryption_key_cp(); }
	public static refresh_encryption_key_cp[] newArray(int arrayLength) {
		return Structure.newArray(refresh_encryption_key_cp.class, arrayLength);
	}
	public static class ByReference extends refresh_encryption_key_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends refresh_encryption_key_cp implements Structure.ByValue {
		
	};
}