package com.nikola.rxjavasample

import io.reactivex.rxjava3.core.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActivityUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testHelloWorldRx(){
        Flowable.just("Hello World").subscribe{ item->
            println(item)
        }


    }


    @Test
    fun testHelloWorldRxSingle(){
        Single.just("Hello World").subscribe{ item->
            println(item)
       }


    }
     @Test
    fun testHelloWorldRxObservable(){
        Observable.just("Hello World").subscribe{ item->
            println(item)
        }


    }
    
       @Test
    fun testHelloWorldCompletable(){
        Maybe.just("Hello World").subscribe{ item->
            println(item)
        }
        Completable.complete()
    }

}
