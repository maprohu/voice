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
public class read_local_version_rp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte status() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public read_local_version_rp status(byte status) {
		this.io.setByteField(this, 0, status);
		return this;
	}
	@Field(1) 
	public byte hci_ver() {
		return this.io.getByteField(this, 1);
	}
	@Field(1) 
	public read_local_version_rp hci_ver(byte hci_ver) {
		this.io.setByteField(this, 1, hci_ver);
		return this;
	}
	@Field(2) 
	public short hci_rev() {
		return this.io.getShortField(this, 2);
	}
	@Field(2) 
	public read_local_version_rp hci_rev(short hci_rev) {
		this.io.setShortField(this, 2, hci_rev);
		return this;
	}
	@Field(3) 
	public byte lmp_ver() {
		return this.io.getByteField(this, 3);
	}
	@Field(3) 
	public read_local_version_rp lmp_ver(byte lmp_ver) {
		this.io.setByteField(this, 3, lmp_ver);
		return this;
	}
	@Field(4) 
	public short manufacturer() {
		return this.io.getShortField(this, 4);
	}
	@Field(4) 
	public read_local_version_rp manufacturer(short manufacturer) {
		this.io.setShortField(this, 4, manufacturer);
		return this;
	}
	@Field(5) 
	public short lmp_subver() {
		return this.io.getShortField(this, 5);
	}
	@Field(5) 
	public read_local_version_rp lmp_subver(short lmp_subver) {
		this.io.setShortField(this, 5, lmp_subver);
		return this;
	}
	public read_local_version_rp() {
		super();
	}
	public read_local_version_rp(Pointer pointer) {
		super(pointer);
	}
}
