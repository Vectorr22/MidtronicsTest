@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.vector.midtronicstest.ui.detailscreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.vector.midtronicstest.data.model.Country
import com.vector.midtronicstest.ui.theme.MidtronicsTestTheme
import com.vector.midtronicstest.ui.theme.imageLoadingPlaceHolder

@Composable
fun DetailScreenRoot(
    innerPaddingValues: PaddingValues,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {

    val detailScreenViewModel: DetailScreenViewModel = hiltViewModel()
    val state by detailScreenViewModel.state.collectAsStateWithLifecycle()

    DetailScreen(
        innerPaddingValues = innerPaddingValues,
        state = state,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope
    )
}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues,
    state: DetailScreenUiState,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val context = LocalContext.current
    if (state.country == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val country = state.country
        with(sharedTransitionScope) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPaddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = country.officialName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(country.flagUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(imageLoadingPlaceHolder),
                    contentDescription = "Image for ${country.commonName}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                        .clip(RoundedCornerShape(16.dp))
                        .sharedElement(
                            sharedContentState = rememberSharedContentState(key = "image-${country.commonName}"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                )

                Spacer(Modifier.height(20.dp))
                CountryDetails(country = country)
            }
        }
    }

}

@Composable
private fun CountryDetails(
    modifier: Modifier = Modifier,
    country: Country
) {
    ElevatedCard {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Capital",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,

                    )

                Text(
                    text = "Population",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,

                    )

                Text(
                    text = "Area",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,

                    )

                Text(
                    text = "Region",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,

                    )

                Text(
                    text = "SubRegion",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,

                    )


            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = country.capital,
                    fontSize = 18.sp,
                    color = Color.Gray
                )

                Text(
                    text = country.population.toString(),
                    fontSize = 18.sp,
                    color = Color.Gray
                )

                Text(
                    text = country.area.toString(),
                    fontSize = 18.sp,
                    color = Color.Gray
                )

                Text(
                    text = country.region,
                    fontSize = 18.sp,
                    color = Color.Gray
                )

                Text(
                    text = country.subRegion,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun DetailScreenPreview() {
//    MidtronicsTestTheme {
//        DetailScreen(
//            state = DetailScreenUiState(),
//            innerPaddingValues = PaddingValues(1.dp)
//        )
//    }
//}