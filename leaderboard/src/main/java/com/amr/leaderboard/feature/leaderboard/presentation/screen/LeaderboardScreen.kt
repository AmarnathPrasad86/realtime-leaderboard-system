package com.amr.leaderboard.feature.leaderboard.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.amr.leaderboard.core.presentation.components.LoadingView
import com.amr.leaderboard.feature.leaderboard.domain.model.LeaderboardEntry
import com.amr.leaderboard.feature.leaderboard.presentation.component.LeaderboardItem
import com.amr.leaderboard.feature.leaderboard.presentation.viewmodel.LeaderboardViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun LeaderboardScreen(
    viewModel: LeaderboardViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberLazyListState()
    
    // Smooth collapse detection using derivedStateOf
    val isCollapsed by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 100
        }
    }

    val myEntry = state.entries.find { it.username == "Amarnath" }

    // Matching the background gradient from the image - deep dark with a subtle top glow
    val backgroundGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF121212), Color(0xFF0A0A0A))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        // LazyColumn scrolls behind the header
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 280.dp, bottom = 24.dp)
        ) {
            item {
                SectionLabel()
            }
            
            // Handle loading and empty states
            if (state.isLoading && state.entries.isEmpty()) {
                item { LoadingView() }
            } else if (state.entries.isEmpty()) {
                // Fallback to fake data for demonstration
                val fakeEntries = List(20) { i ->
                    LeaderboardEntry(
                        userId = i.toString(),
                        username = if (i == 5) "Amarnath" else "Player ${i + 1}",
                        totalScore = 15000L - i * 250,
                        rank = i + 1
                    )
                }
                items(fakeEntries) { entry ->
                    LeaderboardItem(entry = entry)
                }
            } else {
                items(
                    items = state.entries,
                    key = { it.userId }
                ) { entry ->
                    LeaderboardItem(
                        entry = entry,
                        modifier = Modifier.animateItem(placementSpec = tween(600))
                    )
                }
            }
        }

        // Fixed Morphing Header outside LazyColumn
        MorphingHeader(myEntry ?: state.entries.find { it.username == "Amarnath" }, isCollapsed)
    }
}

@Composable
fun MorphingHeader(myEntry: LeaderboardEntry?, isCollapsed: Boolean) {
    val numberFormatter = remember { NumberFormat.getNumberInstance(Locale.US) }
    
    // Animation target values
    val headerHeight by animateDpAsState(
        targetValue = if (isCollapsed) 70.dp else 280.dp,
        animationSpec = tween(500),
        label = "HeaderHeight"
    )

    val logoScale by animateFloatAsState(
        targetValue = if (isCollapsed) 0.3f else 1.0f,
        animationSpec = tween(500),
        label = "LogoScale"
    )

    val logoTranslationX by animateDpAsState(
        targetValue = if (isCollapsed) (-80).dp else 0.dp,
        animationSpec = tween(500),
    )

    val logoTranslationY by animateDpAsState(
        targetValue = if (isCollapsed) 0.dp else (-30).dp,
        animationSpec = tween(500),
    )

    val titleTranslationX by animateDpAsState(
        targetValue = if (isCollapsed) 50.dp else 0.dp,
        animationSpec = tween(500),
    )

    val titleTranslationY by animateDpAsState(
        targetValue = if (isCollapsed) 0.dp else 55.dp,
        animationSpec = tween(500),
    )

    val titleFontSize by animateFloatAsState(
        targetValue = if (isCollapsed) 22f else 44f,
        animationSpec = tween(500),
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight),
        color = Color.Transparent
    ) {
        // Deep red/black gradient for the header to match the fire theme
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF4A0E0E), Color(0xFF121212))
                    )
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Section (Season Label) - Hidden when collapsed
                AnimatedVisibility(
                    visible = !isCollapsed,
                    enter = fadeIn(tween(300)),
                    exit = fadeOut(tween(300))
                ) {
                    Box(modifier = Modifier.padding(top = 40.dp)) {
                        Surface(
                            color = Color(0xFFFF4500).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.EmojiEvents,
                                    contentDescription = null,
                                    modifier = Modifier.size(12.dp),
                                    tint = Color(0xFFFF4500)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    "GENESIS SEASON", 
                                    style = MaterialTheme.typography.labelSmall, 
                                    color = Color(0xFFFF4500), 
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            }
                        }
                    }
                }

                // Center Section (Logo and Title) - Morphs and moves
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    // Centered Logo that shrinks and moves
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        modifier = Modifier
                            .size(140.dp)
                            .graphicsLayer {
                                scaleX = logoScale
                                scaleY = logoScale
                                translationX = logoTranslationX.toPx()
                                translationY = logoTranslationY.toPx()
                            },
                        tint = Color(0xFFFF4500)
                    )

                    // Title that moves beside logo
                    Text(
                        text = "LEGENDS",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = if (isCollapsed) 2.sp else 8.sp,
                            fontSize = titleFontSize.sp
                        ),
                        color = Color.White,
                        modifier = Modifier.graphicsLayer {
                            translationX = titleTranslationX.toPx()
                            translationY = titleTranslationY.toPx()
                        }
                    )
                }

                // Bottom Section (Badges) - Hidden when collapsed
                AnimatedVisibility(
                    visible = !isCollapsed,
                    enter = fadeIn(tween(300)),
                    exit = fadeOut(tween(300))
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 24.dp)
                    ) {
                        val rank = myEntry?.rank ?: 0
                        val suffix = when {
                            rank % 100 in 11..13 -> "th"
                            rank % 10 == 1 -> "st"
                            rank % 10 == 2 -> "nd"
                            rank % 10 == 3 -> "rd"
                            else -> "th"
                        }
                        HeaderBadge(Icons.Default.MilitaryTech, if(rank == 0) "-" else "$rank$suffix")
                        HeaderBadge(
                            Icons.Default.EmojiEvents, 
                            myEntry?.totalScore?.let { numberFormatter.format(it) } ?: "0", 
                            isGold = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SectionLabel() {
    Row(
        modifier = Modifier.padding(start = 24.dp, bottom = 12.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("LEADERBOARD", style = MaterialTheme.typography.labelMedium, color = Color.White.copy(alpha = 0.4f), letterSpacing = 1.sp)
        Spacer(Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.QueryStats,
            contentDescription = null,
            modifier = Modifier.size(14.dp),
            tint = Color.White.copy(alpha = 0.4f)
        )
    }
}

@Composable
fun HeaderBadge(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, isGold: Boolean = false) {
    Surface(
        color = if (isGold) Color(0xFFFFD700) else Color.White.copy(alpha = 0.1f),
        contentColor = if (isGold) Color.Black else Color.White,
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(text = text, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.ExtraBold))
        }
    }
}
