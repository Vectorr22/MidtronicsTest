@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.vector.midtronicstest.ui.homescreen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.vector.midtronicstest.R
import com.vector.midtronicstest.data.model.CountryUi
import com.vector.midtronicstest.ui.theme.MidtronicsTestTheme
import com.vector.midtronicstest.ui.theme.imageLoadingPlaceHolder
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreenRoot(
    onCountryClicked: (String) -> Unit,
    innerPaddingValues: PaddingValues,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
    val state by homeScreenViewModel.state.collectAsStateWithLifecycle()
    val parentContext = LocalContext.current
    val events = homeScreenViewModel.events
    LaunchedEffect(events) {
        events.collect { actions ->
            when (actions) {
                HomeUiActions.EmptyData -> {
                    Toast.makeText(
                        parentContext,
                        "No data fetched!",
                        Toast.LENGTH_SHORT
                    )
                }

                HomeUiActions.Error -> {
                    Toast.makeText(
                        parentContext,
                        "Error fetching data!",
                        Toast.LENGTH_LONG
                    )
                }
            }
        }
    }
    HomeScreen(
        state = state,
        onCountryClicked = onCountryClicked,
        innerPadding = innerPaddingValues,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    onCountryClicked: (String) -> Unit,
    innerPadding: PaddingValues,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val context = LocalContext.current
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text(
                    text = "Choose a country",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(
                items = state.listOfCountries,
                key = { it.cca2 }
            ) { countryUI ->

                CountryUiCard(
                    countryUi = countryUI,
                    context = context,
                    onCardClicked = { countryName ->
                        onCountryClicked(countryName)
                    },
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }
        }
    }
}

@Composable
fun CountryUiCard(
    modifier: Modifier = Modifier,
    countryUi: CountryUi,
    onCardClicked: (String) -> Unit,
    context: Context,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    ElevatedCard(
        onClick = {
            onCardClicked(countryUi.commonName)
        }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            with(sharedTransitionScope) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(countryUi.flagUrl)
                        .crossfade(true)
                        .build(),

                    placeholder = painterResource(imageLoadingPlaceHolder),
                    contentDescription = "Image for ${countryUi.commonName}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = "image-${countryUi.commonName}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )
            }

            Text(
                text = countryUi.commonName,
                fontSize = 16.sp,
            )

            Text(
                text = countryUi.cca2,
                fontSize = 12.sp
            )
        }
    }

}

