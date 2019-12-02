package com.example.lastfm.models.albumSearchResults

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("@attr")
    val attr: Attr,
    val albummatches: Albummatches,
    @SerializedName("opensearch:Query")
    val openSearchQuery: OpensearchQuery,
    @SerializedName("opensearch:itemsPerPage")
    val openSearchItemsPerPage: String,
    @SerializedName("opensearch:startIndex")
    val openSearchStartIndex: String,
    @SerializedName("opensearch:totalResults")
    val openSearchTotalResults: String
)