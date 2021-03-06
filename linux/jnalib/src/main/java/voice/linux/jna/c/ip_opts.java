package voice.linux.jna.c;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import voice.linux.jna.c.CLibrary.in_addr;
/**
 * Structure used to describe IP options for IP_OPTIONS and IP_RETOPTS.<br>
 * The `ip_dst' field is used for the first-hop gateway when using a<br>
 * source route (this gets put into the header proper).<br>
 * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\bits\in.h:112</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class ip_opts extends Structure {
	/**
	 * First hop; zero without source route.<br>
	 * C type : in_addr
	 */
	public in_addr ip_dst;
	/**
	 * Actually variable in size.<br>
	 * C type : char[40]
	 */
	public byte[] ip_opts = new byte[40];
	public ip_opts() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("ip_dst", "ip_opts");
	}
	/**
	 * @param ip_dst First hop; zero without source route.<br>
	 * C type : in_addr<br>
	 * @param ip_opts Actually variable in size.<br>
	 * C type : char[40]
	 */
	public ip_opts(in_addr ip_dst, byte ip_opts[]) {
		super();
		this.ip_dst = ip_dst;
		if ((ip_opts.length != this.ip_opts.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.ip_opts = ip_opts;
	}
	public ip_opts(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends ip_opts implements Structure.ByReference {
		
	};
	public static class ByValue extends ip_opts implements Structure.ByValue {
		
	};
}
