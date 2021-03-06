package voice.linux.jna.bluetooth;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class periodic_inquiry_cp extends Structure {
	/** 1.28s units */
	public short max_period;
	/** 1.28s units */
	public short min_period;
	/** C type : uint8_t[3] */
	public byte[] lap = new byte[3];
	/** 1.28s units */
	public byte length;
	public byte num_rsp;
	public periodic_inquiry_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("max_period", "min_period", "lap", "length", "num_rsp");
	}
	/**
	 * @param max_period 1.28s units<br>
	 * @param min_period 1.28s units<br>
	 * @param lap C type : uint8_t[3]<br>
	 * @param length 1.28s units
	 */
	public periodic_inquiry_cp(short max_period, short min_period, byte lap[], byte length, byte num_rsp) {
		super();
		this.max_period = max_period;
		this.min_period = min_period;
		if ((lap.length != this.lap.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.lap = lap;
		this.length = length;
		this.num_rsp = num_rsp;
	}
	public periodic_inquiry_cp(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends periodic_inquiry_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends periodic_inquiry_cp implements Structure.ByValue {
		
	};
}
