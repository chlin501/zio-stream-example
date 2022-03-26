package examples 

import zio._
import zio.stream._

import scala.util.Random

object App {

  // UIO[Queue[T]] is equivalent to makeQueue, 
  // which always create a new Queue instead of 
  // using the same instance. So the programme 
  // will hang because no data is pushed into 
  // the queue that is pulled by the stream.
  // 
  // That means the queue used by stream is 
  // different from the queue used by producer.
  def producer(queue: Queue[Int]) =
    for {
      p <- queue.offer(Random.nextInt(10) + 1).delay(100.milliseconds).forever.fork
    } yield p

  def main(args: Array[String]): Unit = {
    val queue = Queue.bounded[Int](3)

    val tuple = for {
      q <- queue
      p <- producer(q)
      s <- UIO(ZStream.fromQueue(q))
    } yield (s, p)

    val program = for {
      t <- tuple
      _ <- t._1.take(10).tap(Console.putStrLn(_)).runDrain
      _ <- t._2.interrupt
    } yield ()

    val runtime = Runtime.default
    runtime.unsafeRunSync(program)

  }

}
