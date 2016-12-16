package voice.linux.jna.bluetooth;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class evt_logical_link_complete extends Structure {
	public byte status;
	public short log_handle;
	public byte handle;
	public byte tx_flow_id;
	public evt_logical_link_complete() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "log_handle", "handle", "tx_flow_id");
	}
	public evt_logical_link_complete(byte status, short log_handle, byte handle, byte tx_flow_id) {
		super();
		this.status = status;
		this.log_handle = log_handle;
		this.handle = handle;
		this.tx_flow_id = tx_flow_id;
	}
	public evt_logical_link_complete(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends evt_logical_link_complete implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_logical_link_complete implements Structure.ByValue {
		
	};
}
