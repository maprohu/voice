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
public class user_confirm_reply_cp extends Structure<user_confirm_reply_cp, user_confirm_reply_cp.ByValue, user_confirm_reply_cp.ByReference > {
	/** C type : bdaddr_t */
	public bdaddr_t bdaddr;
	public user_confirm_reply_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("bdaddr");
	}
	/** @param bdaddr C type : bdaddr_t */
	public user_confirm_reply_cp(bdaddr_t bdaddr) {
		super();
		this.bdaddr = bdaddr;
	}
	public user_confirm_reply_cp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected user_confirm_reply_cp newInstance() { return new user_confirm_reply_cp(); }
	public static user_confirm_reply_cp[] newArray(int arrayLength) {
		return Structure.newArray(user_confirm_reply_cp.class, arrayLength);
	}
	public static class ByReference extends user_confirm_reply_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends user_confirm_reply_cp implements Structure.ByValue {
		
	};
}