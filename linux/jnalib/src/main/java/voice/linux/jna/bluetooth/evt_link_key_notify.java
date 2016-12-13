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
public class evt_link_key_notify extends StructObject {
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
	public evt_link_key_notify bdaddr(bdaddr_t bdaddr) {
		this.io.setNativeObjectField(this, 0, bdaddr);
		return this;
	}
	/** C type : uint8_t[16] */
	@Array({16}) 
	@Field(1) 
	public Pointer<Byte > link_key() {
		return this.io.getPointerField(this, 1);
	}
	@Field(2) 
	public byte key_type() {
		return this.io.getByteField(this, 2);
	}
	@Field(2) 
	public evt_link_key_notify key_type(byte key_type) {
		this.io.setByteField(this, 2, key_type);
		return this;
	}
	public evt_link_key_notify() {
		super();
	}
	public evt_link_key_notify(Pointer pointer) {
		super(pointer);
	}
}
