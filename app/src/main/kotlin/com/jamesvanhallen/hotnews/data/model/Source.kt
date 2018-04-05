package com.jamesvanhallen.hotnews.data.model

import com.google.gson.annotations.SerializedName

data class Source(@SerializedName("id") var id: String,
                  @SerializedName("description") var description: String,
                  @SerializedName("url") var url: String,
                  @SerializedName("category") var category: String,
                  @SerializedName("language") var language: String,
                  @SerializedName("country") var country: String,
                  @SerializedName("name") var name: String,
                  @SerializedName("sortBysAvailable") var sort: ArrayList<String>)
