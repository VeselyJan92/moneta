package cz.janvesely.nba.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cz.janvesely.nba.network.model.Player
import retrofit2.HttpException
import java.io.IOException


class PlayerPagingSource(
    val myBackend: NBARepository,
    val itemsPerPage: Int = 35
) : PagingSource<Int, Player>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {

        return try {
            val pageNumber = params.key ?: 0

            val response = myBackend.getPlayers(
                page = pageNumber * itemsPerPage,
                perPage = itemsPerPage
            )

            LoadResult.Page(
                data = response.data,
                prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                nextKey = if (response.data.isNotEmpty()) pageNumber + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Player>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}