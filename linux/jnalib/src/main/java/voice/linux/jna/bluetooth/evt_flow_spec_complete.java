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
public class evt_flow_spec_complete extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte status() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public evt_flow_spec_complete status(byte status) {
		this.io.setByteField(this, 0, status);
		return this;
	}
	@Field(1) 
	public short handle() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public evt_flow_spec_complete handle(short handle) {
		this.io.setShortField(this, 1, handle);
		return this;
	}
	@Field(2) 
	public byte flags() {
		return this.io.getByteField(this, 2);
	}
	@Field(2) 
	public evt_flow_spec_complete flags(byte flags) {
		this.io.setByteField(this, 2, flags);
		return this;
	}
	@Field(3) 
	public byte direction() {
		return this.io.getByteField(this, 3);
	}
	@Field(3) 
	public evt_flow_spec_complete direction(byte direction) {
		this.io.setByteField(this, 3, direction);
		return this;
	}
	/** C type : hci_qos */
	@Field(4) 
	public hci_qos qos() {
		return this.io.getNativeObjectField(this, 4);
	}
	/** C type : hci_qos */
	@Field(4) 
	public evt_flow_spec_complete qos(hci_qos qos) {
		this.io.setNativeObjectField(this, 4, qos);
		return this;
	}
	public evt_flow_spec_complete() {
		super();
	}
	public evt_flow_spec_complete(Pointer pointer) {
		super(pointer);
	}
}
