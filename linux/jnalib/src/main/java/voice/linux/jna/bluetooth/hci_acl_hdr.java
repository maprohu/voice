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
public class hci_acl_hdr extends Structure<hci_acl_hdr, hci_acl_hdr.ByValue, hci_acl_hdr.ByReference > {
	/** Handle & Flags(PB, BC) */
	public short handle;
	public short dlen;
	public hci_acl_hdr() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("handle", "dlen");
	}
	/** @param handle Handle & Flags(PB, BC) */
	public hci_acl_hdr(short handle, short dlen) {
		super();
		this.handle = handle;
		this.dlen = dlen;
	}
	public hci_acl_hdr(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected hci_acl_hdr newInstance() { return new hci_acl_hdr(); }
	public static hci_acl_hdr[] newArray(int arrayLength) {
		return Structure.newArray(hci_acl_hdr.class, arrayLength);
	}
	public static class ByReference extends hci_acl_hdr implements Structure.ByReference {
		
	};
	public static class ByValue extends hci_acl_hdr implements Structure.ByValue {
		
	};
}