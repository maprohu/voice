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
public class cmplt_handle extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public short handle() {
		return this.io.getShortField(this, 0);
	}
	@Field(0) 
	public cmplt_handle handle(short handle) {
		this.io.setShortField(this, 0, handle);
		return this;
	}
	@Field(1) 
	public short num_cmplt_pkts() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public cmplt_handle num_cmplt_pkts(short num_cmplt_pkts) {
		this.io.setShortField(this, 1, num_cmplt_pkts);
		return this;
	}
	@Field(2) 
	public short num_cmplt_blks() {
		return this.io.getShortField(this, 2);
	}
	@Field(2) 
	public cmplt_handle num_cmplt_blks(short num_cmplt_blks) {
		this.io.setShortField(this, 2, num_cmplt_blks);
		return this;
	}
	public cmplt_handle() {
		super();
	}
	public cmplt_handle(Pointer pointer) {
		super(pointer);
	}
}
