package voice.linux.jna.bluetooth;
import org.bridj.BridJ;
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
public abstract class send_keypress_notify_cp extends StructObject {
	static {
		BridJ.register();
	}
	/** Conversion Error : bdaddr_t (failed to convert type to Java (undefined type)) */
	@Field(1) 
	public byte type() {
		return this.io.getByteField(this, 1);
	}
	@Field(1) 
	public send_keypress_notify_cp type(byte type) {
		this.io.setByteField(this, 1, type);
		return this;
	}
}
