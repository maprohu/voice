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
public class read_local_amp_info_rp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public byte status() {
		return this.io.getByteField(this, 0);
	}
	@Field(0) 
	public read_local_amp_info_rp status(byte status) {
		this.io.setByteField(this, 0, status);
		return this;
	}
	@Field(1) 
	public byte amp_status() {
		return this.io.getByteField(this, 1);
	}
	@Field(1) 
	public read_local_amp_info_rp amp_status(byte amp_status) {
		this.io.setByteField(this, 1, amp_status);
		return this;
	}
	@Field(2) 
	public int total_bandwidth() {
		return this.io.getIntField(this, 2);
	}
	@Field(2) 
	public read_local_amp_info_rp total_bandwidth(int total_bandwidth) {
		this.io.setIntField(this, 2, total_bandwidth);
		return this;
	}
	@Field(3) 
	public int max_guaranteed_bandwidth() {
		return this.io.getIntField(this, 3);
	}
	@Field(3) 
	public read_local_amp_info_rp max_guaranteed_bandwidth(int max_guaranteed_bandwidth) {
		this.io.setIntField(this, 3, max_guaranteed_bandwidth);
		return this;
	}
	@Field(4) 
	public int min_latency() {
		return this.io.getIntField(this, 4);
	}
	@Field(4) 
	public read_local_amp_info_rp min_latency(int min_latency) {
		this.io.setIntField(this, 4, min_latency);
		return this;
	}
	@Field(5) 
	public int max_pdu_size() {
		return this.io.getIntField(this, 5);
	}
	@Field(5) 
	public read_local_amp_info_rp max_pdu_size(int max_pdu_size) {
		this.io.setIntField(this, 5, max_pdu_size);
		return this;
	}
	@Field(6) 
	public byte controller_type() {
		return this.io.getByteField(this, 6);
	}
	@Field(6) 
	public read_local_amp_info_rp controller_type(byte controller_type) {
		this.io.setByteField(this, 6, controller_type);
		return this;
	}
	@Field(7) 
	public short pal_caps() {
		return this.io.getShortField(this, 7);
	}
	@Field(7) 
	public read_local_amp_info_rp pal_caps(short pal_caps) {
		this.io.setShortField(this, 7, pal_caps);
		return this;
	}
	@Field(8) 
	public short max_amp_assoc_length() {
		return this.io.getShortField(this, 8);
	}
	@Field(8) 
	public read_local_amp_info_rp max_amp_assoc_length(short max_amp_assoc_length) {
		this.io.setShortField(this, 8, max_amp_assoc_length);
		return this;
	}
	@Field(9) 
	public int max_flush_timeout() {
		return this.io.getIntField(this, 9);
	}
	@Field(9) 
	public read_local_amp_info_rp max_flush_timeout(int max_flush_timeout) {
		this.io.setIntField(this, 9, max_flush_timeout);
		return this;
	}
	@Field(10) 
	public int best_effort_flush_timeout() {
		return this.io.getIntField(this, 10);
	}
	@Field(10) 
	public read_local_amp_info_rp best_effort_flush_timeout(int best_effort_flush_timeout) {
		this.io.setIntField(this, 10, best_effort_flush_timeout);
		return this;
	}
	public read_local_amp_info_rp() {
		super();
	}
	public read_local_amp_info_rp(Pointer pointer) {
		super(pointer);
	}
}
