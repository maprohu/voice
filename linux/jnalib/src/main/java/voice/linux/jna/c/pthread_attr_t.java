package voice.linux.jna.c;
import com.ochafik.lang.jnaerator.runtime.Union;
import com.sun.jna.Pointer;
/**
 * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\bits\pthreadtypes.h:19</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class pthread_attr_t extends Union<pthread_attr_t, pthread_attr_t.ByValue, pthread_attr_t.ByReference > {
	/** C type : char[36] */
	public byte[] __size = new byte[36];
	public long __align;
	public pthread_attr_t() {
		super();
	}
	/** @param __size C type : char[36] */
	public pthread_attr_t(byte __size[]) {
		super();
		if ((__size.length != this.__size.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.__size = __size;
		setType(byte[].class);
	}
	public pthread_attr_t(long __align) {
		super();
		this.__align = __align;
		setType(Long.TYPE);
	}
	public pthread_attr_t(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected pthread_attr_t newInstance() { return new pthread_attr_t(); }
	public static pthread_attr_t[] newArray(int arrayLength) {
		return Union.newArray(pthread_attr_t.class, arrayLength);
	}
	public static class ByReference extends pthread_attr_t implements com.ochafik.lang.jnaerator.runtime.Structure.ByReference {
		
	};
	public static class ByValue extends pthread_attr_t implements com.ochafik.lang.jnaerator.runtime.Structure.ByValue {
		
	};
}