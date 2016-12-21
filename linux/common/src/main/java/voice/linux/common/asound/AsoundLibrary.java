package voice.linux.common.asound;

import com.ochafik.lang.jnaerator.runtime.NativeSize;
import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.NativeLongByReference;
import com.sun.jna.ptr.PointerByReference;

import java.nio.Buffer;

/**
 * Created by pappmar on 20/12/2016.
 */
public interface AsoundLibrary extends Library {
    public static final String JNA_LIBRARY_NAME = "asound";
    public static final NativeLibrary JNA_NATIVE_LIB = NativeLibrary.getInstance(AsoundLibrary.JNA_LIBRARY_NAME);
    public static final AsoundLibrary INSTANCE = (AsoundLibrary)Native.loadLibrary(AsoundLibrary.JNA_LIBRARY_NAME, AsoundLibrary.class);


    /**
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h</i><br>
     * enum values
     */
    public static interface snd_pcm_stream_t {
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:101</i> */
        public static final int SND_PCM_STREAM_PLAYBACK = 0;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:103</i> */
        public static final int SND_PCM_STREAM_CAPTURE = 1;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:104</i> */
        public static final int SND_PCM_STREAM_LAST = (int) AsoundLibrary.snd_pcm_stream_t.SND_PCM_STREAM_CAPTURE;
    };
    /**
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h</i><br>
     * enum values
     */
    public static interface snd_pcm_access_t {
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:110</i> */
        public static final int SND_PCM_ACCESS_MMAP_INTERLEAVED = 0;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:112</i> */
        public static final int SND_PCM_ACCESS_MMAP_NONINTERLEAVED = 1;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:114</i> */
        public static final int SND_PCM_ACCESS_MMAP_COMPLEX = 2;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:116</i> */
        public static final int SND_PCM_ACCESS_RW_INTERLEAVED = 3;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:118</i> */
        public static final int SND_PCM_ACCESS_RW_NONINTERLEAVED = 4;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:119</i> */
        public static final int SND_PCM_ACCESS_LAST = (int) AsoundLibrary.snd_pcm_access_t.SND_PCM_ACCESS_RW_NONINTERLEAVED;
    };

