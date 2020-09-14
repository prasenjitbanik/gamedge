/*
 * Copyright 2020 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
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

package com.paulrybitskyi.gamedge.data.datastores

import com.paulrybitskyi.gamedge.data.datastores.commons.Pagination
import com.paulrybitskyi.gamedge.data.utils.DataCompany
import com.paulrybitskyi.gamedge.data.utils.DataGame
import com.paulrybitskyi.gamedge.data.utils.DataResult

interface GamesServerDataStore {

    suspend fun searchGames(searchQuery: String, pagination: Pagination): DataResult<List<DataGame>>

    suspend fun getPopularGames(pagination: Pagination): DataResult<List<DataGame>>

    suspend fun getRecentlyReleasedGames(pagination: Pagination): DataResult<List<DataGame>>

    suspend fun getComingSoonGames(pagination: Pagination): DataResult<List<DataGame>>

    suspend fun getMostAnticipatedGames(pagination: Pagination): DataResult<List<DataGame>>

    suspend fun getCompanyGames(company: DataCompany, pagination: Pagination): DataResult<List<DataGame>>

    suspend fun getSimilarGames(game: DataGame, pagination: Pagination): DataResult<List<DataGame>>

}