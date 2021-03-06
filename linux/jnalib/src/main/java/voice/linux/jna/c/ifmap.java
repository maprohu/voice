package voice.linux.jna.c;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * Device mapping structure. I'd just gone off and designed a<br>
 * beautiful scheme using only loadable modules with arguments for<br>
 * driver options and along come the PCMCIA people 8)<br>
 * Ah well. The get() side of this is good for WDSETUP, and it'll be<br>
 * handy for debugging things. The set side is fine for now and being<br>
 * very small might be worth keeping for clean configuration.<br>
 * <i>native declaration : net\if.h:109</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class ifmap extends Structure {
	public long mem_start;
	public long mem_end;
	public short base_addr;
	public byte irq;
	public byte dma;
	public byte port;
	public ifmap() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("mem_start", "mem_end", "base_addr", "irq", "dma", "port");
	}
	public ifmap(long mem_start, long mem_end, short base_addr, byte irq, byte dma, byte port) {
		super();
		this.mem_start = mem_start;
		this.mem_end = mem_end;
		this.base_addr = base_addr;
		this.irq = irq;
		this.dma = dma;
		this.port = port;
	}
	public ifmap(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends ifmap implements Structure.ByReference {
		
	};
	public static class ByValue extends ifmap implements Structure.ByValue {
		
	};
}
