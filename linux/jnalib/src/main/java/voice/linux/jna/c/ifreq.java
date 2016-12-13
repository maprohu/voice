package voice.linux.jna.c;
import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Union;
/**
 * Interface request structure used for socket ioctl's.  All interface<br>
 * ioctl's must have parameter definitions which begin with ifr_name.<br>
 * The remainder may be interface specific.<br>
 * <i>native declaration : net\if.h:124</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("c") 
public class ifreq extends StructObject {
	static {
		BridJ.register();
	}
	/** C type : ifr_ifrn_union */
	@Field(0) 
	public ifreq.ifr_ifrn_union ifr_ifrn() {
		return this.io.getNativeObjectField(this, 0);
	}
	/** C type : ifr_ifrn_union */
	@Field(0) 
	public ifreq ifr_ifrn(ifreq.ifr_ifrn_union ifr_ifrn) {
		this.io.setNativeObjectField(this, 0, ifr_ifrn);
		return this;
	}
	/** C type : ifr_ifru_union */
	@Field(1) 
	public ifreq.ifr_ifru_union ifr_ifru() {
		return this.io.getNativeObjectField(this, 1);
	}
	/** C type : ifr_ifru_union */
	@Field(1) 
	public ifreq ifr_ifru(ifreq.ifr_ifru_union ifr_ifru) {
		this.io.setNativeObjectField(this, 1, ifr_ifru);
		return this;
	}
	/** <i>native declaration : net\if.h:128</i> */
	@Union 
	public static class ifr_ifrn_union extends StructObject {
		static {
			BridJ.register();
		}
		/**
		 * Interface name, e.g. "en0".<br>
		 * C type : char[16]
		 */
		@Array({16}) 
		@Field(0) 
		public Pointer<Byte > ifrn_name() {
			return this.io.getPointerField(this, 0);
		}
		public ifr_ifrn_union() {
			super();
		}
		public ifr_ifrn_union(Pointer pointer) {
			super(pointer);
		}
	};
	/** <i>native declaration : net\if.h:133</i> */
	@Union 
	public static abstract class ifr_ifru_union extends StructObject {
		static {
			BridJ.register();
		}
		/** Conversion Error : sockaddr (Unsupported type) */
		/** Conversion Error : sockaddr (Unsupported type) */
		/** Conversion Error : sockaddr (Unsupported type) */
		/** Conversion Error : sockaddr (Unsupported type) */
		/** Conversion Error : sockaddr (Unsupported type) */
		@Field(5) 
		public short ifru_flags() {
			return this.io.getShortField(this, 5);
		}
		@Field(5) 
		public ifr_ifru_union ifru_flags(short ifru_flags) {
			this.io.setShortField(this, 5, ifru_flags);
			return this;
		}
		@Field(6) 
		public int ifru_ivalue() {
			return this.io.getIntField(this, 6);
		}
		@Field(6) 
		public ifr_ifru_union ifru_ivalue(int ifru_ivalue) {
			this.io.setIntField(this, 6, ifru_ivalue);
			return this;
		}
		@Field(7) 
		public int ifru_mtu() {
			return this.io.getIntField(this, 7);
		}
		@Field(7) 
		public ifr_ifru_union ifru_mtu(int ifru_mtu) {
			this.io.setIntField(this, 7, ifru_mtu);
			return this;
		}
		/** C type : ifmap */
		@Field(8) 
		public ifmap ifru_map() {
			return this.io.getNativeObjectField(this, 8);
		}
		/** C type : ifmap */
		@Field(8) 
		public ifr_ifru_union ifru_map(ifmap ifru_map) {
			this.io.setNativeObjectField(this, 8, ifru_map);
			return this;
		}
		/**
		 * Just fits the size<br>
		 * C type : char[16]
		 */
		@Array({16}) 
		@Field(9) 
		public Pointer<Byte > ifru_slave() {
			return this.io.getPointerField(this, 9);
		}
		/** C type : char[16] */
		@Array({16}) 
		@Field(10) 
		public Pointer<Byte > ifru_newname() {
			return this.io.getPointerField(this, 10);
		}
		/** C type : __caddr_t */
		@Field(11) 
		public Pointer<Byte > ifru_data() {
			return this.io.getPointerField(this, 11);
		}
		/** C type : __caddr_t */
		@Field(11) 
		public ifr_ifru_union ifru_data(Pointer<Byte > ifru_data) {
			this.io.setPointerField(this, 11, ifru_data);
			return this;
		}
	};
	public ifreq() {
		super();
	}
	public ifreq(Pointer pointer) {
		super(pointer);
	}
}
