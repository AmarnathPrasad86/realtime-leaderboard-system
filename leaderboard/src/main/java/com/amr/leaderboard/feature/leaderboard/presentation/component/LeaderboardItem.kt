package com.amr.leaderboard.feature.leaderboard.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Face2
import androidx.compose.material.icons.filled.Face3
import androidx.compose.material.icons.filled.Face4
import androidx.compose.material.icons.filled.Face5
import androidx.compose.material.icons.filled.Face6
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amr.leaderboard.feature.leaderboard.domain.model.LeaderboardEntry
import java.text.NumberFormat
import java.util.Locale

@Composable
fun LeaderboardItem(
    entry: LeaderboardEntry,
    modifier: Modifier = Modifier
) {
    val numberFormatter = remember { NumberFormat.getNumberInstance(Locale.US) }

    // Sequence: User Icon -> Position -> Name -> Points

    // 1. Selection of Avatars
    val avatars = listOf(Icons.Default.Face, Icons.Default.Face2, Icons.Default.Face3, Icons.Default.Face4, Icons.Default.Face5, Icons.Default.Face6)
    val avatarIcon = remember(entry.userId) {
        if (entry.userId == "me") Icons.Default.Person 
        else avatars[entry.userId.hashCode().coerceAtLeast(0) % avatars.size]
    }

    val avatarColor = remember(entry.userId) {
        val colors = listOf(Color(0xFFE91E63), Color(0xFF4CAF50), Color(0xFF2196F3), Color(0xFF9C27B0), Color(0xFFFF9800))
        colors[entry.userId.hashCode().coerceAtLeast(0) % colors.size]
    }

    val rankTextColor = when (entry.rank) {
        1 -> Color(0xFFFFD700)
        2 -> Color(0xFFC0C0C0)
        3 -> Color(0xFFCD7F32)
        else -> Color.White.copy(alpha = 0.6f)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // A. User Icon
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(avatarColor.copy(alpha = 0.15f))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = avatarIcon,
                    contentDescription = null,
                    tint = avatarColor
                )
            }

            Spacer(Modifier.width(16.dp))

            // B. Position (Rank)
            Text(
                text = "${entry.rank}.",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black, fontSize = 17.sp),
                color = rankTextColor,
                modifier = Modifier.width(36.dp)
            )

            // C. Name
            Text(
                text = entry.username,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp),
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            // D. Points
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFFFFD700)
                )
                Spacer(Modifier.width(6.dp))
                AnimatedContent(
                    targetState = entry.totalScore,
                    transitionSpec = { fadeIn(tween(400)).togetherWith(fadeOut(tween(200))) },
                    label = "Score"
                ) { score ->
                    Text(
                        text = numberFormatter.format(score),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
            }
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = Color.White.copy(alpha = 0.05f))
    }
}
