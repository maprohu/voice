package voice.linux.jna.asound;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
import voice.linux.jna.asound.AsoundLibrary.snd_htimestamp_t;
/**
 * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\timer.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class snd_timer_tread_t extends Structure {
	/**
	 * @see snd_timer_event_t<br>
	 * < Timer event<br>
	 * C type : snd_timer_event_t
	 */
	public int event;
	/**
	 * < Time stamp of each event<br>
	 * C type : snd_htimestamp_t
	 */
	public snd_htimestamp_t tstamp;
	/** < Event value */
	public int val;
	public snd_timer_tread_t() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("event", "tstamp", "val");
	}
	/**
	 * @param event @see snd_timer_event_t<br>
	 * < Timer event<br>
	 * C type : snd_timer_event_t<br>
	 * @param tstamp < Time stamp of each event<br>
	 * C type : snd_htimestamp_t<br>
	 * @param val < Event value
	 */
	public snd_timer_tread_t(int event, snd_htimestamp_t tstamp, int val) {
		super();
		this.event = event;
		this.tstamp = tstamp;
		this.val = val;
	}
	public snd_timer_tread_t(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends snd_timer_tread_t implements Structure.ByReference {
		
	};
	public static class ByValue extends snd_timer_tread_t implements Structure.ByValue {
		
	};
}
