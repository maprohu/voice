package voice.linux.jna.bluetooth;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
/**
 * RFCOMM TTY support<br>
 * <i>native declaration : bluetooth\rfcomm.h:37</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class rfcomm_dev_req extends Structure<rfcomm_dev_req, rfcomm_dev_req.ByValue, rfcomm_dev_req.ByReference > {
	public short dev_id;
	public int flags;
	/** C type : bdaddr_t */
	public bdaddr_t src;
	/** C type : bdaddr_t */
	public bdaddr_t dst;
	public byte channel;
	public rfcomm_dev_req() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("dev_id", "flags", "src", "dst", "channel");
	}
	/**
	 * @param src C type : bdaddr_t<br>
	 * @param dst C type : bdaddr_t
	 */
	public rfcomm_dev_req(short dev_id, int flags, bdaddr_t src, bdaddr_t dst, byte channel) {
		super();
		this.dev_id = dev_id;
		this.flags = flags;
		this.src = src;
		this.dst = dst;
		this.channel = channel;
	}
	public rfcomm_dev_req(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected rfcomm_dev_req newInstance() { return new rfcomm_dev_req(); }
	public static rfcomm_dev_req[] newArray(int arrayLength) {
		return Structure.newArray(rfcomm_dev_req.class, arrayLength);
	}
	public static class ByReference extends rfcomm_dev_req implements Structure.ByReference {
		
	};
	public static class ByValue extends rfcomm_dev_req implements Structure.ByValue {
		
	};
}