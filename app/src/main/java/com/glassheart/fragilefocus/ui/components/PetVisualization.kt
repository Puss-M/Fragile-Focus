package com.glassheart.fragilefocus.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glassheart.fragilefocus.domain.PetState
import com.glassheart.fragilefocus.domain.getEmoji

/**
 * 宠物可视化组件
 * MVP: 使用Emoji + Compose动画
 */
@Composable
fun PetVisualization(
    petState: PetState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        when (petState) {
            is PetState.Healthy -> HealthyPet()
            is PetState.Poisoned -> PoisonedPet()
            is PetState.Critical -> CriticalPet()
            is PetState.Dead -> DeadPet()
        }
    }
}

/**
 * 健康状态 - 缓慢跳动
 */
@Composable
private fun HealthyPet() {
    val infiniteTransition = rememberInfiniteTransition(label = "healthy")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    Text(
        text = "😊",
        fontSize = 120.sp,
        modifier = Modifier.scale(scale)
    )
}

/**
 * 中毒状态 - 震动
 */
@Composable
private fun PoisonedPet() {
    val infiniteTransition = rememberInfiniteTransition(label = "poisoned")
    
    val offsetX by infiniteTransition.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shake"
    )
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🤢",
            fontSize = 120.sp,
            modifier = Modifier
                .offset(x = offsetX.dp)
                .graphicsLayer { rotationZ = rotation }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // 毒雾效果
        Text(
            text = "☠️💨",
            fontSize = 32.sp,
            modifier = Modifier.alpha(0.6f)
        )
    }
}

/**
 * 濒死状态 - 闪烁
 */
@Composable
private fun CriticalPet() {
    val infiniteTransition = rememberInfiniteTransition(label = "critical")
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blink"
    )
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "💀",
            fontSize = 120.sp,
            modifier = Modifier
                .alpha(alpha)
                .scale(scale)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "⚠️ 危在旦夕！",
            fontSize = 20.sp,
            color = androidx.compose.ui.graphics.Color.Red,
            modifier = Modifier.alpha(alpha)
        )
    }
}

/**
 * 死亡状态 - 淡出并倒下
 */
@Composable
private fun DeadPet() {
    val rotation by remember {
        Animatable(0f)
    }
    
    LaunchedEffect(Unit) {
        rotation.animateTo(
            targetValue = 90f,
            animationSpec = tween(1500, easing = EaseOutBounce)
        )
    }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "⚰️",
            fontSize = 120.sp,
            modifier = Modifier.graphicsLayer { rotationZ = rotation.value }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "💔 宠物已死亡",
            fontSize = 24.sp,
            color = androidx.compose.ui.graphics.Color(0xFFF44336)
        )
    }
}

// 扩展函数 - 用于更新Modifier
import androidx.compose.ui.graphics.graphicsLayer
