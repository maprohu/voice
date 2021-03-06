package voice.linux.jna.bluetooth;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h:2288</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class hci_filter extends Structure {
	public int type_mask;
	/** C type : uint32_t[2] */
	public int[] event_mask = new int[2];
	public short opcode;
	public hci_filter() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("type_mask", "event_mask", "opcode");
	}
	/** @param event_mask C type : uint32_t[2] */
	public hci_filter(int type_mask, int event_mask[], short opcode) {
		super();
		this.type_mask = type_mask;
		if ((event_mask.length != this.event_mask.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.event_mask = event_mask;
		this.opcode = opcode;
	}
	public hci_filter(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends hci_filter implements Structure.ByReference {
		
	};
	public static class ByValue extends hci_filter implements Structure.ByValue {
		
	};
}
