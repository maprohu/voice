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
public class le_transmitter_test_cp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte frequency() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public le_transmitter_test_cp frequency(byte frequency) {
		this.io.setByteField(this, 0, frequency);
		return this;
	}
	@Field(1) 
	public byte length() {
		return this.io.getByteField(this, 1);
	}
	@Field(1) 
	public le_transmitter_test_cp length(byte length) {
		this.io.setByteField(this, 1, length);
		return this;
	}
	@Field(2) 
	public byte payload() {
		return this.io.getByteField(this, 2);
	}
	@Field(2) 
	public le_transmitter_test_cp payload(byte payload) {
		this.io.setByteField(this, 2, payload);
		return this;
	}
	public le_transmitter_test_cp() {
		super();
	}
	public le_transmitter_test_cp(Pointer pointer) {
		super(pointer);
	}
}
