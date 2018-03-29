package cn.junechiu.interview.kotlin

/**
 * Created by android on 2017/12/20.
 */

class PersionHandle {

    var persion: Persion? = null;

    fun <T> use(block: Persion.() -> T): T {
        if (persion == null) {
            persion = Persion("june", 12)
        }
        return persion!!.block()
    }

    fun test() {
        use {
            say("ssss")
            var p = Persion("jeee", 89)
            p.eat()
        }
    }
}
