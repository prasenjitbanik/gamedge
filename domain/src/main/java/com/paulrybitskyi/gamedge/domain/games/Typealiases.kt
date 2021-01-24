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

package com.paulrybitskyi.gamedge.domain.games

import com.paulrybitskyi.gamedge.domain.commons.usecases.ObservableUseCase
import com.paulrybitskyi.gamedge.domain.games.commons.ObserveGamesUseCaseParams
import com.paulrybitskyi.gamedge.domain.games.entities.*


typealias ObservableGamesUseCase = ObservableUseCase<ObserveGamesUseCaseParams, List<Game>>

typealias DomainAgeRating = AgeRating
typealias DomainAgeRatingCategory = AgeRatingCategory
typealias DomainAgeRatingType = AgeRatingType
typealias DomainCompany = Company
typealias DomainGame = Game
typealias DomainGenre = Genre
typealias DomainCategory = Category
typealias DomainImage = Image
typealias DomainVideo = Video
typealias DomainReleaseDate = ReleaseDate
typealias DomainReleaseDateCategory = ReleaseDateCategory
typealias DomainInvolvedCompany = InvolvedCompany
typealias DomainKeyword = Keyword
typealias DomainMode = Mode
typealias DomainPlatform = Platform
typealias DomainPlayerPerspective = PlayerPerspective
typealias DomainTheme = Theme
typealias DomainWebsite = Website
typealias DomainWebsiteCategory = WebsiteCategory