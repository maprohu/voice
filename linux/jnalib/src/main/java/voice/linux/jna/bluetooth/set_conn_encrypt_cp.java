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
public class set_conn_encrypt_cp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public short handle() {
		return this.io.getShortField(this, 0);
	}
	@Field(0) 
	public set_conn_encrypt_cp handle(short handle) {
		this.io.setShortField(this, 0, handle);
		return this;
	}
	@Field(1) 
	public byte encrypt() {
		return this.io.getByteField(this, 1);
	}
	@Field(1) 
	public set_conn_encrypt_cp encrypt(byte encrypt) {
		this.io.setByteField(this, 1, encrypt);
		return this;
	}
	public set_conn_encrypt_cp() {
		super();
	}
	public set_conn_encrypt_cp(Pointer pointer) {
		super(pointer);
	}
}
