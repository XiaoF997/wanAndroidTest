package com.kukeduo.module_common.base.utils

import android.content.Context
import android.content.SharedPreferences
import com.btpj.lib_base.ext.toJson
import com.btpj.lib_base.utils.LogUtil
import com.google.gson.Gson
import com.kukeduo.module_common.base.application.App
import java.nio.charset.Charset
import kotlin.reflect.KProperty

class Preference<T>(val name: String, val default: T) {

    companion object {
        private val prefs: SharedPreferences by lazy {
            App.context.getSharedPreferences("wan_android", Context.MODE_PRIVATE)
        }

        fun clearSp() {
            prefs.edit().clear().apply()
        }

        fun clearSp(key: String) {
            prefs.edit().remove(key).apply()
        }

        fun contains(key: String): Boolean {
            return prefs.contains(key)
        }

        fun getAll(): Map<String, *> {
            return prefs.all
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getSharePreference(name, default)
    }


    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putSharePreference(name, value)
    }

    private fun putSharePreference(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> putString(name, value.toJson())
//            else -> putString(name, serialize(value))
        }.apply()

    }

    //
    private fun getSharePreference(name: String, default: T): T = with(prefs) {
        val res: Any? = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else-> getString(name,default.toJson()).let {
                val str = getString(name, default.toJson())
                fromJson(str)
            }
//            else -> getString(name, serialize(default))?.let { deSerialize(it) }

        }
        return res as T
    }


    inline fun <reified T> fromJson(str:String?) = Gson().fromJson(str, T::class.java)

//
//    /**
//     * 序列化对象
//     */
//    @Throws(IOException::class)
//    private fun <A> serialize(obj: A): String {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
//        objectOutputStream.writeObject(obj)
//        var baos = byteArrayOutputStream.toString("ISO-8859-1")
//        baos = URLEncoder.encode(baos, "UTF-8")
//        objectOutputStream.close()
//        byteArrayOutputStream.close()
//
//        return baos
//    }
//
//    /**
//     * 反序列化对象
//     */
//    @Throws(IOException::class, ClassNotFoundException::class)
//    private fun <A> deSerialize(str: String): A {
//        val deStr = URLDecoder.decode(str, "UTF-8")
//        val byteArrayInputStream = ByteArrayInputStream(deStr.toByteArray(charset("ISO-8859-1")))
//        val objectInputStream = ObjectInputStream(byteArrayInputStream)
//        val obj = objectInputStream.readObject() as A
//        objectInputStream.close()
//        byteArrayInputStream.close()
//        return obj
//    }
}