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
public class write_simple_pairing_debug_mode_cp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte mode() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public write_simple_pairing_debug_mode_cp mode(byte mode) {
		this.io.setByteField(this, 0, mode);
		return this;
	}
	public write_simple_pairing_debug_mode_cp() {
		super();
	}
	public write_simple_pairing_debug_mode_cp(Pointer pointer) {
		super(pointer);
	}
}
