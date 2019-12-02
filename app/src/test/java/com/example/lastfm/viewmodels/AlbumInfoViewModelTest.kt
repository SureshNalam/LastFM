package com.example.lastfm.viewmodels

import com.example.lastfm.apidataprovider.DataProviderImpl
import com.example.lastfm.models.albumInfo.AlbumInfo
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner.Silent::class)
class AlbumInfoViewModelTest {

    @Mock
    lateinit var dataProviderImpl: DataProviderImpl
    private lateinit var viewModel: AlbumInfoViewModel
    private lateinit var jsonData: String
    private lateinit var responseData: AlbumInfo
    private lateinit var mockResponseData: AlbumInfo
    private lateinit var singleResponseData: Single<AlbumInfo>
    private lateinit var singleErrorResponse: Single<Throwable>

    @Before
    fun setUp() {
        jsonData = Scanner(javaClass.getResourceAsStream(ALBUM_INFO_JSON)!!).useDelimiter("\\Z").next()!!
        mockResponseData = Gson().fromJson(jsonData, AlbumInfo::class.java)!!
        singleResponseData = Single.just(mockResponseData)
        viewModel = AlbumInfoViewModel(dataProviderImpl)
        singleErrorResponse = Single.error(Throwable(ERROR_MSG))
    }

    @Test
    fun testGetAlbumInfo() {

        `when`(dataProviderImpl.getAlbumDetails(ARTIST_NAME, ALBUM_NAME)).thenReturn(
            singleResponseData
        )
        viewModel.getAlbumInfo(ALBUM_NAME, ARTIST_NAME)
            .subscribeWith(object : DisposableSingleObserver<AlbumInfo>() {
                override fun onSuccess(albumInfo: AlbumInfo) {
                    responseData = albumInfo
                }

                override fun onError(e: Throwable) {
                    println(" ${e.message}}")
                }

            })

        assertEquals(responseData.album.artist, ARTIST_NAME)
        assertEquals(responseData.album.name, ALBUM_NAME)
    }

    @Test
    fun testGetAlbumName() {
        assertEquals(viewModel.getAlbumName(mockResponseData), ALBUM_NAME)
    }

    @Test
    fun testGetArtistName(){
        assertEquals(viewModel.getArtistName(mockResponseData), ARTIST_NAME)
    }

    @Test
    fun testGetPublishedDate(){
        assertEquals(viewModel.getPublishDate(mockResponseData), PUBLISH_DATE)
    }
    @Test
    fun testGetSummary(){
        assertEquals(viewModel.getSummary(mockResponseData), SUMMARY)
    }


    companion object{
        const val ARTIST_NAME = "Cher"
        const val ALBUM_NAME = "Believe"
        const val PUBLISH_DATE = "27 Jul 2008, 15:55"
        const val SUMMARY =
            "Believe is the twenty-third studio album by American  singer-actress Cher, " +
                    "released on November 10, 1998 by Warner Bros. Records." +
                    " The RIAA certified it Quadruple Platinum on December 23, 1999," +
                    " recognizing four million shipments in the United States; Worldwide, " +
                    "the album has sold more than 20 million copies," +
                    " making it the biggest-selling album of her career. " +
                    "In 1999 the album received three Grammy Awards nominations including" +
                    " \"Record of the Year\"," +
                    " \"Best Pop Album\"" +
                    " and winning " +
                    "\"Best Dance Recording\" for the single \"Believe\"." +
                    " <a href=\"http://www.last.fm/music/Cher/Believe\">Read more on Last.fm</a>."

        const val ERROR_MSG = "Unable to resolve host"
        const val ALBUM_INFO_JSON = "/json/mockalbumData.json"
    }
}