    /**
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h</i><br>
     * enum values
     */
    public static interface snd_pcm_format_t {
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:125</i> */
        public static final int SND_PCM_FORMAT_UNKNOWN = -1;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:127</i> */
        public static final int SND_PCM_FORMAT_S8 = 0;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:129</i> */
        public static final int SND_PCM_FORMAT_U8 = 1;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:131</i> */
        public static final int SND_PCM_FORMAT_S16_LE = 2;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:133</i> */
        public static final int SND_PCM_FORMAT_S16_BE = 3;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:135</i> */
        public static final int SND_PCM_FORMAT_U16_LE = 4;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:137</i> */
        public static final int SND_PCM_FORMAT_U16_BE = 5;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:139</i> */
        public static final int SND_PCM_FORMAT_S24_LE = 6;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:141</i> */
        public static final int SND_PCM_FORMAT_S24_BE = 7;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:143</i> */
        public static final int SND_PCM_FORMAT_U24_LE = 8;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:145</i> */
        public static final int SND_PCM_FORMAT_U24_BE = 9;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:147</i> */
        public static final int SND_PCM_FORMAT_S32_LE = 10;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:149</i> */
        public static final int SND_PCM_FORMAT_S32_BE = 11;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:151</i> */
        public static final int SND_PCM_FORMAT_U32_LE = 12;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:153</i> */
        public static final int SND_PCM_FORMAT_U32_BE = 13;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:155</i> */
        public static final int SND_PCM_FORMAT_FLOAT_LE = 14;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:157</i> */
        public static final int SND_PCM_FORMAT_FLOAT_BE = 15;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:159</i> */
        public static final int SND_PCM_FORMAT_FLOAT64_LE = 16;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:161</i> */
        public static final int SND_PCM_FORMAT_FLOAT64_BE = 17;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:163</i> */
        public static final int SND_PCM_FORMAT_IEC958_SUBFRAME_LE = 18;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:165</i> */
        public static final int SND_PCM_FORMAT_IEC958_SUBFRAME_BE = 19;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:167</i> */
        public static final int SND_PCM_FORMAT_MU_LAW = 20;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:169</i> */
        public static final int SND_PCM_FORMAT_A_LAW = 21;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:171</i> */
        public static final int SND_PCM_FORMAT_IMA_ADPCM = 22;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:173</i> */
        public static final int SND_PCM_FORMAT_MPEG = 23;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:175</i> */
        public static final int SND_PCM_FORMAT_GSM = 24;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:177</i> */
        public static final int SND_PCM_FORMAT_SPECIAL = 31;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:179</i> */
        public static final int SND_PCM_FORMAT_S24_3LE = 32;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:181</i> */
        public static final int SND_PCM_FORMAT_S24_3BE = 33;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:183</i> */
        public static final int SND_PCM_FORMAT_U24_3LE = 34;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:185</i> */
        public static final int SND_PCM_FORMAT_U24_3BE = 35;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:187</i> */
        public static final int SND_PCM_FORMAT_S20_3LE = 36;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:189</i> */
        public static final int SND_PCM_FORMAT_S20_3BE = 37;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:191</i> */
        public static final int SND_PCM_FORMAT_U20_3LE = 38;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:193</i> */
        public static final int SND_PCM_FORMAT_U20_3BE = 39;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:195</i> */
        public static final int SND_PCM_FORMAT_S18_3LE = 40;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:197</i> */
        public static final int SND_PCM_FORMAT_S18_3BE = 41;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:199</i> */
        public static final int SND_PCM_FORMAT_U18_3LE = 42;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:201</i> */
        public static final int SND_PCM_FORMAT_U18_3BE = 43;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:203</i> */
        public static final int SND_PCM_FORMAT_G723_24 = 44;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:205</i> */
        public static final int SND_PCM_FORMAT_G723_24_1B = 45;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:207</i> */
        public static final int SND_PCM_FORMAT_G723_40 = 46;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:209</i> */
        public static final int SND_PCM_FORMAT_G723_40_1B = 47;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:211</i> */
        public static final int SND_PCM_FORMAT_DSD_U8 = 48;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:213</i> */
        public static final int SND_PCM_FORMAT_DSD_U16_LE = 49;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:214</i> */
        public static final int SND_PCM_FORMAT_LAST = (int)AsoundLibrary.snd_pcm_format_t.SND_PCM_FORMAT_DSD_U16_LE;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:218</i> */
        public static final int SND_PCM_FORMAT_S16 = (int)AsoundLibrary.snd_pcm_format_t.SND_PCM_FORMAT_S16_LE;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:220</i> */
        public static final int SND_PCM_FORMAT_U16 = (int)AsoundLibrary.snd_pcm_format_t.SND_PCM_FORMAT_U16_LE;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:222</i> */
        public static final int SND_PCM_FORMAT_S24 = (int)AsoundLibrary.snd_pcm_format_t.SND_PCM_FORMAT_S24_LE;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:224</i> */
        public static final int SND_PCM_FORMAT_U24 = (int)AsoundLibrary.snd_pcm_format_t.SND_PCM_FORMAT_U24_LE;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:226</i> */
        public static final int SND_PCM_FORMAT_S32 = (int)AsoundLibrary.snd_pcm_format_t.SND_PCM_FORMAT_S32_LE;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:228</i> */
        public static final int SND_PCM_FORMAT_U32 = (int)AsoundLibrary.snd_pcm_format_t.SND_PCM_FORMAT_U32_LE;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:230</i> */
        public static final int SND_PCM_FORMAT_FLOAT = (int)AsoundLibrary.snd_pcm_format_t.SND_PCM_FORMAT_FLOAT_LE;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:232</i> */
        public static final int SND_PCM_FORMAT_FLOAT64 = (int)AsoundLibrary.snd_pcm_format_t.SND_PCM_FORMAT_FLOAT64_LE;
        /** <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:234</i> */
        public static final int SND_PCM_FORMAT_IEC958_SUBFRAME = (int)AsoundLibrary.snd_pcm_format_t.SND_PCM_FORMAT_IEC958_SUBFRAME_LE;
    };

    /**
     * Original signature : <code>int snd_pcm_open(snd_pcm_t**, const char*, snd_pcm_stream_t, int)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:437</i>
     */
    int snd_pcm_open(PointerByReference pcm, String name, int stream, int mode);

    /**
     * \defgroup PCM_HW_Params Hardware Parameters<br>
     * \ingroup PCM<br>
     * See the \ref pcm page for more details.<br>
     * \{<br>
     * Original signature : <code>int snd_pcm_hw_params_any(snd_pcm_t*, snd_pcm_hw_params_t*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:644</i>
     */
    int snd_pcm_hw_params_any(Pointer pcm, Pointer params);

    /**
     * Original signature : <code>int snd_pcm_hw_params_set_access(snd_pcm_t*, snd_pcm_hw_params_t*, snd_pcm_access_t)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:705</i>
     */
    int snd_pcm_hw_params_set_access(Pointer pcm, Pointer params, int _access);

