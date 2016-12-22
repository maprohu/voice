package voice.linux.jna.asound;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\seq_event.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class snd_seq_ev_raw8 extends Structure {
	/**
	 * < 8 bit value<br>
	 * C type : unsigned char[12]
	 */
	public byte[] d = new byte[12];
	public snd_seq_ev_raw8() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("d");
	}
	/**
	 * @param d < 8 bit value<br>
	 * C type : unsigned char[12]
	 */
	public snd_seq_ev_raw8(byte d[]) {
		super();
		if ((d.length != this.d.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.d = d;
	}
	public snd_seq_ev_raw8(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends snd_seq_ev_raw8 implements Structure.ByReference {
		
	};
	public static class ByValue extends snd_seq_ev_raw8 implements Structure.ByValue {
		
	};
}