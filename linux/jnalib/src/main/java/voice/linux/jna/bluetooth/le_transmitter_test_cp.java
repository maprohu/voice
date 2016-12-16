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
public class le_transmitter_test_cp extends Structure {
	public byte frequency;
	public byte length;
	public byte payload;
	public le_transmitter_test_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("frequency", "length", "payload");
	}
	public le_transmitter_test_cp(byte frequency, byte length, byte payload) {
		super();
		this.frequency = frequency;
		this.length = length;
		this.payload = payload;
	}
	public le_transmitter_test_cp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends le_transmitter_test_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends le_transmitter_test_cp implements Structure.ByValue {
		
	};
}
