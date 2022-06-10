package com.kukeduo.module_common.base.utils

import android.content.Context
import android.content.SharedPreferences
import com.kukeduo.module_common.base.application.App
import kotlin.reflect.KProperty

class Preference<T>(val name:String, val default:T) {

    companion object{
        private val prefs: SharedPreferences by lazy {
            App.context.getSharedPreferences("wan_android", Context.MODE_PRIVATE)
        }

        fun clearSp(){
            prefs.edit().clear().apply()
        }

        fun clearSp(key: String){
            prefs.edit().remove(key).apply()
        }

        fun contains(key: String):Boolean{
            return prefs.contains(key)
        }

        fun getAll():Map<String, *>{
            return prefs.all
        }
    }

//    operator fun getValue(thisRef: Any?, property: KProperty<*>):T{
//        return
//    }
//
//    private fun getSharePreference(name: String, default: T):T = with(prefs){
//        val res: Any = when(default){
//            is Long -> getLong(name, default)
//            is String -> getString(name, default)
//            is Int -> getInt(name, default)
//            is Boolean -> getBoolean(name,default)
//            is Float -> getFloat(name,default)
//            else->deSe
//        }
//    }
}