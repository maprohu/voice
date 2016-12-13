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
public class qos_setup_cp extends Structure<qos_setup_cp, qos_setup_cp.ByValue, qos_setup_cp.ByReference > {
	public short handle;
	/** Reserved */
	public byte flags;
	/** C type : hci_qos */
	public hci_qos qos;
	public qos_setup_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("handle", "flags", "qos");
	}
	/**
	 * @param flags Reserved<br>
	 * @param qos C type : hci_qos
	 */
	public qos_setup_cp(short handle, byte flags, hci_qos qos) {
		super();
		this.handle = handle;
		this.flags = flags;
		this.qos = qos;
	}
	public qos_setup_cp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected qos_setup_cp newInstance() { return new qos_setup_cp(); }
	public static qos_setup_cp[] newArray(int arrayLength) {
		return Structure.newArray(qos_setup_cp.class, arrayLength);
	}
	public static class ByReference extends qos_setup_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends qos_setup_cp implements Structure.ByValue {
		
	};
}
