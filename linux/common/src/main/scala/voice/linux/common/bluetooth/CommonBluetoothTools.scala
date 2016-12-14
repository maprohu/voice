package voice.linux.common.bluetooth

/**
  * Created by pappmar on 14/12/2016.
  */
object CommonBluetoothTools {
  import voice.common.linux.c.CommonCTools._

  //  #define RFCOMMCREATEDEV		_IOW('R', 200, int)
  val RFCOMMCREATEDEV = _IOW('R', 200, Integer.BYTES)
  //  #define RFCOMMRELEASEDEV	_IOW('R', 201, int)
  val RFCOMMRELEASEDEV =_IOW('R', 201, Integer.BYTES)
  //  #define RFCOMMGETDEVLIST	_IOR('R', 210, int)
  val RFCOMMGETDEVLIST = _IOR('R', 210, Integer.BYTES)
  //  #define RFCOMMGETDEVINFO	_IOR('R', 211, int)
  val RFCOMMGETDEVINFO = _IOR('R', 211, Integer.BYTES)

}
