package voice.linux.jna.bluetooth;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("bluetooth") 
public class inquiry_info_with_rssi_and_pscan_mode extends StructObject {
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
	public inquiry_info_with_rssi_and_pscan_mode bdaddr(bdaddr_t bdaddr) {
		this.io.setNativeObjectField(this, 0, bdaddr);
		return this;
	}
	@Field(1) 
	public byte pscan_rep_mode() {
		return this.io.getByteField(this, 1);
	}
	@Field(1) 
	public inquiry_info_with_rssi_and_pscan_mode pscan_rep_mode(byte pscan_rep_mode) {
		this.io.setByteField(this, 1, pscan_rep_mode);
		return this;
	}
	@Field(2) 
	public byte pscan_period_mode() {
		return this.io.getByteField(this, 2);
	}
	@Field(2) 
	public inquiry_info_with_rssi_and_pscan_mode pscan_period_mode(byte pscan_period_mode) {
		this.io.setByteField(this, 2, pscan_period_mode);
		return this;
	}
	@Field(3) 
	public byte pscan_mode() {
		return this.io.getByteField(this, 3);
	}
	@Field(3) 
	public inquiry_info_with_rssi_and_pscan_mode pscan_mode(byte pscan_mode) {
		this.io.setByteField(this, 3, pscan_mode);
		return this;
	}
	/** C type : uint8_t[3] */
	@Array({3}) 
	@Field(4) 
	public Pointer<Byte > dev_class() {
		return this.io.getPointerField(this, 4);
	}
	@Field(5) 
	public short clock_offset() {
		return this.io.getShortField(this, 5);
	}
	@Field(5) 
	public inquiry_info_with_rssi_and_pscan_mode clock_offset(short clock_offset) {
		this.io.setShortField(this, 5, clock_offset);
		return this;
	}
	@Field(6) 
	public byte rssi() {
		return this.io.getByteField(this, 6);
	}
	@Field(6) 
	public inquiry_info_with_rssi_and_pscan_mode rssi(byte rssi) {
		this.io.setByteField(this, 6, rssi);
		return this;
	}
	public inquiry_info_with_rssi_and_pscan_mode() {
		super();
	}
	public inquiry_info_with_rssi_and_pscan_mode(Pointer pointer) {
		super(pointer);
	}
}
