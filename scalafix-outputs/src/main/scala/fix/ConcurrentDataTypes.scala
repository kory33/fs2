package fix

import cats.effect._
import fs2.async._
import fs2.async.Ref
import fs2.async.refOf
import cats.implicits._
import fs2._
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

abstract class ConcurrentDataTypes[F[_]: Effect] {
  // Ref
  val ref: F[cats.effect.concurrent.Ref[F, Int]] = cats.effect.concurrent.Ref.of(1)
  cats.effect.concurrent.Ref.of[F, Int](1)
  cats.effect.concurrent.Ref.of(1)
  ref.map(_.set(1))
  ref.map(_.setAsync(1))
  val a = ref.flatMap(_.update(_ + 1))
  val b = ref.flatMap(_.modify(i => (i, "a")))
  val c = ref.flatMap(_.tryUpdate(_ + 1))
  val d = ref.flatMap(_.tryModify(i => (i, "a")))

  // Deferred
  val e: F[cats.effect.concurrent.Deferred[F, Int]] = cats.effect.concurrent.Deferred[F, Int]
  val e2: F[cats.effect.concurrent.Deferred[F, Int]] = cats.effect.concurrent.Deferred
  val f: F[cats.effect.concurrent.Deferred[F, Int]] = promise[F, Int]
  val f2: F[cats.effect.concurrent.Deferred[F, Int]] = promise
  e.map(_.get)
  def scheduler: Timer[F]
  e.map(_.timeout(1.second))
}