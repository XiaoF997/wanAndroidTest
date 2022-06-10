package com.kukeduo.kotlindemo.net

import android.util.Log
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.wanandroid.data.bean.Article
import com.kukeduo.kotlindemo.bean.User
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.kukeduo.kotlindemo.bean.BannerBean
import com.kukeduo.kotlindemo.bean.IdiomsItem
import com.kukeduo.kotlindemo.bean.ProjectTitle
import com.kukeduo.module_common.base.bean.ApiResponse
import okhttp3.*
import okhttp3.Headers
import okhttp3.internal.http.promisesBody
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset

object HttpManager {
    val retrofit: Retrofit = Retrofit.Builder()
        .client(OkHttpClient.Builder().addInterceptor {
            val request = it.request().newBuilder()
                .build()
            val requestUrl = request.url.toString()
            val methodStr = request.method
            val body = request.body
            Log.e("HTTP-Interceptor", requestUrl)
            Log.e("HTTP-Interceptor", methodStr)
            Log.e("HTTP-Interceptor", Gson().toJson(body))

            it.proceed(request)
        }.addInterceptor(LogginInterceptor()).build())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl("https://www.wanandroid.com/")
        .build()
}

class LogginInterceptor: Interceptor{
    //一行字符最大数量
    private val MAX_LENGTH = 1024

    private val UTF8 = Charset.forName("UTF-8")

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response: Response
        response = try {
            chain.proceed(request)
        } catch (e: IOException) {
            print(String.format("┣━━━ [HTTP FAILED] url:%s exception:%s", request.url, e.message))
            throw e
        }
        getResponseBody(request, response)?.let { printLong(it) }

        return response
    }


    /**
     * 打印内容过长文本，超过一行长度，折行显示
     *
     * @param text 要打印的文本
     */
    private fun printLong(text: String) {
        val length = text.length
        if (length <= MAX_LENGTH) {
            print(text)
        } else {
            var lineNum = length / MAX_LENGTH
            if (length % MAX_LENGTH != 0) {
                lineNum++
            }
            for (i in 1..lineNum) {
                if (i < lineNum) {
                    print(
                        text.substring(
                            (i - 1) * MAX_LENGTH,
                            i * MAX_LENGTH
                        )
                    )
                } else {
                    print(text.substring((i - 1) * MAX_LENGTH, length))
                }
            }
        }
    }


    /**
     * 返回响应体字符串
     *
     * @param request  [Request]
     * @param response [Response]
     * @return 响应体字符串
     */
    @Throws(IOException::class)
    private fun getResponseBody(request: Request, response: Response): String? {
        var body = "[No Response Body]"
        if (response.promisesBody()) {
            val responseBody = response.body
            val source = responseBody!!.source()
            source.request(Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            if (isEncoded(request.headers)) {
                body = "[Body: Encoded]"
            } else if (!isPlaintext(buffer)) {
                val url = request.url.toString()
                body = if (!url.contains("?")) {
                    String.format("[File:%s]", url.substring(url.lastIndexOf("/") + 1))
                } else {
                    "[Body: Not readable]"
                }
            } else {
                var charset: Charset = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    charset = contentType.charset(UTF8)!!
                }
                body = buffer.clone().readString(charset)
            }
        }
        return body
    }


    private fun isPlaintext(buffer: Buffer): Boolean {
        return try {
            val prefix = Buffer()
            val byteCount = if (buffer.size < 64) buffer.size else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            true
        } catch (e: EOFException) {
            false // Truncated UTF-8 sequence.
        }
    }
    /**
     * 判断 Headers 是不是编码过的
     *
     * @param headers [Headers]
     * @return `true ` 编码过的
     */
    private fun isEncoded(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

}



interface IdiomsApi {
    @GET("query")
    suspend fun query(@Query("word") wd: String, @Query("key") key: String): IdiomsItem
}

interface wanAndroidApi {
    /** 登录 */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") pwd: String
    ): ApiResponse<User>


    /** 获取首页banner数据 */
    @GET("banner/json")
    suspend fun getBanner(): ApiResponse<List<BannerBean>>

    /** 获取置顶文章集合数据 */
    @GET("article/top/json")
    suspend fun getArticleTopList(): ApiResponse<List<Article>>

    /** 获取首页文章数据 */
    @GET("article/list/{pageNo}/json")
    suspend fun getArticlePageList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): ApiResponse<PageResponse<Article>>

    /** 注册 */
    @FormUrlEncoded
    @POST("user/register")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") pwd: String,
        @Field("repassword") pwdSure: String
    ): ApiResponse<Any?>

    /** 获取项目分类数据 */
    @GET("project/tree/json")
    suspend fun getProjectTitleList(): ApiResponse<List<ProjectTitle>>
}