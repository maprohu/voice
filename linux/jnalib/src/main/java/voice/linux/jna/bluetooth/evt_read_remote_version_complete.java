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
public class evt_read_remote_version_complete extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte status() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public evt_read_remote_version_complete status(byte status) {
		this.io.setByteField(this, 0, status);
		return this;
	}
	@Field(1) 
	public short handle() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public evt_read_remote_version_complete handle(short handle) {
		this.io.setShortField(this, 1, handle);
		return this;
	}
	@Field(2) 
	public byte lmp_ver() {
		return this.io.getByteField(this, 2);
	}
	@Field(2) 
	public evt_read_remote_version_complete lmp_ver(byte lmp_ver) {
		this.io.setByteField(this, 2, lmp_ver);
		return this;
	}
	@Field(3) 
	public short manufacturer() {
		return this.io.getShortField(this, 3);
	}
	@Field(3) 
	public evt_read_remote_version_complete manufacturer(short manufacturer) {
		this.io.setShortField(this, 3, manufacturer);
		return this;
	}
	@Field(4) 
	public short lmp_subver() {
		return this.io.getShortField(this, 4);
	}
	@Field(4) 
	public evt_read_remote_version_complete lmp_subver(short lmp_subver) {
		this.io.setShortField(this, 4, lmp_subver);
		return this;
	}
	public evt_read_remote_version_complete() {
		super();
	}
	public evt_read_remote_version_complete(Pointer pointer) {
		super(pointer);
	}
}
