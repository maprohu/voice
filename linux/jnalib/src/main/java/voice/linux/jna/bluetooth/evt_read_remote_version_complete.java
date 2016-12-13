package voice.linux.jna.bluetooth;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class evt_read_remote_version_complete extends Structure<evt_read_remote_version_complete, evt_read_remote_version_complete.ByValue, evt_read_remote_version_complete.ByReference > {
	public byte status;
	public short handle;
	public byte lmp_ver;
	public short manufacturer;
	public short lmp_subver;
	public evt_read_remote_version_complete() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "handle", "lmp_ver", "manufacturer", "lmp_subver");
	}
	public evt_read_remote_version_complete(byte status, short handle, byte lmp_ver, short manufacturer, short lmp_subver) {
		super();
		this.status = status;
		this.handle = handle;
		this.lmp_ver = lmp_ver;
		this.manufacturer = manufacturer;
		this.lmp_subver = lmp_subver;
	}
	public evt_read_remote_version_complete(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected evt_read_remote_version_complete newInstance() { return new evt_read_remote_version_complete(); }
	public static evt_read_remote_version_complete[] newArray(int arrayLength) {
		return Structure.newArray(evt_read_remote_version_complete.class, arrayLength);
	}
	public static class ByReference extends evt_read_remote_version_complete implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_read_remote_version_complete implements Structure.ByValue {
		
	};
}
