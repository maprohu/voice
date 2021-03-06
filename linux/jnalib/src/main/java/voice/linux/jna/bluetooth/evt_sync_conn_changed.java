package voice.linux.jna.bluetooth;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : bluetooth\hci.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class evt_sync_conn_changed extends Structure {
	public byte status;
	public short handle;
	public byte trans_interval;
	public byte retrans_window;
	public short rx_pkt_len;
	public short tx_pkt_len;
	public evt_sync_conn_changed() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("status", "handle", "trans_interval", "retrans_window", "rx_pkt_len", "tx_pkt_len");
	}
	public evt_sync_conn_changed(byte status, short handle, byte trans_interval, byte retrans_window, short rx_pkt_len, short tx_pkt_len) {
		super();
		this.status = status;
		this.handle = handle;
		this.trans_interval = trans_interval;
		this.retrans_window = retrans_window;
		this.rx_pkt_len = rx_pkt_len;
		this.tx_pkt_len = tx_pkt_len;
	}
	public evt_sync_conn_changed(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends evt_sync_conn_changed implements Structure.ByReference {
		
	};
	public static class ByValue extends evt_sync_conn_changed implements Structure.ByValue {
		
	};
}
