package br.com.news.data.model

import br.com.mindbet.common.extension.createUUID
import com.google.gson.annotations.SerializedName

class News(
    @SerializedName("id")
    val id: String = createUUID(false),
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?
){
    val tag: String?
    get() = title+description+publishedAt

    val contentFormat:String?
    get() = description + "\n\n\n" + content?.split("[+")?.firstOrNull() ?: content
}