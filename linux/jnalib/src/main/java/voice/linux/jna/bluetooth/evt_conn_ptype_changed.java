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
public class evt_conn_ptype_changed extends Structure {
	public byte status;
	public short handle;
	public short ptype;
	public evt_conn_ptype_changed() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "handle", "ptype");
	}
	public evt_conn_ptype_changed(byte status, short handle, short ptype) {
		super();
		this.status = status;
		this.handle = handle;
		this.ptype = ptype;
	}
	public evt_conn_ptype_changed(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends evt_conn_ptype_changed implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_conn_ptype_changed implements Structure.ByValue {
		
	};
}
