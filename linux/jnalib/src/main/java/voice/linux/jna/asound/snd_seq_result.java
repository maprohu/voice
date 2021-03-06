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
public class snd_seq_result extends Structure {
	/** < processed event type */
	public int event;
	/** < status */
	public int result;
	public snd_seq_result() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("event", "result");
	}
	/**
	 * @param event < processed event type<br>
	 * @param result < status
	 */
	public snd_seq_result(int event, int result) {
		super();
		this.event = event;
		this.result = result;
	}
	public snd_seq_result(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends snd_seq_result implements Structure.ByReference {
		
	};
	public static class ByValue extends snd_seq_result implements Structure.ByValue {
		
	};
}
