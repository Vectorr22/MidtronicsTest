@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.vector.midtronicstest.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vector.midtronicstest.ui.common.MidtronicsTestTopBar
import com.vector.midtronicstest.ui.detailscreen.DetailScreenRoot
import com.vector.midtronicstest.ui.homescreen.HomeScreenRoot
import com.vector.midtronicstest.ui.profilescreen.ProfileScreenRoot
import kotlinx.serialization.Serializable


@Serializable
object Home

@Serializable
object Profile

@Serializable
data class Detail(val name: String)

@Composable
fun NavRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Home
        ) {
            //home
            composable<Home> {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MidtronicsTestTopBar(
                            title = "Home",
                            canNavigateBack = false,
                            isInProfileScreen = false,
                            onClickProfile = {
                                navController.navigate(Profile)
                            },
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = this@composable
                        )
                    }
                ) { innerPadding ->
                    HomeScreenRoot(
                        innerPaddingValues = innerPadding,
                        onCountryClicked = { countryName ->
                            navController.navigate(Detail(countryName))
                        },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable
                    )
                }
            }

            //detail
            composable<Detail> { navBackStack ->
                val arguments = navBackStack.toRoute<Detail>()
                val name = arguments.name
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MidtronicsTestTopBar(
                            title = name,
                            canNavigateBack = true,
                            isInProfileScreen = false,
                            onClickNavBack = {
                                navController.navigateUp()
                            },
                            onClickProfile = {
                                navController.navigate(Profile)
                            },
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = this@composable
                        )
                    }
                ) { innerPadding ->
                    DetailScreenRoot(
                        innerPaddingValues = innerPadding,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable
                    )
                }
            }
            //profile
            composable<Profile> {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MidtronicsTestTopBar(
                            title = "Profile",
                            canNavigateBack = true,
                            isInProfileScreen = true,
                            onClickNavBack = {
                                navController.navigateUp()
                            },
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedVisibilityScope = this@composable
                        )
                    }
                ) { innerPadding ->
                    ProfileScreenRoot(
                        innerPadding = innerPadding,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@composable
                    )
                }

            }

        }

    }

}