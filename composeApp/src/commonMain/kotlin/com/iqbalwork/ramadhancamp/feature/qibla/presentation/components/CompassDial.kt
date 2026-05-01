package com.iqbalwork.ramadhancamp.feature.qibla.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CompassDial(
    heading: Float,
    bearingToKaaba: Float?,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    val colors = RamadhanTheme.colors
    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(
        color = colors.textPrimary,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.width / 2

            rotate(degrees = -heading, pivot = center) {
                val petalColor = Color(0xFF132F28).copy(alpha = 0.5f)
                val petalSize = Size(radius * 1.6f, radius * 1.6f)
                val petalOffset = Offset(center.x - petalSize.width / 2, center.y - petalSize.height / 2)
                for (i in 0 until 4) {
                    rotate(degrees = i * 45f, pivot = center) {
                        drawRoundRect(
                            color = petalColor,
                            topLeft = petalOffset,
                            size = petalSize,
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(radius * 0.4f)
                        )
                    }
                }

                drawCircle(
                    color = Color(0xFF262C2A),
                    radius = radius,
                    center = center
                )

                for (i in 0 until 120) {
                    val angle = i * 3f
                    val isMajor = i % 10 == 0
                    val isSuperMajor = i % 30 == 0

                    val innerRadius = if (isSuperMajor) radius * 0.85f else if (isMajor) radius * 0.88f else radius * 0.94f
                    val outerRadius = radius * 0.97f

                    val angleRad = (angle - 90) * (PI / 180f).toFloat()

                    drawLine(
                        color = if (isSuperMajor) colors.textPrimary else colors.textMuted,
                        start = Offset(
                            x = center.x + innerRadius * cos(angleRad),
                            y = center.y + innerRadius * sin(angleRad)
                        ),
                        end = Offset(
                            x = center.x + outerRadius * cos(angleRad),
                            y = center.y + outerRadius * sin(angleRad)
                        ),
                        strokeWidth = if (isSuperMajor) 2.dp.toPx() else 1.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                val cardinalRadius = radius * 0.72f
                val cardinals = listOf("U" to 0f, "T" to 90f, "S" to 180f, "B" to 270f)

                cardinals.forEach { (label, angle) ->
                    val textLayoutResult = textMeasurer.measure(label, textStyle)
                    val angleRad = (angle - 90) * (PI / 180f).toFloat()
                    val x = center.x + cardinalRadius * cos(angleRad) - textLayoutResult.size.width / 2
                    val y = center.y + cardinalRadius * sin(angleRad) - textLayoutResult.size.height / 2

                    rotate(degrees = heading, pivot = Offset(x + textLayoutResult.size.width / 2, y + textLayoutResult.size.height / 2)) {
                        drawText(
                            textLayoutResult = textLayoutResult,
                            topLeft = Offset(x, y)
                        )
                    }
                }

                if (bearingToKaaba != null && !isLoading) {
                    rotate(degrees = bearingToKaaba, pivot = center) {
                        drawLine(
                            color = colors.accentGold,
                            start = center,
                            end = Offset(center.x, center.y - radius * 0.65f),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )

                        drawCircle(
                            color = colors.accentGold,
                            radius = 6.dp.toPx(),
                            center = center
                        )

                        translate(top = center.y - radius * 0.65f, left = center.x) {
                            val kaabaSize = 24.dp.toPx()
                            val halfSize = kaabaSize / 2

                            drawRect(
                                color = colors.accentGold,
                                topLeft = Offset(-halfSize, -halfSize),
                                size = Size(kaabaSize, kaabaSize)
                            )
                            drawRect(
                                color = Color.Black,
                                topLeft = Offset(-halfSize + 2.dp.toPx(), -halfSize + 2.dp.toPx()),
                                size = Size(kaabaSize - 4.dp.toPx(), kaabaSize - 4.dp.toPx())
                            )
                            drawRect(
                                color = colors.accentGold,
                                topLeft = Offset(-halfSize + 6.dp.toPx(), halfSize - 10.dp.toPx()),
                                size = Size(4.dp.toPx(), 8.dp.toPx())
                            )
                            drawRect(
                                color = colors.accentGold,
                                topLeft = Offset(-halfSize + 2.dp.toPx(), -halfSize + 6.dp.toPx()),
                                size = Size(kaabaSize - 4.dp.toPx(), 2.dp.toPx())
                            )
                        }
                    }
                }
            }
        }
    }
}
