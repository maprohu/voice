package voice.linux.jna.c;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.ochafik.lang.jnaerator.runtime.Union;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
import voice.linux.jna.c.CLibrary.iface;
/**
 * The ifaddr structure contains information about one address of an<br>
 * interface.  They are maintained by the different address families,<br>
 * are allocated and attached when an address is set, and are linked<br>
 * together so all addresses for an interface can be located.<br>
 * <i>native declaration : net\if.h:86</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class ifaddr extends Structure<ifaddr, ifaddr.ByValue, ifaddr.ByReference > {
	/**
	 * Address of interface.<br>
	 * C type : sockaddr
	 */
	public sockaddr ifa_addr;
	/** C type : ifa_ifu_union */
	public ifa_ifu_union ifa_ifu;
	/**
	 * Back-pointer to interface.<br>
	 * C type : iface*
	 */
	public iface ifa_ifp;
	/**
	 * Next address for interface.<br>
	 * C type : ifaddr*
	 */
	public ifaddr.ByReference ifa_next;
	/** <i>native declaration : net\if.h:89</i> */
	public static class ifa_ifu_union extends Union<ifa_ifu_union, ifa_ifu_union.ByValue, ifa_ifu_union.ByReference > {
		/** C type : sockaddr */
		public sockaddr ifu_broadaddr;
		/** C type : sockaddr */
		public sockaddr ifu_dstaddr;
		public ifa_ifu_union() {
			super();
		}
		/** @param ifu_broadaddr_or_ifu_dstaddr C type : sockaddr, or C type : sockaddr */
		public ifa_ifu_union(sockaddr ifu_broadaddr_or_ifu_dstaddr) {
			super();
			this.ifu_dstaddr = this.ifu_broadaddr = ifu_broadaddr_or_ifu_dstaddr;
			setType(sockaddr.class);
		}
		public ifa_ifu_union(Pointer peer) {
			super(peer);
		}
		protected ByReference newByReference() { return new ByReference(); }
		protected ByValue newByValue() { return new ByValue(); }
		protected ifa_ifu_union newInstance() { return new ifa_ifu_union(); }
		public static ifa_ifu_union[] newArray(int arrayLength) {
			return Union.newArray(ifa_ifu_union.class, arrayLength);
		}
		public static class ByReference extends ifa_ifu_union implements Structure.ByReference {
			
		};
		public static class ByValue extends ifa_ifu_union implements Structure.ByValue {
			
		};
	};
	public ifaddr() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("ifa_addr", "ifa_ifu", "ifa_ifp", "ifa_next");
	}
	/**
	 * @param ifa_addr Address of interface.<br>
	 * C type : sockaddr<br>
	 * @param ifa_ifu C type : ifa_ifu_union<br>
	 * @param ifa_ifp Back-pointer to interface.<br>
	 * C type : iface*<br>
	 * @param ifa_next Next address for interface.<br>
	 * C type : ifaddr*
	 */
	public ifaddr(sockaddr ifa_addr, ifa_ifu_union ifa_ifu, iface ifa_ifp, ifaddr.ByReference ifa_next) {
		super();
		this.ifa_addr = ifa_addr;
		this.ifa_ifu = ifa_ifu;
		this.ifa_ifp = ifa_ifp;
		this.ifa_next = ifa_next;
	}
	public ifaddr(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected ifaddr newInstance() { return new ifaddr(); }
	public static ifaddr[] newArray(int arrayLength) {
		return Structure.newArray(ifaddr.class, arrayLength);
	}
	public static class ByReference extends ifaddr implements Structure.ByReference {
		
	};
	public static class ByValue extends ifaddr implements Structure.ByValue {
		
	};
}
