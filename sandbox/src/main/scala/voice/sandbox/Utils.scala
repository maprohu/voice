package voice.sandbox

import scala.util.Random

/**
  * Created by pappmar on 05/09/2016.
  */
object Utils {

  def randomElement[T](items: Seq[T]) : T =
    items(Random.nextInt(items.size))

  def randomSequence[T](items: Seq[T]) : Iterator[T] =
    Iterator.continually(randomElement(items))

  def roundRobin[T](
    iterators: Iterator[T]*
  ) : Iterator[Seq[Iterator[T]]] =
    Iterator.iterate(iterators)(its => its.tail :+ its.head)



}
