package voice.linux.common.bluetooth

/**
  * Created by pappmar on 14/12/2016.
  */
object CommonBluetoothTools {
  import voice.linux.common.c.CommonCTools._

  //  #define RFCOMMCREATEDEV		_IOW('R', 200, int)
  val RFCOMMCREATEDEV = _IOW('R', 200, Integer.BYTES)
  //  #define RFCOMMRELEASEDEV	_IOW('R', 201, int)
  val RFCOMMRELEASEDEV =_IOW('R', 201, Integer.BYTES)
  //  #define RFCOMMGETDEVLIST	_IOR('R', 210, int)
  val RFCOMMGETDEVLIST = _IOR('R', 210, Integer.BYTES)
  //  #define RFCOMMGETDEVINFO	_IOR('R', 211, int)
  val RFCOMMGETDEVINFO = _IOR('R', 211, Integer.BYTES)

  require(RFCOMMCREATEDEV == 1074025160)

  /*
  gcc bt.c -I/usr/include/bluetooth

  #include <sys/ioctl.h>
  #include <bluetooth.h>
  #include <rfcomm.h>
  main() {
    printf("%d", RFCOMMCREATEDEV);
  }
   */

}
