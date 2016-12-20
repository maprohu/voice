package voice.linux.jna.c;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import java.util.Arrays;
import java.util.List;
/**
 * Reentrant versions of the `random' family of functions.<br>
 * These functions all use the following data structure to contain<br>
 * state, rather than global state variables.<br>
 * <i>native declaration : ..\voice\local\headers\\usr\include\stdlib.h:29</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class random_data extends Structure {
	/**
	 * Front pointer.<br>
	 * C type : int32_t*
	 */
	public IntByReference fptr;
	/**
	 * Rear pointer.<br>
	 * C type : int32_t*
	 */
	public IntByReference rptr;
	/**
	 * Array of state values.<br>
	 * C type : int32_t*
	 */
	public IntByReference state;
	/** Type of random number generator. */
	public int rand_type;
	/** Degree of random number generator. */
	public int rand_deg;
	/** Distance between front and rear. */
	public int rand_sep;
	/**
	 * Pointer behind state table.<br>
	 * C type : int32_t*
	 */
	public IntByReference end_ptr;
	public random_data() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("fptr", "rptr", "state", "rand_type", "rand_deg", "rand_sep", "end_ptr");
	}
	/**
	 * @param fptr Front pointer.<br>
	 * C type : int32_t*<br>
	 * @param rptr Rear pointer.<br>
	 * C type : int32_t*<br>
	 * @param state Array of state values.<br>
	 * C type : int32_t*<br>
	 * @param rand_type Type of random number generator.<br>
	 * @param rand_deg Degree of random number generator.<br>
	 * @param rand_sep Distance between front and rear.<br>
	 * @param end_ptr Pointer behind state table.<br>
	 * C type : int32_t*
	 */
	public random_data(IntByReference fptr, IntByReference rptr, IntByReference state, int rand_type, int rand_deg, int rand_sep, IntByReference end_ptr) {
		super();
		this.fptr = fptr;
		this.rptr = rptr;
		this.state = state;
		this.rand_type = rand_type;
		this.rand_deg = rand_deg;
		this.rand_sep = rand_sep;
		this.end_ptr = end_ptr;
	}
	public random_data(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends random_data implements Structure.ByReference {
		
	};
	public static class ByValue extends random_data implements Structure.ByValue {
		
	};
}
