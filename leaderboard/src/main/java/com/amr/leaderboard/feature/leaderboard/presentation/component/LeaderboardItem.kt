package com.amr.leaderboard.feature.leaderboard.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.amr.leaderboard.feature.leaderboard.domain.model.LeaderboardEntry
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale

@Composable
fun LeaderboardItem(
    entry: LeaderboardEntry,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val numberFormatter = remember { NumberFormat.getNumberInstance(Locale.US) }

    val (cardColor, rankBadgeColor) = when (entry.rank) {
        1 -> Color(0xFFFFF8E1) to Color(0xFFFFD700)
        2 -> Color(0xFFF5F5F5) to Color(0xFFC0C0C0)
        3 -> Color(0xFFFFF3E0) to Color(0xFFCD7F32)
        else -> MaterialTheme.colorScheme.surface to MaterialTheme.colorScheme.outlineVariant
    }

    var isUpdating by remember { mutableStateOf(false) }
    LaunchedEffect(entry.totalScore) {
        isUpdating = true
        delay(400)
        isUpdating = false
    }

    val scale by animateFloatAsState(
        targetValue = if (isUpdating) 1.03f else if (isSelected) 1.02f else 1f,
        animationSpec = tween(300), label = "scale"
    )

    val glowColor by animateColorAsState(
        targetValue = if (isUpdating) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) 
                      else if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                      else cardColor,
        animationSpec = tween(300), label = "glow"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary 
                        else if (isUpdating) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) 
                        else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 4.dp else 2.dp),
        colors = CardDefaults.cardColors(containerColor = glowColor)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 14.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(rankBadgeColor.copy(alpha = 0.2f))
                        .border(1.dp, rankBadgeColor.copy(alpha = 0.5f), CircleShape)
                ) {
                    Text(
                        text = when(entry.rank) {
                            1 -> "🥇"
                            2 -> "🥈"
                            3 -> "🥉"
                            else -> entry.rank.toString()
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (entry.rank <= 3) rankBadgeColor else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = entry.username,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Player ID: ${entry.userId}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                AnimatedContent(
                    targetState = entry.totalScore,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(400)).togetherWith(fadeOut(animationSpec = tween(200)))
                    },
                    label = "ScoreAnim"
                ) { targetScore ->
                    Text(
                        text = numberFormatter.format(targetScore),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(90.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 74.dp, end = 16.dp, bottom = 12.dp)
                ) {
                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 8.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                    )
                    Text(
                        text = "Realtime Stats:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "• Connection verified & active",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
