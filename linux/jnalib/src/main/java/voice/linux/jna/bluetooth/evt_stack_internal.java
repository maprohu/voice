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
public class evt_stack_internal extends Structure {
	public short type;
	/** C type : uint8_t[0] */
	public byte[] data = new byte[0];
	public evt_stack_internal() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("type", "data");
	}
	/** @param data C type : uint8_t[0] */
	public evt_stack_internal(short type, byte data[]) {
		super();
		this.type = type;
		if ((data.length != this.data.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.data = data;
	}
	public evt_stack_internal(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends evt_stack_internal implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_stack_internal implements Structure.ByValue {
		
	};
}
