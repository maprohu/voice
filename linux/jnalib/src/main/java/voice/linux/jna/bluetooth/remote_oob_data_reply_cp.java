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
public class remote_oob_data_reply_cp extends Structure {
	/** C type : bdaddr_t */
	public bdaddr_t bdaddr;
	/** C type : uint8_t[16] */
	public byte[] hash = new byte[16];
	/** C type : uint8_t[16] */
	public byte[] randomizer = new byte[16];
	public remote_oob_data_reply_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("bdaddr", "hash", "randomizer");
	}
	/**
	 * @param bdaddr C type : bdaddr_t<br>
	 * @param hash C type : uint8_t[16]<br>
	 * @param randomizer C type : uint8_t[16]
	 */
	public remote_oob_data_reply_cp(bdaddr_t bdaddr, byte hash[], byte randomizer[]) {
		super();
		this.bdaddr = bdaddr;
		if ((hash.length != this.hash.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.hash = hash;
		if ((randomizer.length != this.randomizer.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.randomizer = randomizer;
	}
	public remote_oob_data_reply_cp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends remote_oob_data_reply_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends remote_oob_data_reply_cp implements Structure.ByValue {
		
	};
}
