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
public class remote_name_req_cp extends StructObject {
	static {
		BridJ.register();
	}
	/** C type : bdaddr_t */
	@Field(0) 
	public bdaddr_t bdaddr() {
		return this.io.getNativeObjectField(this, 0);
	}
	/** C type : bdaddr_t */
	@Field(0) 
	public remote_name_req_cp bdaddr(bdaddr_t bdaddr) {
		this.io.setNativeObjectField(this, 0, bdaddr);
		return this;
	}
	@Field(1) 
	public byte pscan_rep_mode() {
		return this.io.getByteField(this, 1);
	}
	@Field(1) 
	public remote_name_req_cp pscan_rep_mode(byte pscan_rep_mode) {
		this.io.setByteField(this, 1, pscan_rep_mode);
		return this;
	}
	@Field(2) 
	public byte pscan_mode() {
		return this.io.getByteField(this, 2);
	}
	@Field(2) 
	public remote_name_req_cp pscan_mode(byte pscan_mode) {
		this.io.setByteField(this, 2, pscan_mode);
		return this;
	}
	@Field(3) 
	public short clock_offset() {
		return this.io.getShortField(this, 3);
	}
	@Field(3) 
	public remote_name_req_cp clock_offset(short clock_offset) {
		this.io.setShortField(this, 3, clock_offset);
		return this;
	}
	public remote_name_req_cp() {
		super();
	}
	public remote_name_req_cp(Pointer pointer) {
		super(pointer);
	}
}
