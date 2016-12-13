package voice.linux.jna.bluetooth;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * HCI CMSG flags<br>
 * <i>native declaration : bluetooth\hci.h:2276</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class sockaddr_hci extends Structure {
	/** C type : sa_family_t */
	public short hci_family;
	public short hci_dev;
	public short hci_channel;
	public sockaddr_hci() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("hci_family", "hci_dev", "hci_channel");
	}
	/** @param hci_family C type : sa_family_t */
	public sockaddr_hci(short hci_family, short hci_dev, short hci_channel) {
		super();
		this.hci_family = hci_family;
		this.hci_dev = hci_dev;
		this.hci_channel = hci_channel;
	}
	public sockaddr_hci(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends sockaddr_hci implements Structure.ByReference {
		
	};
	public static class ByValue extends sockaddr_hci implements Structure.ByValue {
		
	};
}
