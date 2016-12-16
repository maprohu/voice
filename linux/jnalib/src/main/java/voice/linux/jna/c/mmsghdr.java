package voice.linux.jna.c;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * For `recvmmsg' and `sendmmsg'.<br>
 * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\sys\socket.h:96</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class mmsghdr extends Structure {
	/**
	 * Actual message header.<br>
	 * C type : msghdr
	 */
	public msghdr msg_hdr;
	/**
	 * Number of received or sent bytes for the<br>
	 * entry.
	 */
	public int msg_len;
	public mmsghdr() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("msg_hdr", "msg_len");
	}
	/**
	 * @param msg_hdr Actual message header.<br>
	 * C type : msghdr<br>
	 * @param msg_len Number of received or sent bytes for the<br>
	 * entry.
	 */
	public mmsghdr(msghdr msg_hdr, int msg_len) {
		super();
		this.msg_hdr = msg_hdr;
		this.msg_len = msg_len;
	}
	public mmsghdr(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends mmsghdr implements Structure.ByReference {
		
	};
	public static class ByValue extends mmsghdr implements Structure.ByValue {
		
	};
}
