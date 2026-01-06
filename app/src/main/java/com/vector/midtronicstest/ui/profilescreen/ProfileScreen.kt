@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.vector.midtronicstest.ui.profilescreen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vector.midtronicstest.ui.theme.MidtronicsTestTheme
import com.vector.midtronicstest.ui.theme.githubIconResource
import com.vector.midtronicstest.ui.theme.linkedinIconResource
import com.vector.midtronicstest.ui.theme.profilePictureResource
import com.vector.midtronicstest.ui.theme.schoolIconResource
import com.vector.midtronicstest.ui.theme.workIconResource

@Composable
fun ProfileScreenRoot(
    innerPadding: PaddingValues,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    ProfileScreen(
        innerPadding = innerPadding,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope
    )
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    with(sharedTransitionScope) {
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(12.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(profilePictureResource),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "profile-pic-anim"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Victor Tijerina",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Android Engineer",
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp)
            )

            SectionHeader(
                modifier = Modifier.padding(bottom = 8.dp),
                title = "Education",
                icon = schoolIconResource
            )
            EducationSummary()

            Spacer(Modifier.height(12.dp))


            SectionHeader(
                modifier = Modifier.padding(vertical = 8.dp),
                title = "Experience",
                icon = workIconResource
            )

            Spacer(Modifier.height(12.dp))

            WorkExperienceSummary()

            Spacer(Modifier.height(12.dp))

            SocialsLinks()

        }
    }

}

@Composable
private fun SocialsLinks(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My socials",
            fontSize = 20.sp,
            color = Color.Gray
        )
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SocialIcon(
                icon = linkedinIconResource,
                url = "https://www.linkedin.com/in/victor-tijerina-g",
                contentDescription = "Linkedin profile"
            )

            SocialIcon(
                icon = githubIconResource,
                url = "https://github.com/Vectorr22",
                contentDescription = "Github profile"
            )

        }
    }
}

@Composable
private fun SocialIcon(
    icon: Int,
    url: String,
    contentDescription: String
) {
    val uriHandler = LocalUriHandler.current 

    IconButton(
        onClick = { uriHandler.openUri(url) } 
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            tint = Color.Unspecified,
            modifier = Modifier.size(60.dp)
        )
    }
}

@Composable
private fun SectionHeader(
    modifier: Modifier = Modifier,
    title: String,
    icon: Int
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(20.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun EducationSummary(
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Instituto Tecnologico de La Laguna",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "BS in Computer Systems Engineering",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Specialized in Mobile Development",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun WorkExperienceSummary(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        //android
        WorkExperienceItem(
            role = "Independent Android Engineer",
            company = "Self-Employed",
            period = "Jan 2023 - Present",
            description = "• Architected production-ready apps using Clean Architecture & Jetpack Compose.\n" +
                    "• Engineered background services (Foreground/WorkManager) for reliable data sync.\n" +
                    "• Managed full CI/CD release lifecycles."
        )

        //tyson
        WorkExperienceItem(
            role = "Data Engineer Intern",
            company = "Tyson Foods",
            period = "Aug 2024 - Dec 2024",
            description = "• Managed critical migration of 10TB+ data to Google Cloud Platform.\n" +
                    "• Optimized SQL pipelines reducing query runtime by 15%."
        )

        //coquete
        WorkExperienceItem(
            role = "IT Systems Developer",
            company = "La Coqueta",
            period = "Feb 2023 - Jun 2023",
            description = "• Automated business reporting scripts, cutting downtime by 30%."
        )
    }
}

@Composable
private fun WorkExperienceItem(
    role: String,
    company: String,
    period: String,
    description: String
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = role, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                    Text(text = company, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                }
                Text(text = period, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.End)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description, style = MaterialTheme.typography.bodySmall, lineHeight = 18.sp)
        }
    }
}


@Preview
@Composable
private fun ProfileScreenPreview() {
    MidtronicsTestTheme {
//        ProfileScreen()
    }
}