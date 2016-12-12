package voice.linux.jna.bluetooth;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : linux\generator\src\main\c\\usr\include\bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("bluetooth") 
public class le_set_advertising_data_cp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte length() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public le_set_advertising_data_cp length(byte length) {
		this.io.setByteField(this, 0, length);
		return this;
	}
	/** C type : uint8_t[31] */
	@Array({31}) 
	@Field(1) 
	public Pointer<Byte > data() {
		return this.io.getPointerField(this, 1);
	}
	public le_set_advertising_data_cp() {
		super();
	}
	public le_set_advertising_data_cp(Pointer pointer) {
		super(pointer);
	}
}