    /**
     * Original signature : <code>int snd_pcm_hw_params_set_format(snd_pcm_t*, snd_pcm_hw_params_t*, snd_pcm_format_t)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:713</i>
     */
    int snd_pcm_hw_params_set_format(Pointer pcm, Pointer params, int val);

    /**
     * Original signature : <code>int snd_pcm_hw_params_set_channels(snd_pcm_t*, snd_pcm_hw_params_t*, unsigned int)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:731</i>
     */
    int snd_pcm_hw_params_set_channels(Pointer pcm, Pointer params, int val);

    /**
     * Original signature : <code>int snd_pcm_hw_params_set_rate_near(snd_pcm_t*, snd_pcm_hw_params_t*, unsigned int*, int*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:747</i>
     */
    int snd_pcm_hw_params_set_rate_near(Pointer pcm, Pointer params, IntByReference val, IntByReference dir);
    /**
     * Original signature : <code>int snd_pcm_hw_params(snd_pcm_t*, snd_pcm_hw_params_t*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:460</i>
     */
    int snd_pcm_hw_params(Pointer pcm, Pointer params);
    /**
     * Original signature : <code>char* snd_pcm_name(snd_pcm_t*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:447</i>
     */
    String snd_pcm_name(Pointer pcm);
    /**
     * Original signature : <code>char* snd_pcm_state_name(const snd_pcm_state_t)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:991</i>
     */
    String snd_pcm_state_name(int state);
    /**
     * Original signature : <code>snd_pcm_state_t snd_pcm_state(snd_pcm_t*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:471</i>
     */
    int snd_pcm_state(Pointer pcm);
    /**
     * Original signature : <code>int snd_pcm_hw_params_get_channels(const snd_pcm_hw_params_t*, unsigned int*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:727</i>
     */
    int snd_pcm_hw_params_get_channels(Pointer params, IntByReference val);
    /**
     * Original signature : <code>int snd_pcm_hw_params_get_rate(const snd_pcm_hw_params_t*, unsigned int*, int*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:739</i>
     */
    int snd_pcm_hw_params_get_rate(Pointer params, IntByReference val, IntByReference dir);
    /**
     * Original signature : <code>int snd_pcm_hw_params_get_period_size(const snd_pcm_hw_params_t*, snd_pcm_uframes_t*, int*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:769</i>
     */
    int snd_pcm_hw_params_get_period_size(Pointer params, NativeLongByReference frames, IntByReference dir);

    /**
     * Original signature : <code>int snd_pcm_hw_params_get_buffer_size(const snd_pcm_hw_params_t*, snd_pcm_uframes_t*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:807</i><br>
     */
    int snd_pcm_hw_params_get_buffer_size(Pointer params, NativeLongByReference val);

    /**
     * Original signature : <code>size_t snd_pcm_hw_params_sizeof()</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:691</i>
     */
    NativeSize snd_pcm_hw_params_sizeof();
    /**
     * Original signature : <code>int snd_pcm_hw_params_set_period_size(snd_pcm_t*, snd_pcm_hw_params_t*, snd_pcm_uframes_t, int)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:773</i><br>
     */
    int snd_pcm_hw_params_set_period_size(Pointer pcm, Pointer params, NativeLong val, int dir);
    /**
     * Original signature : <code>int snd_pcm_hw_params_set_buffer_size(snd_pcm_t*, snd_pcm_hw_params_t*, snd_pcm_uframes_t)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:811</i><br>
     */
    int snd_pcm_hw_params_set_buffer_size(Pointer pcm, Pointer params, NativeLong val);
    /**
     * \defgroup Error Error handling<br>
     *  Error handling macros and functions.<br>
     *  \{<br>
     * Original signature : <code>char* snd_strerror(int)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\error.h:45</i>
     */
    String snd_strerror(int errnum);

    /**
     * Original signature : <code>snd_pcm_sframes_t snd_pcm_writei(snd_pcm_t*, const void*, snd_pcm_uframes_t)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:483</i><br>
     */
    NativeLong snd_pcm_writei(Pointer pcm, Buffer buffer, NativeLong size);

    /**
     * Original signature : <code>int snd_pcm_drain(snd_pcm_t*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:469</i><br>
     */
    int snd_pcm_drain(Pointer pcm);

    /**
     * Original signature : <code>int snd_pcm_close(snd_pcm_t*)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:446</i><br>
     */
    int snd_pcm_close(Pointer pcm);
    /**
     * Original signature : <code>snd_pcm_sframes_t snd_pcm_readi(snd_pcm_t*, void*, snd_pcm_uframes_t)</code><br>
     * <i>native declaration : ..\voice\local\headers\\usr\include\alsa\pcm.h:484</i><br>
     */
    NativeLong snd_pcm_readi(Pointer pcm, Buffer buffer, NativeLong size);
}
