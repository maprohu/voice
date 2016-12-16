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
public class change_local_name_cp extends Structure<change_local_name_cp, change_local_name_cp.ByValue, change_local_name_cp.ByReference > {
	/** C type : uint8_t[248] */
	public byte[] name = new byte[248];
	public change_local_name_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("name");
	}
	/** @param name C type : uint8_t[248] */
	public change_local_name_cp(byte name[]) {
		super();
		if ((name.length != this.name.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.name = name;
	}
	public change_local_name_cp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected change_local_name_cp newInstance() { return new change_local_name_cp(); }
	public static change_local_name_cp[] newArray(int arrayLength) {
		return Structure.newArray(change_local_name_cp.class, arrayLength);
	}
	public static class ByReference extends change_local_name_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends change_local_name_cp implements Structure.ByValue {
		
	};
}