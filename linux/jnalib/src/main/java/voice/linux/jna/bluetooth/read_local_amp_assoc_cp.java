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
public class read_local_amp_assoc_cp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte handle() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public read_local_amp_assoc_cp handle(byte handle) {
		this.io.setByteField(this, 0, handle);
		return this;
	}
	@Field(1) 
	public short length_so_far() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public read_local_amp_assoc_cp length_so_far(short length_so_far) {
		this.io.setShortField(this, 1, length_so_far);
		return this;
	}
	@Field(2) 
	public short assoc_length() {
		return this.io.getShortField(this, 2);
	}
	@Field(2) 
	public read_local_amp_assoc_cp assoc_length(short assoc_length) {
		this.io.setShortField(this, 2, assoc_length);
		return this;
	}
	public read_local_amp_assoc_cp() {
		super();
	}
	public read_local_amp_assoc_cp(Pointer pointer) {
		super(pointer);
	}
}
