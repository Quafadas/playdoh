//> using dep ai.dragonfly::narr:0.105

package vecxt

import narr.NArray
import scala.annotation.targetName

export Tensors.*


object Tensors {

    type TupleOfInts[T <: Tuple] <: Boolean = T match
        case EmptyTuple => true    // Base case: Empty tuple is valid
        case Int *: tail => TupleOfInts[tail]  // Recursive case: Head is Int, check the tail
        case _ => false  // If any element is not an Int, return false

    opaque type Tensor = (NArray[Double], Tuple)
    object Tensor:
        def apply[T <: Tuple](a: NArray[Double], b: T)(using ev: TupleOfInts[T] =:= true): Tensor = (a, b)

    opaque type Vector1 = (NArray[Double], Tuple1[Int])
    type Vector = Vector1 & Tensor

    object Vector:    
        def apply(a: NArray[Double]): Vector = (a, Tuple1(a.size) )

    opaque type Matrix1 = (NArray[Double], Tuple2[Int, Int])

    type Matrix = Matrix1 & Tensor

    object Matrix:        
        def apply[T <: Tuple2[Int, Int]](a: NArray[Double], b: T)(using ev: TupleOfInts[T] =:= true): Matrix = (a, b)
        
        // extension (t: Tensor)
        //     @targetName("martixRaw")
        //     def raw: Array[Double] = t.raw

    opaque type StrictMatrix1[M <: Int, N <: Int] = (NArray[Double], Tuple2[M,N]) & Matrix

    type StrictMatrix[M <: Int, N <: Int] = StrictMatrix1[M , N] & Tensor
    object StrictMatrix:    
        def apply[M <: Int, N <: Int](a: NArray[Double])(using ev: TupleOfInts[Tuple2[M, N]] =:= true,  m: ValueOf[M], n: ValueOf[N] ): StrictMatrix[M,N] = 
            val tup = (m.value,n.value)
            (a, tup)

    extension (t: Tensor)
        def raw: Array[Double] = t._1

        /**
          * Zero indexed element retrieval
          */
        def elementAt[T <: Tuple](b: T)(using ev: TupleOfInts[T] =:= true) = 
            val indexes = b.toList.asInstanceOf[List[Int]]
            val dimensions = t._2.toList.asInstanceOf[List[Int]]

            assert(indexes.length == dimensions.length)

            val linearIndex = indexes.zip(dimensions.scanRight(1)(_ * _).tail).map {
                case (index, stride) => index * stride
            }.sum

            t._1(linearIndex)
    
}
