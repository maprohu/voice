package voice.linux.common.c;

/**
 * Created by pappmar on 14/12/2016.
 */
public class CommonCTools {

    public static long _IOR(char who, int nr, int size) {
        return _IOC(2L, who, nr, size);
    }

    public static long _IOW(char who, int nr, int size) {
        return _IOC(1L, who, nr, size);
    }

    public static long _IOC(long dir, int type, int nr, int size) {
        return (dir << 30) |
                (type << 8) |
                nr |
                (size << 16);
    }
}
