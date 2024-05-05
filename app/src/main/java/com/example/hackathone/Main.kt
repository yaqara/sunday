package com.example.hackathone

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import calcMed
import com.example.hackathone.components.CircleButton
import com.example.hackathone.components.MainButton
import com.example.hackathone.components.NameTextField
import com.example.hackathone.calcs.calcDosage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Main : ComponentActivity() {
    private var choose = 0
    private var ratioSynthesis = 0
    private val values = arrayOf(
        "", ""
    )
    private val dosage = arrayOf(
        2000
    )

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    NavMenu(navController)
                }
            ) {
                NavGraph(navController = navController)
            }
        }
    }

    @Composable
    fun NavGraph(navController: NavHostController) {
        NavHost(navController, startDestination = "home") {
            composable("home") { HomeScreen() }
            composable("settings") { SettingsScreen() }
            composable("profile") { ProfileScreen() }
        }
    }

    @Composable
    fun NavMenu(navController: NavHostController) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NavEl(
                iconUrl = Icons.Rounded.Settings,
                navController = navController,
                route = "settings"
            )
            NavEl(
                iconUrl = Icons.Rounded.Home,
                navController = navController,
                route = "home"
            )
            NavEl(
                iconUrl = Icons.Rounded.AccountCircle,
                navController = navController,
                route = "profile"
            )
        }
    }

    @Composable
    fun NavEl(iconUrl: ImageVector, navController: NavHostController, route: String) {
        IconButton(onClick = { navController.navigate(route) }) {
            Icon(iconUrl, contentDescription = route)
        }
    }

// SETTINGS SCREEN

    @Composable
    fun SettingsScreen() {
        val genderOptions = listOf("Мужской", "Женский")
        var selectedGender by remember { mutableStateOf("") }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(text = "Выберите свой тон кожи")
            DialogTone()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    "Возраст",
                    Modifier.padding(16.dp)
                )
                NameTextField(value = values[0], onValueChange = { values[0] = it })
            }
            Column {
                genderOptions.forEach { gender ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedGender == gender,
                            onClick = {
                                selectedGender = gender
                                values[1] = when (gender) {
                                    "Женский" -> "2"
                                    "Мужской" -> "1"
                                    else -> "0"
                                }
                            }
                        )
                        Text(
                            text = gender,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
            MainButton(func = {
                if (
                    (values[0] != "" || values[1] != "" || choose != 0) ||
                    (values[0] != "" && values[1] != "" && choose != 0)
                ) {
                    Toast.makeText(this@Main, "Done", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@Main, "Заполните все данные", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text(text = "Confirm")
            }
        }
    }

    @Composable
    fun DialogTone() {
        val showDialog = remember {
            mutableStateOf(false)
        }
        val colors = arrayOf(
            0xFFFFE5D9,
            0xFFFFDFD1,
            0xFFD6A48C,
            0xFFCC9966,
            0xFFA56E50,
            0xFF6A3D30
        )
        MainButton({ showDialog.value = true }) {
            Text(text = "Готовые решения")
        }
        if (showDialog.value) {
            Dialog(
                onDismissRequest = { showDialog.value = false }
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Choose a Photo")
                    for (color in colors) {
                        Box(
                            modifier = Modifier
                                .height(30.dp)
                                .width(100.dp)
                                .padding(1.dp)
                                .border(width = 0.5.dp, color = Color.Black)
                                .background(color = Color(color))
                                .clickable {
                                    choose = colors.indexOfFirst { it == color } + 1
                                    showDialog.value = false
                                    ratioSynthesis = when (choose) {
                                        1 -> 1000
                                        2 -> 800
                                        3 -> 600
                                        4 -> 400
                                        5 -> 200
                                        6 -> 100
                                        else -> {
                                            0
                                        }
                                    }
                                    Toast
                                        .makeText(
                                            this@Main,
                                            "Вы выбрали $choose тон",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                                .padding(vertical = 4.dp)
                        )
                    }
                    MainButton({ showDialog.value = false }) {
                        Text("Cancel")
                    }
                }
            }
        }
    }

// HOME SCREEN

    @Composable
    fun HomeScreen() {
        val context = LocalContext.current
        val sensorManager = context.getSystemService(SensorManager::class.java)
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        val lightValue = remember { mutableFloatStateOf(0f) }
        val running = remember { mutableStateOf(false) }
        val time = remember { mutableLongStateOf(0L) }
        val coroutineScope = rememberCoroutineScope()
        val vitamin = remember { mutableLongStateOf(0L) }
        val med = remember { mutableFloatStateOf(0F) }

        val timer = Runnable {
            if (running.value) {
                time.longValue++
                coroutineScope.launch {
                    while (running.value) {
                        delay(10L)
                        time.longValue += 11
                        vitamin.longValue = Math.round(
                            calcDosage(
                                tone = choose,
                                gender = values[0].toInt(),
                                time = time.longValue / 1000F,
                                age = values[1].toInt()
                            )
                        )

                    }
                }
            }
        }

        Box (
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFFFFD66E))
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 5.dp, top = 16.dp)
                ) {
                    Text(
                        fontSize = 30.sp,
                        text = "Мин. MED - ${"%.2f".format(med.floatValue)} мин."
                    )
                    Text(
                        fontSize = 30.sp,
                        text = "Время - ${formatTime(time.longValue)}" +
                            "- ${lightValue.floatValue}"
                    )
                    Text(
                        fontSize = 30.sp,
                        text = "Вит. D - ${vitamin.longValue} нг/мл"
                    )
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    CircleButton({
                        sensorManager.registerListener(
                            object : SensorEventListener {
                                override fun onSensorChanged(event: SensorEvent?) {
                                    if (event != null && event.sensor.type == Sensor.TYPE_LIGHT) {
                                        lightValue.floatValue = event.values[0]
                                    }
                                }
                                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
                            },
                            lightSensor,
                            SensorManager.SENSOR_DELAY_NORMAL
                        )
                        med.floatValue = calcMed(choose, lightValue.floatValue)
                        Log.d(
                            "MED",
                            "$choose // ${lightValue.floatValue}"
                        )
                        running.value = !running.value
                        if (running.value) {
                            timer.run()
                        }
                    }) {
                        Text(
                            text = if (running.value) "Stop" else "Start"
                        )
                    }
                    CircleButton(
                        { time.longValue = 0; vitamin.longValue = 0; med.floatValue = 0F }
                    ) {
                        Text(
                            text = "Reset"
                        )
                    }
                }
            }
        }
    }

    private fun formatTime(time: Long): String {
        val seconds = time / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)
    }

// PROFILE SCREEN

    @Composable
    fun ProfileScreen() {

    }
}
