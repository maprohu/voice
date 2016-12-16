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
public class write_afh_mode_cp extends Structure<write_afh_mode_cp, write_afh_mode_cp.ByValue, write_afh_mode_cp.ByReference > {
	public byte mode;
	public write_afh_mode_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("mode");
	}
	public write_afh_mode_cp(byte mode) {
		super();
		this.mode = mode;
	}
	public write_afh_mode_cp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected write_afh_mode_cp newInstance() { return new write_afh_mode_cp(); }
	public static write_afh_mode_cp[] newArray(int arrayLength) {
		return Structure.newArray(write_afh_mode_cp.class, arrayLength);
	}
	public static class ByReference extends write_afh_mode_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends write_afh_mode_cp implements Structure.ByValue {
		
	};
}