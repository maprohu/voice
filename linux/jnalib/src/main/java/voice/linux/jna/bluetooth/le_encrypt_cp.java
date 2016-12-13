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
public class le_encrypt_cp extends Structure {
	/** C type : uint8_t[16] */
	public byte[] key = new byte[16];
	/** C type : uint8_t[16] */
	public byte[] plaintext = new byte[16];
	public le_encrypt_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("key", "plaintext");
	}
	/**
	 * @param key C type : uint8_t[16]<br>
	 * @param plaintext C type : uint8_t[16]
	 */
	public le_encrypt_cp(byte key[], byte plaintext[]) {
		super();
		if ((key.length != this.key.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.key = key;
		if ((plaintext.length != this.plaintext.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.plaintext = plaintext;
	}
	public le_encrypt_cp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends le_encrypt_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends le_encrypt_cp implements Structure.ByValue {
		
	};
}
