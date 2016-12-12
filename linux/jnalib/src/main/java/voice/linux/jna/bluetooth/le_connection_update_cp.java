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
public class le_connection_update_cp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public short handle() {
		return this.io.getShortField(this, 0);
	}
	@Field(0) 
	public le_connection_update_cp handle(short handle) {
		this.io.setShortField(this, 0, handle);
		return this;
	}
	@Field(1) 
	public short min_interval() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public le_connection_update_cp min_interval(short min_interval) {
		this.io.setShortField(this, 1, min_interval);
		return this;
	}
	@Field(2) 
	public short max_interval() {
		return this.io.getShortField(this, 2);
	}
	@Field(2) 
	public le_connection_update_cp max_interval(short max_interval) {
		this.io.setShortField(this, 2, max_interval);
		return this;
	}
	@Field(3) 
	public short latency() {
		return this.io.getShortField(this, 3);
	}
	@Field(3) 
	public le_connection_update_cp latency(short latency) {
		this.io.setShortField(this, 3, latency);
		return this;
	}
	@Field(4) 
	public short supervision_timeout() {
		return this.io.getShortField(this, 4);
	}
	@Field(4) 
	public le_connection_update_cp supervision_timeout(short supervision_timeout) {
		this.io.setShortField(this, 4, supervision_timeout);
		return this;
	}
	@Field(5) 
	public short min_ce_length() {
		return this.io.getShortField(this, 5);
	}
	@Field(5) 
	public le_connection_update_cp min_ce_length(short min_ce_length) {
		this.io.setShortField(this, 5, min_ce_length);
		return this;
	}
	@Field(6) 
	public short max_ce_length() {
		return this.io.getShortField(this, 6);
	}
	@Field(6) 
	public le_connection_update_cp max_ce_length(short max_ce_length) {
		this.io.setShortField(this, 6, max_ce_length);
		return this;
	}
	public le_connection_update_cp() {
		super();
	}
	public le_connection_update_cp(Pointer pointer) {
		super(pointer);
	}
}
