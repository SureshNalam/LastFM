package com.example.lastfm.viewmodels

import com.example.lastfm.apidataprovider.DataProviderImpl
import com.example.lastfm.models.albumSearchResults.SearchResults
import junit.framework.TestCase.assertEquals
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner.Silent::class)
class SearchAlbumViewModelTest {

    @Mock
    lateinit var dataProviderImpl: DataProviderImpl
    private lateinit var viewModel: SearchAlbumViewModel
    private lateinit var jsonData: String
    private lateinit var mockResponseData: SearchResults
    private lateinit var singleResponseData: Single<SearchResults>
    private lateinit var responseData: SearchResults

    @Before
    fun setUp() {
        viewModel = SearchAlbumViewModel(dataProviderImpl)
        jsonData = Scanner(javaClass.getResourceAsStream(SEARCH_RESULTS_JSON)!!).useDelimiter("\\Z").next()!!
        mockResponseData = Gson().fromJson(jsonData, SearchResults::class.java)!!
        singleResponseData = Single.just(mockResponseData)
    }


    @Test
    fun getSearchResultsData() {

        `when`(dataProviderImpl.getAlbumSearchResults(QUERY)).thenReturn(singleResponseData)
        viewModel.getSearchQueryResults(QUERY)
            .subscribeWith(object : DisposableSingleObserver<SearchResults>() {
                override fun onSuccess(t: SearchResults) {
                    responseData = t
                }

                override fun onError(e: Throwable) {
                    println("${e.message}")
                }
            })

        assertEquals(responseData.results.attr , mockResponseData.results.attr)
        assertEquals(responseData.results.openSearchTotalResults, mockResponseData.results.openSearchTotalResults)
        assertEquals(responseData.results.openSearchItemsPerPage, mockResponseData.results.openSearchItemsPerPage)
    }


    @Test
    fun getAlbumsData() {
        val list = viewModel.getAlbumsData(mockResponseData)
        assertEquals(list.size , mockResponseData.results.albummatches.album.size)
        assertEquals(list.last(), mockResponseData.results.albummatches.album.last())
    }

    companion object{
        const val QUERY = "Believe"
        const val SEARCH_RESULTS_JSON = "/json/mockalbumSearchResults.json"
    }
}