package com.glassheart.fragilefocus.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glassheart.fragilefocus.domain.PetManager
import com.glassheart.fragilefocus.domain.PetState
import com.glassheart.fragilefocus.domain.getDescription
import com.glassheart.fragilefocus.domain.getEmoji
import com.glassheart.fragilefocus.service.MonitorService
import com.glassheart.fragilefocus.ui.components.PetVisualization
import com.glassheart.fragilefocus.ui.theme.CustomColors
import androidx.compose.ui.platform.LocalContext

/**
 * 主界面
 * 显示宠物、HP条、状态信息
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    
    // TODO: 通过依赖注入获取PetManager
    // val petManager: PetManager = hiltViewModel()
    
    // 临时模拟数据用于UI展示
    var petState by remember { mutableStateOf<PetState>(PetState.Healthy) }
    var hp by remember { mutableFloatStateOf(100f) }
    
    // 启动监控服务
    LaunchedEffect(Unit) {
        MonitorService.start(context)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fragile Focus", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = { /* TODO: 打开设置 */ }) {
                        Icon(Icons.Default.Settings, contentDescription = "设置")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // 顶部状态信息
            StatusCard(petState = petState)
            
            // 中间宠物可视化
            PetVisualization(
                petState = petState,
                modifier = Modifier.weight(1f)
            )
            
            // 底部HP条
            HpBar(hp = hp)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 调试按钮（用于模拟状态变化）
            DebugControls(
                onHealthy = { 
                    petState = PetState.Healthy
                    hp = 100f
                },
                onPoisoned = {
                    petState = PetState.Poisoned("抖音")
                    hp = 50f
                },
                onCritical = {
                    petState = PetState.Critical
                    hp = 20f
                },
                onDead = {
                    petState = PetState.Dead("过度使用抖音", "抖音", 3600)
                    hp = 0f
                }
            )
        }
    }
}

@Composable
private fun StatusCard(petState: PetState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "状态",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = petState.getDescription(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Text(
                text = petState.getEmoji(),
                fontSize = 32.sp
            )
        }
    }
}

@Composable
private fun HpBar(hp: Float) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "HP",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = "${hp.toInt()}/100",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // HP进度条
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(hp / 100f)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                CustomColors.HpGradientStart,
                                CustomColors.HpGradientEnd
                            )
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
            )
        }
    }
}

@Composable
private fun DebugControls(
    onHealthy: () -> Unit,
    onPoisoned: () -> Unit,
    onCritical: () -> Unit,
    onDead: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "⚙️ 调试控制",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onHealthy, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = CustomColors.HealthyGreen)) {
                    Text("健康", fontSize = 12.sp)
                }
                Button(onClick = onPoisoned, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = CustomColors.PoisonedYellow)) {
                    Text("中毒", fontSize = 12.sp)
                }
                Button(onClick = onCritical, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = CustomColors.CriticalOrange)) {
                    Text("濒死", fontSize = 12.sp)
                }
                Button(onClick = onDead, modifier = Modifier.weight(1f), colors = ButtonDefaults.buttonColors(containerColor = CustomColors.DeadRed)) {
                    Text("死亡", fontSize = 12.sp)
                }
            }
        }
    }
}
