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
public class snd_seq_real_time extends Structure {
	/** < seconds */
	public int tv_sec;
	/** < nanoseconds */
	public int tv_nsec;
	public snd_seq_real_time() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("tv_sec", "tv_nsec");
	}
	/**
	 * @param tv_sec < seconds<br>
	 * @param tv_nsec < nanoseconds
	 */
	public snd_seq_real_time(int tv_sec, int tv_nsec) {
		super();
		this.tv_sec = tv_sec;
		this.tv_nsec = tv_nsec;
	}
	public snd_seq_real_time(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends snd_seq_real_time implements Structure.ByReference {
		
	};
	public static class ByValue extends snd_seq_real_time implements Structure.ByValue {
		
	};
}
