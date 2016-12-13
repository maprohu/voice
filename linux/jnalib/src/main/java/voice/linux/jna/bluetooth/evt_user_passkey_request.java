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
public class evt_user_passkey_request extends Structure<evt_user_passkey_request, evt_user_passkey_request.ByValue, evt_user_passkey_request.ByReference > {
	/** C type : bdaddr_t */
	public bdaddr_t bdaddr;
	public evt_user_passkey_request() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("bdaddr");
	}
	/** @param bdaddr C type : bdaddr_t */
	public evt_user_passkey_request(bdaddr_t bdaddr) {
		super();
		this.bdaddr = bdaddr;
	}
	public evt_user_passkey_request(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected evt_user_passkey_request newInstance() { return new evt_user_passkey_request(); }
	public static evt_user_passkey_request[] newArray(int arrayLength) {
		return Structure.newArray(evt_user_passkey_request.class, arrayLength);
	}
	public static class ByReference extends evt_user_passkey_request implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_user_passkey_request implements Structure.ByValue {
		
	};
}
