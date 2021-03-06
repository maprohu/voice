package voice.linux.jna.asound;
import com.sun.jna.Pointer;
import com.sun.jna.Union;
/**
 * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\seq_event.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class snd_seq_timestamp extends Union {
	/**
	 * < tick-time<br>
	 * C type : snd_seq_tick_time_t
	 */
	public int tick;
	/**
	 * < real-time<br>
	 * C type : snd_seq_real_time
	 */
	public snd_seq_real_time time;
	public snd_seq_timestamp() {
		super();
	}
	/**
	 * @param tick < tick-time<br>
	 * C type : snd_seq_tick_time_t
	 */
	public snd_seq_timestamp(int tick) {
		super();
		this.tick = tick;
		setType(Integer.TYPE);
	}
	/**
	 * @param time < real-time<br>
	 * C type : snd_seq_real_time
	 */
	public snd_seq_timestamp(snd_seq_real_time time) {
		super();
		this.time = time;
		setType(snd_seq_real_time.class);
	}
	public snd_seq_timestamp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends snd_seq_timestamp implements com.sun.jna.Structure.ByReference {
		
	};
	public static class ByValue extends snd_seq_timestamp implements com.sun.jna.Structure.ByValue {
		
	};
}
