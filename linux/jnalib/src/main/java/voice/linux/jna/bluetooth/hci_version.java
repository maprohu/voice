package voice.linux.jna.bluetooth;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci_lib.h:43</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class hci_version extends Structure {
	public short manufacturer;
	public byte hci_ver;
	public short hci_rev;
	public byte lmp_ver;
	public short lmp_subver;
	public hci_version() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("manufacturer", "hci_ver", "hci_rev", "lmp_ver", "lmp_subver");
	}
	public hci_version(short manufacturer, byte hci_ver, short hci_rev, byte lmp_ver, short lmp_subver) {
		super();
		this.manufacturer = manufacturer;
		this.hci_ver = hci_ver;
		this.hci_rev = hci_rev;
		this.lmp_ver = lmp_ver;
		this.lmp_subver = lmp_subver;
	}
	public hci_version(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends hci_version implements Structure.ByReference {
		
	};
	public static class ByValue extends hci_version implements Structure.ByValue {
		
	};
}
