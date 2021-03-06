package voice.linux.jna.c;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * Structure describing a generic socket address.<br>
 * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\bits\socket.h:92</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class sockaddr extends Structure {
	/**
	 * Common data: address family and length.<br>
	 * C type : sa_family_t
	 */
	public short sa_family;
	/**
	 * Address data.<br>
	 * C type : char[14]
	 */
	public byte[] sa_data = new byte[14];
	public sockaddr() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("sa_family", "sa_data");
	}
	/**
	 * @param sa_family Common data: address family and length.<br>
	 * C type : sa_family_t<br>
	 * @param sa_data Address data.<br>
	 * C type : char[14]
	 */
	public sockaddr(short sa_family, byte sa_data[]) {
		super();
		this.sa_family = sa_family;
		if ((sa_data.length != this.sa_data.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.sa_data = sa_data;
	}
	public sockaddr(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends sockaddr implements Structure.ByReference {
		
	};
	public static class ByValue extends sockaddr implements Structure.ByValue {
		
	};
}
