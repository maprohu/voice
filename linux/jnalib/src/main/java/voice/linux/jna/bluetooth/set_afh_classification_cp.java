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
public class set_afh_classification_cp extends Structure {
	/** C type : uint8_t[10] */
	public byte[] map = new byte[10];
	public set_afh_classification_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("map");
	}
	/** @param map C type : uint8_t[10] */
	public set_afh_classification_cp(byte map[]) {
		super();
		if ((map.length != this.map.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.map = map;
	}
	public set_afh_classification_cp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends set_afh_classification_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends set_afh_classification_cp implements Structure.ByValue {
		
	};
}
