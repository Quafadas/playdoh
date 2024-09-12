package notvecxt

import vecxt.* 
import narr.NArray

@main def test2 =     
    val a = StrictMatrix[3,3](NArray[Double](1,2,3,4,5,6,7,8,9))
    println(a.elementAt((1,0)))
    
    val t: Tensor = Tensor(NArray[Double](1,2,3,4,5, 6), (6,1))
    println(t.elementAt((5,0)))

    val b = Matrix(NArray[Double](1,2,3), (3, 1))    
    println(b.elementAt((2,0)))
