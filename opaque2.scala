//> using dep ai.dragonfly::narr:0.105

import narr.NArray

type TupleOfInts[T <: Tuple] <: Boolean = T match
  case EmptyTuple => true    // Base case: Empty tuple is valid
  case Int *: tail => TupleOfInts[tail]  // Recursive case: Head is Int, check the tail
  case _ => false  // If any element is not an Int, return false

opaque type Tensor = (NArray[Double], Tuple)

object Tensor:
    def apply[T <: Tuple](a: NArray[Double], b: T)(using ev: TupleOfInts[T] =:= true): Tensor = (a, b)

opaque type Vector = (NArray[Double], Tuple1[Int]) & Tensor

object Vector:    
    def apply(a: NArray[Double]): Vector = (a, Tuple1(a.size) )

opaque type Matrix = (NArray[Double], Tuple2[Int, Int]) & Tensor

object Matrix:
    // def apply(a: NArray[Double], b: NArray[Int]): Matrix = (a, b)
    def apply[T <: Tuple2[Int, Int]](a: NArray[Double], b: T)(using ev: TupleOfInts[T] =:= true): Matrix = (a, b)


opaque type StrictMatrix[M <: Int, N <: Int] = (NArray[Double], Tuple2[M,N]) & Matrix

object StrictMatrix:    
    def apply[M <: Int, N <: Int](a: NArray[Double])(using ev: TupleOfInts[Tuple2[M, N]] =:= true,  m: ValueOf[M], n: ValueOf[N] ): StrictMatrix[M,N] = 
        val tup = (m.value,n.value)
        (a, tup)

extension (t: Tensor)
    def raw = t._1

    
@main def test2 =     
    
    val t: Tensor = Tensor(NArray[Double](1,2,3,4,5, 6), (3,1))
    println(t.raw.mkString(","))

    val b: Matrix = Matrix(NArray[Double](1,2,3), (3, 1))
    println(b.raw.mkString(","))

    val a = StrictMatrix[3,3](NArray[Double](1,2,3))
    println(a.raw.mkString(","))

    
    

