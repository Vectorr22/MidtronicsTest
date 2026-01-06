@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.vector.midtronicstest.ui.common

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vector.midtronicstest.R
import com.vector.midtronicstest.ui.theme.MidtronicsTestTheme
import com.vector.midtronicstest.ui.theme.companyLogoResource
import com.vector.midtronicstest.ui.theme.mainBlue
import com.vector.midtronicstest.ui.theme.mainWhite
import com.vector.midtronicstest.ui.theme.profilePictureResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MidtronicsTestTopBar(
    modifier: Modifier = Modifier,
    title: String,
    canNavigateBack: Boolean,
    isInProfileScreen: Boolean,
    onClickNavBack: () -> Unit = {},
    onClickProfile: () -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = mainBlue,
            titleContentColor = mainWhite,
            navigationIconContentColor = mainWhite
        ),

        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = onClickNavBack,
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_back)
                    )
                }
            } else {
                Image(
                    painter = painterResource(companyLogoResource),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp)
                )
            }
        },
        title = {
            Text(
                text = title,
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        actions = {
            if(!isInProfileScreen) {
                with(sharedTransitionScope) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(width = 2.dp, color = Color.Black, shape = CircleShape)
                            .background(mainWhite)
                            .clickable { onClickProfile() }
                            .padding(4.dp)
                    ) {
                        Image(
                            painter = painterResource(profilePictureResource),
                            contentDescription = "Account",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .sharedElement(
                                    sharedContentState = rememberSharedContentState(key = "profile-pic-anim"),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                        )
                    }
                }
            }
        }
    )
}

//@Preview
//@Composable
//private fun MidtronicsTestTopBarPreview() {
//    MidtronicsTestTheme {
//        MidtronicsTestTopBar(
//            title = "Test title",
//            canNavigateBack = true,
//            isInProfileScreen = false,
//            sharedTransitionScope = this,
//            animatedVisibilityScope = this
//        )
//    }
//}