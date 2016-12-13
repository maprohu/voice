package voice.linux.jna.bluetooth;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("bluetooth") 
public class le_start_encryption_cp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public short handle() {
		return this.io.getShortField(this, 0);
	}
	@Field(0) 
	public le_start_encryption_cp handle(short handle) {
		this.io.setShortField(this, 0, handle);
		return this;
	}
	@Field(1) 
	public long random() {
		return this.io.getLongField(this, 1);
	}
	@Field(1) 
	public le_start_encryption_cp random(long random) {
		this.io.setLongField(this, 1, random);
		return this;
	}
	@Field(2) 
	public short diversifier() {
		return this.io.getShortField(this, 2);
	}
	@Field(2) 
	public le_start_encryption_cp diversifier(short diversifier) {
		this.io.setShortField(this, 2, diversifier);
		return this;
	}
	/** C type : uint8_t[16] */
	@Array({16}) 
	@Field(3) 
	public Pointer<Byte > key() {
		return this.io.getPointerField(this, 3);
	}
	public le_start_encryption_cp() {
		super();
	}
	public le_start_encryption_cp(Pointer pointer) {
		super(pointer);
	}
}
