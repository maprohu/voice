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
public class le_read_remote_used_features_cp extends Structure {
	public short handle;
	public le_read_remote_used_features_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("handle");
	}
	public le_read_remote_used_features_cp(short handle) {
		super();
		this.handle = handle;
	}
	public le_read_remote_used_features_cp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends le_read_remote_used_features_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends le_read_remote_used_features_cp implements Structure.ByValue {
		
	};
}
