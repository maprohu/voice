package voice.storage


case class Device(
  name: String
)

case class Speaker(
  name: String
)

/**
  * Always assume:
  *   pcm
  *   16 bits signed integer
  *   little endian
  *   mono
  *
  * @param sampleRate
  */
@SerialVersionUID(1)
case class AudioDataFormat(
  sampleRate: Int
)


@SerialVersionUID(1)
case class RecordedSpeech(
  device: Device,
  speaker: Speaker,
  format: AudioDataFormat,
  script: Array[Syllables.Code],
  data: Array[Byte]
)

