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
public class create_conn_cp extends StructObject {
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
	public create_conn_cp bdaddr(bdaddr_t bdaddr) {
		this.io.setNativeObjectField(this, 0, bdaddr);
		return this;
	}
	@Field(1) 
	public short pkt_type() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public create_conn_cp pkt_type(short pkt_type) {
		this.io.setShortField(this, 1, pkt_type);
		return this;
	}
	@Field(2) 
	public byte pscan_rep_mode() {
		return this.io.getByteField(this, 2);
	}
	@Field(2) 
	public create_conn_cp pscan_rep_mode(byte pscan_rep_mode) {
		this.io.setByteField(this, 2, pscan_rep_mode);
		return this;
	}
	@Field(3) 
	public byte pscan_mode() {
		return this.io.getByteField(this, 3);
	}
	@Field(3) 
	public create_conn_cp pscan_mode(byte pscan_mode) {
		this.io.setByteField(this, 3, pscan_mode);
		return this;
	}
	@Field(4) 
	public short clock_offset() {
		return this.io.getShortField(this, 4);
	}
	@Field(4) 
	public create_conn_cp clock_offset(short clock_offset) {
		this.io.setShortField(this, 4, clock_offset);
		return this;
	}
	@Field(5) 
	public byte role_switch() {
		return this.io.getByteField(this, 5);
	}
	@Field(5) 
	public create_conn_cp role_switch(byte role_switch) {
		this.io.setByteField(this, 5, role_switch);
		return this;
	}
	public create_conn_cp() {
		super();
	}
	public create_conn_cp(Pointer pointer) {
		super(pointer);
	}
}
