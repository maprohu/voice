package voice.linux.jna.bluetooth;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("bluetooth") 
public class hci_msg_hdr extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public short device() {
		return this.io.getShortField(this, 0);
	}
	@Field(0) 
	public hci_msg_hdr device(short device) {
		this.io.setShortField(this, 0, device);
		return this;
	}
	@Field(1) 
	public short type() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public hci_msg_hdr type(short type) {
		this.io.setShortField(this, 1, type);
		return this;
	}
	@Field(2) 
	public short plen() {
		return this.io.getShortField(this, 2);
	}
	@Field(2) 
	public hci_msg_hdr plen(short plen) {
		this.io.setShortField(this, 2, plen);
		return this;
	}
	public hci_msg_hdr() {
		super();
	}
	public hci_msg_hdr(Pointer pointer) {
		super(pointer);
	}
}
