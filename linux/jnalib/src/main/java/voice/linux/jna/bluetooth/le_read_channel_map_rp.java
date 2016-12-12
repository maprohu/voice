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
public class le_read_channel_map_rp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte status() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public le_read_channel_map_rp status(byte status) {
		this.io.setByteField(this, 0, status);
		return this;
	}
	@Field(1) 
	public short handle() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public le_read_channel_map_rp handle(short handle) {
		this.io.setShortField(this, 1, handle);
		return this;
	}
	/** C type : uint8_t[5] */
	@Array({5}) 
	@Field(2) 
	public Pointer<Byte > map() {
		return this.io.getPointerField(this, 2);
	}
	public le_read_channel_map_rp() {
		super();
	}
	public le_read_channel_map_rp(Pointer pointer) {
		super(pointer);
	}
}
