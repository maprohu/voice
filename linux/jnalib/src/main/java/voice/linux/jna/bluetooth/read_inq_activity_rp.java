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
public class read_inq_activity_rp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte status() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public read_inq_activity_rp status(byte status) {
		this.io.setByteField(this, 0, status);
		return this;
	}
	@Field(1) 
	public short interval() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public read_inq_activity_rp interval(short interval) {
		this.io.setShortField(this, 1, interval);
		return this;
	}
	@Field(2) 
	public short window() {
		return this.io.getShortField(this, 2);
	}
	@Field(2) 
	public read_inq_activity_rp window(short window) {
		this.io.setShortField(this, 2, window);
		return this;
	}
	public read_inq_activity_rp() {
		super();
	}
	public read_inq_activity_rp(Pointer pointer) {
		super(pointer);
	}
}
