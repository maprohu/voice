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
public class read_clock_offset_cp extends Structure<read_clock_offset_cp, read_clock_offset_cp.ByValue, read_clock_offset_cp.ByReference > {
	public short handle;
	public read_clock_offset_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("handle");
	}
	public read_clock_offset_cp(short handle) {
		super();
		this.handle = handle;
	}
	public read_clock_offset_cp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected read_clock_offset_cp newInstance() { return new read_clock_offset_cp(); }
	public static read_clock_offset_cp[] newArray(int arrayLength) {
		return Structure.newArray(read_clock_offset_cp.class, arrayLength);
	}
	public static class ByReference extends read_clock_offset_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends read_clock_offset_cp implements Structure.ByValue {
		
	};
}
