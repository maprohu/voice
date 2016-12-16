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
public class read_best_effort_flush_timeout_rp extends Structure<read_best_effort_flush_timeout_rp, read_best_effort_flush_timeout_rp.ByValue, read_best_effort_flush_timeout_rp.ByReference > {
	public byte status;
	public int timeout;
	public read_best_effort_flush_timeout_rp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "timeout");
	}
	public read_best_effort_flush_timeout_rp(byte status, int timeout) {
		super();
		this.status = status;
		this.timeout = timeout;
	}
	public read_best_effort_flush_timeout_rp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected read_best_effort_flush_timeout_rp newInstance() { return new read_best_effort_flush_timeout_rp(); }
	public static read_best_effort_flush_timeout_rp[] newArray(int arrayLength) {
		return Structure.newArray(read_best_effort_flush_timeout_rp.class, arrayLength);
	}
	public static class ByReference extends read_best_effort_flush_timeout_rp implements Structure.ByReference {
		
	};
	public static class ByValue extends read_best_effort_flush_timeout_rp implements Structure.ByValue {
		
	};
}