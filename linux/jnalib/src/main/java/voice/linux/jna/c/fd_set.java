package voice.linux.jna.c;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\sys\select.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public abstract class fd_set extends Structure<fd_set, fd_set.ByValue, fd_set.ByReference > {
	/** Conversion Error : sizeof(__fd_mask) */
	public fd_set() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList();
	}
	public fd_set(Pointer peer) {
		super(peer);
	}
	public static abstract class ByReference extends fd_set implements Structure.ByReference {
		
	};
	public static abstract class ByValue extends fd_set implements Structure.ByValue {
		
	};
}
