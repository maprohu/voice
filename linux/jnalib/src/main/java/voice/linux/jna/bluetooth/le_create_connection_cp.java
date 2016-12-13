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
public class le_create_connection_cp extends StructObject {
	static {
		BridJ.register();
	}
	@Field(0) 
	public short interval() {
		return this.io.getShortField(this, 0);
	}
	@Field(0) 
	public le_create_connection_cp interval(short interval) {
		this.io.setShortField(this, 0, interval);
		return this;
	}
	@Field(1) 
	public short window() {
		return this.io.getShortField(this, 1);
	}
	@Field(1) 
	public le_create_connection_cp window(short window) {
		this.io.setShortField(this, 1, window);
		return this;
	}
	@Field(2) 
	public byte initiator_filter() {
		return this.io.getByteField(this, 2);
	}
	@Field(2) 
	public le_create_connection_cp initiator_filter(byte initiator_filter) {
		this.io.setByteField(this, 2, initiator_filter);
		return this;
	}
	@Field(3) 
	public byte peer_bdaddr_type() {
		return this.io.getByteField(this, 3);
	}
	@Field(3) 
	public le_create_connection_cp peer_bdaddr_type(byte peer_bdaddr_type) {
		this.io.setByteField(this, 3, peer_bdaddr_type);
		return this;
	}
	/** C type : bdaddr_t */
	@Field(4) 
	public bdaddr_t peer_bdaddr() {
		return this.io.getNativeObjectField(this, 4);
	}
	/** C type : bdaddr_t */
	@Field(4) 
	public le_create_connection_cp peer_bdaddr(bdaddr_t peer_bdaddr) {
		this.io.setNativeObjectField(this, 4, peer_bdaddr);
		return this;
	}
	@Field(5) 
	public byte own_bdaddr_type() {
		return this.io.getByteField(this, 5);
	}
	@Field(5) 
	public le_create_connection_cp own_bdaddr_type(byte own_bdaddr_type) {
		this.io.setByteField(this, 5, own_bdaddr_type);
		return this;
	}
	@Field(6) 
	public short min_interval() {
		return this.io.getShortField(this, 6);
	}
	@Field(6) 
	public le_create_connection_cp min_interval(short min_interval) {
		this.io.setShortField(this, 6, min_interval);
		return this;
	}
	@Field(7) 
	public short max_interval() {
		return this.io.getShortField(this, 7);
	}
	@Field(7) 
	public le_create_connection_cp max_interval(short max_interval) {
		this.io.setShortField(this, 7, max_interval);
		return this;
	}
	@Field(8) 
	public short latency() {
		return this.io.getShortField(this, 8);
	}
	@Field(8) 
	public le_create_connection_cp latency(short latency) {
		this.io.setShortField(this, 8, latency);
		return this;
	}
	@Field(9) 
	public short supervision_timeout() {
		return this.io.getShortField(this, 9);
	}
	@Field(9) 
	public le_create_connection_cp supervision_timeout(short supervision_timeout) {
		this.io.setShortField(this, 9, supervision_timeout);
		return this;
	}
	@Field(10) 
	public short min_ce_length() {
		return this.io.getShortField(this, 10);
	}
	@Field(10) 
	public le_create_connection_cp min_ce_length(short min_ce_length) {
		this.io.setShortField(this, 10, min_ce_length);
		return this;
	}
	@Field(11) 
	public short max_ce_length() {
		return this.io.getShortField(this, 11);
	}
	@Field(11) 
	public le_create_connection_cp max_ce_length(short max_ce_length) {
		this.io.setShortField(this, 11, max_ce_length);
		return this;
	}
	public le_create_connection_cp() {
		super();
	}
	public le_create_connection_cp(Pointer pointer) {
		super(pointer);
	}
}
