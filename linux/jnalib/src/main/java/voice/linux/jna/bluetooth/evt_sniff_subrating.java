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
public class evt_sniff_subrating extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte status() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public evt_sniff_subrating status(byte status) {
		this.io.setByteField(this, 0, status);
		return this;
	}
	@Field(1) 
	public short handle() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public evt_sniff_subrating handle(short handle) {
		this.io.setShortField(this, 1, handle);
		return this;
	}
	@Field(2) 
	public short max_tx_latency() {
		return this.io.getShortField(this, 2);
	}
	@Field(2) 
	public evt_sniff_subrating max_tx_latency(short max_tx_latency) {
		this.io.setShortField(this, 2, max_tx_latency);
		return this;
	}
	@Field(3) 
	public short max_rx_latency() {
		return this.io.getShortField(this, 3);
	}
	@Field(3) 
	public evt_sniff_subrating max_rx_latency(short max_rx_latency) {
		this.io.setShortField(this, 3, max_rx_latency);
		return this;
	}
	@Field(4) 
	public short min_remote_timeout() {
		return this.io.getShortField(this, 4);
	}
	@Field(4) 
	public evt_sniff_subrating min_remote_timeout(short min_remote_timeout) {
		this.io.setShortField(this, 4, min_remote_timeout);
		return this;
	}
	@Field(5) 
	public short min_local_timeout() {
		return this.io.getShortField(this, 5);
	}
	@Field(5) 
	public evt_sniff_subrating min_local_timeout(short min_local_timeout) {
		this.io.setShortField(this, 5, min_local_timeout);
		return this;
	}
	public evt_sniff_subrating() {
		super();
	}
	public evt_sniff_subrating(Pointer pointer) {
		super(pointer);
	}
}
