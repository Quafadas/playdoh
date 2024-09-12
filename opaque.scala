

// type AllInts[T <: Tuple] <: Boolean = T match
//   case EmptyTuple => true    // Base case: Empty tuple is valid
//   case Int *: tail => AllInts[tail]  // Recursive case: Head is Int, check the tail
//   case _ => false  // If any element is not an Int, return false

// opaque type Array2 = Array[2]

// opaque type Tensor = (Array[Double], Array[Int])

// object Tensor:
//     def apply(a: Array[Double], b: Array[Int]): Tensor = (a, b)

// opaque type Matrix = (Array[Double], Array[Int]) & Tensor

// object Matrix:
//     def apply(a: Array[Double], b: Array[Int]): Matrix = (a, b)
//     def apply(a: Array[Double], b: Tuple2[Int, Int]): Matrix = (a, b.toArray.asInstanceOf[Array[Int]])


// opaque type StrictMatrix[M <: Int, N <: Int] = (Array[Double], Tuple2[M,N])

// object StrictMatrix:    
//     def apply[M <: Int, N <: Int](a: Array[Double], b: Tuple2[M, N]): StrictMatrix[M,N] = (a, b)

// extension [T](t: Tensor)
//     def raw = t._1

    
// @main def test =     
    
//     val t: Tensor = Tensor(Array[Double](1,2,3,4,5, 6), Array(3, 2))
//     val b: Matrix = Matrix(Array[Double](1,2,3), (3, 1))

//     val a: StrictMatrix[3, 1] = StrictMatrix[3,1](Array[Double](1,2,3), (3, 1))
    
//     println(t.raw.mkString(","))
//     println(a.raw.mkString(","))
//     println(b.raw.mkString(","))

