package voice.linux.jna.bluetooth;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : bluetooth/hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("bluetooth") 
public class hci_acl_hdr extends StructObject {
	static {
		BridJ.register();
	}
	/** Handle & Flags(PB, BC) */
	@Field(0) 
	public short handle() {
		return this.io.getShortField(this, 0);
	}
	/** Handle & Flags(PB, BC) */
	@Field(0) 
	public hci_acl_hdr handle(short handle) {
		this.io.setShortField(this, 0, handle);
		return this;
	}
	@Field(1) 
	public short dlen() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public hci_acl_hdr dlen(short dlen) {
		this.io.setShortField(this, 1, dlen);
		return this;
	}
	public hci_acl_hdr() {
		super();
	}
	public hci_acl_hdr(Pointer pointer) {
		super(pointer);
	}
}
