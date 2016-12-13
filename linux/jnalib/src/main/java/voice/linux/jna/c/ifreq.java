package voice.linux.jna.c;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Union;
import java.util.Arrays;
import java.util.List;
/**
 * Interface request structure used for socket ioctl's.  All interface<br>
 * ioctl's must have parameter definitions which begin with ifr_name.<br>
 * The remainder may be interface specific.<br>
 * <i>native declaration : net\if.h:124</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class ifreq extends Structure {
	/** C type : ifr_ifrn_union */
	public ifr_ifrn_union ifr_ifrn;
	/** C type : ifr_ifru_union */
	public ifr_ifru_union ifr_ifru;
	/** <i>native declaration : net\if.h:128</i> */
	public static class ifr_ifrn_union extends Union {
		/**
		 * Interface name, e.g. "en0".<br>
		 * C type : char[16]
		 */
		public byte[] ifrn_name = new byte[16];
		public ifr_ifrn_union() {
			super();
		}
		/**
		 * @param ifrn_name Interface name, e.g. "en0".<br>
		 * C type : char[16]
		 */
		public ifr_ifrn_union(byte ifrn_name[]) {
			super();
			if ((ifrn_name.length != this.ifrn_name.length)) 
				throw new IllegalArgumentException("Wrong array size !");
			this.ifrn_name = ifrn_name;
			setType(byte[].class);
		}
		public ifr_ifrn_union(Pointer peer) {
			super(peer);
		}
		public static class ByReference extends ifr_ifrn_union implements Structure.ByReference {
			
		};
		public static class ByValue extends ifr_ifrn_union implements Structure.ByValue {
			
		};
	};
	/** <i>native declaration : net\if.h:133</i> */
	public static class ifr_ifru_union extends Union {
		/** C type : sockaddr */
		public sockaddr ifru_addr;
		/** C type : sockaddr */
		public sockaddr ifru_dstaddr;
		/** C type : sockaddr */
		public sockaddr ifru_broadaddr;
		/** C type : sockaddr */
		public sockaddr ifru_netmask;
		/** C type : sockaddr */
		public sockaddr ifru_hwaddr;
		public short ifru_flags;
		public int ifru_ivalue;
		public int ifru_mtu;
		/** C type : ifmap */
		public ifmap ifru_map;
		/**
		 * Just fits the size<br>
		 * C type : char[16]
		 */
		public byte[] ifru_slave = new byte[16];
		/**
		 * char ifru_newname[IFNAMSIZ];<br>
		 * C type : __caddr_t
		 */
		public Pointer ifru_data;
		public ifr_ifru_union() {
			super();
		}
		/** @param ifru_addr_or_ifru_dstaddr_or_ifru_broadaddr_or_ifru_netmask_or_ifru_hwaddr C type : sockaddr, or C type : sockaddr, or C type : sockaddr, or C type : sockaddr, or C type : sockaddr */
		public ifr_ifru_union(sockaddr ifru_addr_or_ifru_dstaddr_or_ifru_broadaddr_or_ifru_netmask_or_ifru_hwaddr) {
			super();
			this.ifru_hwaddr = this.ifru_netmask = this.ifru_broadaddr = this.ifru_dstaddr = this.ifru_addr = ifru_addr_or_ifru_dstaddr_or_ifru_broadaddr_or_ifru_netmask_or_ifru_hwaddr;
			setType(sockaddr.class);
		}
		public ifr_ifru_union(short ifru_flags) {
			super();
			this.ifru_flags = ifru_flags;
			setType(Short.TYPE);
		}
		public ifr_ifru_union(int ifru_ivalue_or_ifru_mtu) {
			super();
			this.ifru_mtu = this.ifru_ivalue = ifru_ivalue_or_ifru_mtu;
			setType(Integer.TYPE);
		}
		/** @param ifru_map C type : ifmap */
		public ifr_ifru_union(ifmap ifru_map) {
			super();
			this.ifru_map = ifru_map;
			setType(ifmap.class);
		}
		/**
		 * @param ifru_slave Just fits the size<br>
		 * C type : char[16]
		 */
		public ifr_ifru_union(byte ifru_slave[]) {
			super();
			if ((ifru_slave.length != this.ifru_slave.length)) 
				throw new IllegalArgumentException("Wrong array size !");
			this.ifru_slave = ifru_slave;
			setType(byte[].class);
		}
		/**
		 * @param ifru_data char ifru_newname[IFNAMSIZ];<br>
		 * C type : __caddr_t
		 */
		public ifr_ifru_union(Pointer ifru_data) {
			super();
			this.ifru_data = ifru_data;
			setType(Pointer.class);
		}
		public static class ByReference extends ifr_ifru_union implements Structure.ByReference {
			
		};
		public static class ByValue extends ifr_ifru_union implements Structure.ByValue {
			
		};
	};
	public ifreq() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("ifr_ifrn", "ifr_ifru");
	}
	/**
	 * @param ifr_ifrn C type : ifr_ifrn_union<br>
	 * @param ifr_ifru C type : ifr_ifru_union
	 */
	public ifreq(ifr_ifrn_union ifr_ifrn, ifr_ifru_union ifr_ifru) {
		super();
		this.ifr_ifrn = ifr_ifrn;
		this.ifr_ifru = ifr_ifru;
	}
	public ifreq(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends ifreq implements Structure.ByReference {
		
	};
	public static class ByValue extends ifreq implements Structure.ByValue {
		
	};
}
