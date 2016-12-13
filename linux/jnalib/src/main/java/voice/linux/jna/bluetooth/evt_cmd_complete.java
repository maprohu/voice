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
public class evt_cmd_complete extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte ncmd() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public evt_cmd_complete ncmd(byte ncmd) {
		this.io.setByteField(this, 0, ncmd);
		return this;
	}
	@Field(1) 
	public short opcode() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public evt_cmd_complete opcode(short opcode) {
		this.io.setShortField(this, 1, opcode);
		return this;
	}
	public evt_cmd_complete() {
		super();
	}
	public evt_cmd_complete(Pointer pointer) {
		super(pointer);
	}
}
