package cn.junechiu.interview.kotlin

/**
 * Created by android on 2017/12/20.
 */

class Persion(var name: String, var age: Int) {

    fun eat() {
        print("$name" + " eating")
    }

    fun sleep() {
        print("$name" + " eating")
    }

    fun say(word: String): String {
        return "$name" + " say " + word
    }
}
