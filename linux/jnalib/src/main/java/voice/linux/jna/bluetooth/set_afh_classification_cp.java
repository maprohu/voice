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
public class set_afh_classification_cp extends Structure<set_afh_classification_cp, set_afh_classification_cp.ByValue, set_afh_classification_cp.ByReference > {
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
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected set_afh_classification_cp newInstance() { return new set_afh_classification_cp(); }
	public static set_afh_classification_cp[] newArray(int arrayLength) {
		return Structure.newArray(set_afh_classification_cp.class, arrayLength);
	}
	public static class ByReference extends set_afh_classification_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends set_afh_classification_cp implements Structure.ByValue {
		
	};
}
