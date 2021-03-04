/*
 * Copyright 2021 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paulrybitskyi.gamedge.gamespot.api

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.paulrybitskyi.gamedge.commons.api.ApiResult
import com.paulrybitskyi.gamedge.commons.api.Error
import com.paulrybitskyi.gamedge.commons.api.ErrorMapper
import com.paulrybitskyi.gamedge.core.providers.DispatcherProvider
import com.paulrybitskyi.gamedge.data.articles.datastores.ArticlesRemoteDataStore
import com.paulrybitskyi.gamedge.data.commons.Pagination
import com.paulrybitskyi.gamedge.gamespot.api.articles.ApiArticle
import com.paulrybitskyi.gamedge.gamespot.api.articles.ArticlesEndpoint
import com.paulrybitskyi.gamedge.gamespot.api.articles.datastores.ArticleMapper
import com.paulrybitskyi.gamedge.gamespot.api.articles.datastores.ArticlePublicationDateMapper
import com.paulrybitskyi.gamedge.gamespot.api.articles.datastores.ArticlesGamespotDataStore
import com.paulrybitskyi.gamedge.gamespot.api.articles.datastores.mapToDataArticles
import com.paulrybitskyi.gamedge.gamespot.api.articles.entities.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


private val API_ARTICLES = listOf(
    ApiArticle(publicationDate = "2020-03-02 12:14:16"),
    ApiArticle(publicationDate = "2020-03-02 12:14:16"),
    ApiArticle(publicationDate = "2020-03-02 12:14:16")
)

private val HTTP_ERROR = Error.HttpError(code = 2, message = "http_error")
private val NETWORK_ERROR = Error.NetworkError(Exception("network_error"))
private val UNKNOWN_ERROR = Error.NetworkError(Exception("unknown_error"))

private val PAGINATION = Pagination(offset = 0, limit = 20)


internal class ArticlesGamespotDataStoreTest {


    private lateinit var articlesEndpoint: FakeArticlesEndpoint
    private lateinit var articleMapper: ArticleMapper
    private lateinit var errorMapper: ErrorMapper
    private lateinit var articlesRemoteDataStore: ArticlesRemoteDataStore


    @Before
    fun setup() {
        articlesEndpoint = FakeArticlesEndpoint()
        articleMapper = ArticleMapper(ArticlePublicationDateMapper())
        errorMapper = ErrorMapper()
        articlesRemoteDataStore = ArticlesGamespotDataStore(
            articlesEndpoint = articlesEndpoint,
            dispatcherProvider = FakeDispatcherProvider(),
            articleMapper = articleMapper,
            errorMapper = errorMapper
        )
    }


    @Test
    fun `Returns articles successfully`() = runBlockingTest {
        articlesEndpoint.shouldReturnArticles = true

        val result = articlesRemoteDataStore.getArticles(PAGINATION)

        assertEquals(
            articleMapper.mapToDataArticles(API_ARTICLES),
            result.get()
        )
    }


    @Test
    fun `Returns http error when fetching articles`() = runBlockingTest {
        articlesEndpoint.shouldReturnHttpError = true

        val result = articlesRemoteDataStore.getArticles(PAGINATION)

        assertEquals(
            errorMapper.mapToDataError(HTTP_ERROR),
            result.getError()
        )
    }


    @Test
    fun `Returns network error when fetching articles`() = runBlockingTest {
        articlesEndpoint.shouldReturnNetworkError = true

        val result = articlesRemoteDataStore.getArticles(PAGINATION)

        assertEquals(
            errorMapper.mapToDataError(NETWORK_ERROR),
            result.getError()
        )
    }


    @Test
    fun `Returns unknown error when fetching articles`() = runBlockingTest {
        articlesEndpoint.shouldReturnUnknownError = true

        val result = articlesRemoteDataStore.getArticles(PAGINATION)

        assertEquals(
            errorMapper.mapToDataError(UNKNOWN_ERROR),
            result.getError()
        )
    }


    private class FakeArticlesEndpoint : ArticlesEndpoint {

        var shouldReturnArticles = false
        var shouldReturnHttpError = false
        var shouldReturnNetworkError = false
        var shouldReturnUnknownError = false

        override suspend fun getArticles(offset: Int, limit: Int): ApiResult<List<Article>> {
            return when {
                shouldReturnArticles -> Ok(API_ARTICLES)
                shouldReturnHttpError -> Err(HTTP_ERROR)
                shouldReturnNetworkError -> Err(NETWORK_ERROR)
                shouldReturnUnknownError -> Err(UNKNOWN_ERROR)

                else -> throw IllegalStateException()
            }
        }

    }


    private class FakeDispatcherProvider(
        private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
        override val main: CoroutineDispatcher = testDispatcher,
        override val io: CoroutineDispatcher = testDispatcher,
        override val computation: CoroutineDispatcher = testDispatcher
    ) : DispatcherProvider


}