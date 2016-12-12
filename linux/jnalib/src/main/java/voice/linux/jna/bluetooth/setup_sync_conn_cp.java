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
public class setup_sync_conn_cp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public short handle() {
		return this.io.getShortField(this, 0);
	}
	@Field(0) 
	public setup_sync_conn_cp handle(short handle) {
		this.io.setShortField(this, 0, handle);
		return this;
	}
	@Field(1) 
	public int tx_bandwith() {
		return this.io.getIntField(this, 1);
	}
	@Field(1) 
	public setup_sync_conn_cp tx_bandwith(int tx_bandwith) {
		this.io.setIntField(this, 1, tx_bandwith);
		return this;
	}
	@Field(2) 
	public int rx_bandwith() {
		return this.io.getIntField(this, 2);
	}
	@Field(2) 
	public setup_sync_conn_cp rx_bandwith(int rx_bandwith) {
		this.io.setIntField(this, 2, rx_bandwith);
		return this;
	}
	@Field(3) 
	public short max_latency() {
		return this.io.getShortField(this, 3);
	}
	@Field(3) 
	public setup_sync_conn_cp max_latency(short max_latency) {
		this.io.setShortField(this, 3, max_latency);
		return this;
	}
	@Field(4) 
	public short voice_setting() {
		return this.io.getShortField(this, 4);
	}
	@Field(4) 
	public setup_sync_conn_cp voice_setting(short voice_setting) {
		this.io.setShortField(this, 4, voice_setting);
		return this;
	}
	@Field(5) 
	public byte retrans_effort() {
		return this.io.getByteField(this, 5);
	}
	@Field(5) 
	public setup_sync_conn_cp retrans_effort(byte retrans_effort) {
		this.io.setByteField(this, 5, retrans_effort);
		return this;
	}
	@Field(6) 
	public short pkt_type() {
		return this.io.getShortField(this, 6);
	}
	@Field(6) 
	public setup_sync_conn_cp pkt_type(short pkt_type) {
		this.io.setShortField(this, 6, pkt_type);
		return this;
	}
	public setup_sync_conn_cp() {
		super();
	}
	public setup_sync_conn_cp(Pointer pointer) {
		super(pointer);
	}
}
