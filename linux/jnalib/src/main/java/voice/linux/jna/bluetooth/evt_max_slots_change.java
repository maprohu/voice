package voice.linux.jna.bluetooth;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class evt_max_slots_change extends Structure<evt_max_slots_change, evt_max_slots_change.ByValue, evt_max_slots_change.ByReference > {
	public short handle;
	public byte max_slots;
	public evt_max_slots_change() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("handle", "max_slots");
	}
	public evt_max_slots_change(short handle, byte max_slots) {
		super();
		this.handle = handle;
		this.max_slots = max_slots;
	}
	public evt_max_slots_change(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected evt_max_slots_change newInstance() { return new evt_max_slots_change(); }
	public static evt_max_slots_change[] newArray(int arrayLength) {
		return Structure.newArray(evt_max_slots_change.class, arrayLength);
	}
	public static class ByReference extends evt_max_slots_change implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_max_slots_change implements Structure.ByValue {
		
	};
}
