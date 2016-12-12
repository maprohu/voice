package voice.linux.jna.bluetooth;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : linux\generator\src\main\c\\usr\include\bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("bluetooth") 
public class hci_command_hdr extends StructObject {
	static {
		BridJ.register();
	}
	/** OCF & OGF */
	@Field(0) 
	public short opcode() {
		return this.io.getShortField(this, 0);
	}
	/** OCF & OGF */
	@Field(0) 
	public hci_command_hdr opcode(short opcode) {
		this.io.setShortField(this, 0, opcode);
		return this;
	}
	@Field(1) 
	public byte plen() {
		return this.io.getByteField(this, 1);
	}
	@Field(1) 
	public hci_command_hdr plen(byte plen) {
		this.io.setByteField(this, 1, plen);
		return this;
	}
	public hci_command_hdr() {
		super();
	}
	public hci_command_hdr(Pointer pointer) {
		super(pointer);
	}
}
