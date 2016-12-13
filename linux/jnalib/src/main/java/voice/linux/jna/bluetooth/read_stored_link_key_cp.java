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
public class read_stored_link_key_cp extends StructObject {
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
	public read_stored_link_key_cp bdaddr(bdaddr_t bdaddr) {
		this.io.setNativeObjectField(this, 0, bdaddr);
		return this;
	}
	@Field(1) 
	public byte read_all() {
		return this.io.getByteField(this, 1);
	}
	@Field(1) 
	public read_stored_link_key_cp read_all(byte read_all) {
		this.io.setByteField(this, 1, read_all);
		return this;
	}
	public read_stored_link_key_cp() {
		super();
	}
	public read_stored_link_key_cp(Pointer pointer) {
		super(pointer);
	}
}
