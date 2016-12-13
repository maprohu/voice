package voice.linux.jna.c;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\bits\pthreadtypes.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class __pthread_internal_slist extends Structure {
	/** C type : __pthread_internal_slist* */
	public __pthread_internal_slist.ByReference __next;
	public __pthread_internal_slist() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("__next");
	}
	/** @param __next C type : __pthread_internal_slist* */
	public __pthread_internal_slist(__pthread_internal_slist.ByReference __next) {
		super();
		this.__next = __next;
	}
	public __pthread_internal_slist(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends __pthread_internal_slist implements Structure.ByReference {
		
	};
	public static class ByValue extends __pthread_internal_slist implements Structure.ByValue {
		
	};
}
