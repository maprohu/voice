package voice.linux.jna.bluetooth;
import com.ochafik.lang.jnaerator.runtime.Structure;
import com.sun.jna.Pointer;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class setup_sync_conn_cp extends Structure<setup_sync_conn_cp, setup_sync_conn_cp.ByValue, setup_sync_conn_cp.ByReference > {
	public short handle;
	public int tx_bandwith;
	public int rx_bandwith;
	public short max_latency;
	public short voice_setting;
	public byte retrans_effort;
	public short pkt_type;
	public setup_sync_conn_cp() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("handle", "tx_bandwith", "rx_bandwith", "max_latency", "voice_setting", "retrans_effort", "pkt_type");
	}
	public setup_sync_conn_cp(short handle, int tx_bandwith, int rx_bandwith, short max_latency, short voice_setting, byte retrans_effort, short pkt_type) {
		super();
		this.handle = handle;
		this.tx_bandwith = tx_bandwith;
		this.rx_bandwith = rx_bandwith;
		this.max_latency = max_latency;
		this.voice_setting = voice_setting;
		this.retrans_effort = retrans_effort;
		this.pkt_type = pkt_type;
	}
	public setup_sync_conn_cp(Pointer peer) {
		super(peer);
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected setup_sync_conn_cp newInstance() { return new setup_sync_conn_cp(); }
	public static setup_sync_conn_cp[] newArray(int arrayLength) {
		return Structure.newArray(setup_sync_conn_cp.class, arrayLength);
	}
	public static class ByReference extends setup_sync_conn_cp implements Structure.ByReference {
		
	};
	public static class ByValue extends setup_sync_conn_cp implements Structure.ByValue {
		
	};
}
