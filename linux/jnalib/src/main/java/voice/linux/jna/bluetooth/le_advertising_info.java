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
public class le_advertising_info extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte evt_type() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public le_advertising_info evt_type(byte evt_type) {
		this.io.setByteField(this, 0, evt_type);
		return this;
	}
	@Field(1) 
	public byte bdaddr_type() {
		return this.io.getByteField(this, 1);
	}
	@Field(1) 
	public le_advertising_info bdaddr_type(byte bdaddr_type) {
		this.io.setByteField(this, 1, bdaddr_type);
		return this;
	}
	/** C type : bdaddr_t */
	@Field(2) 
	public bdaddr_t bdaddr() {
		return this.io.getNativeObjectField(this, 2);
	}
	/** C type : bdaddr_t */
	@Field(2) 
	public le_advertising_info bdaddr(bdaddr_t bdaddr) {
		this.io.setNativeObjectField(this, 2, bdaddr);
		return this;
	}
	@Field(3) 
	public byte length() {
		return this.io.getByteField(this, 3);
	}
	@Field(3) 
	public le_advertising_info length(byte length) {
		this.io.setByteField(this, 3, length);
		return this;
	}
	/** C type : uint8_t[0] */
	@Array({0}) 
	@Field(4) 
	public Pointer<Byte > data() {
		return this.io.getPointerField(this, 4);
	}
	public le_advertising_info() {
		super();
	}
	public le_advertising_info(Pointer pointer) {
		super(pointer);
	}
}
