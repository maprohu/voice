package voice.linux.jna.c;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Union;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\bits\pthreadtypes.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class pthread_rwlock_t extends Union {
	/** C type : __data_struct */
	public __data_struct __data;
	/** C type : char[32] */
	public byte[] __size = new byte[32];
	public long __align;
	/** <i>native declaration : ..\voice\local\headers\\usr\include\arm-linux-gnueabihf\bits\pthreadtypes.h:105</i> */
	public static class __data_struct extends Structure {
		public int __lock;
		public int __nr_readers;
		public int __readers_wakeup;
		public int __writer_wakeup;
		public int __nr_readers_queued;
		public int __nr_writers_queued;
		public byte __flags;
		public byte __shared;
		public byte __pad1;
		public byte __pad2;
		public int __writer;
		public __data_struct() {
			super();
		}
		protected List<? > getFieldOrder() {
			return Arrays.asList("__lock", "__nr_readers", "__readers_wakeup", "__writer_wakeup", "__nr_readers_queued", "__nr_writers_queued", "__flags", "__shared", "__pad1", "__pad2", "__writer");
		}
		public __data_struct(Pointer peer) {
			super(peer);
		}
		public static class ByReference extends __data_struct implements Structure.ByReference {
			
		};
		public static class ByValue extends __data_struct implements Structure.ByValue {
			
		};
	};
	public pthread_rwlock_t() {
		super();
	}
	/** @param __data C type : __data_struct */
	public pthread_rwlock_t(__data_struct __data) {
		super();
		this.__data = __data;
		setType(__data_struct.class);
	}
	/** @param __size C type : char[32] */
	public pthread_rwlock_t(byte __size[]) {
		super();
		if ((__size.length != this.__size.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.__size = __size;
		setType(byte[].class);
	}
	public pthread_rwlock_t(long __align) {
		super();
		this.__align = __align;
		setType(Long.TYPE);
	}
	public pthread_rwlock_t(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends pthread_rwlock_t implements Structure.ByReference {
		
	};
	public static class ByValue extends pthread_rwlock_t implements Structure.ByValue {
		
	};
}
