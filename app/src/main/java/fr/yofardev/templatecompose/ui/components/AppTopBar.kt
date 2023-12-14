package fr.yofardev.templatecompose.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.yofardev.templatecompose.ui.theme.BlueYofardev
import fr.yofardev.templatecompose.ui.theme.rubik
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AppTopBar(
    scope: CoroutineScope? = null,
    drawerState: DrawerState? = null,
    title:String = "",
    backArrow: Boolean = false,
    onBackArrowClicked: () -> Unit = {}
) {
    TopAppBar(
        backgroundColor = BlueYofardev,
        contentColor = Color.White,
        elevation = 12.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),

        ) {
        Row(
            modifier = Modifier
              
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,

        ) {
            if (backArrow)
                IconButton(onClick = { onBackArrowClicked() }) {
                    Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint= Color.White)
                }
            else IconButton(onClick = {
                scope?.launch {
                    drawerState?.apply {
                        if (isClosed ) open() else close()
                    }
                }
            }) {
                Icon(
                    Icons.Rounded.Menu,
                    modifier = Modifier.size(38.dp),
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                if (title.isEmpty()) "YOFARDEV" else title,
                color = Color.White,
                fontFamily = rubik,
                fontWeight = FontWeight.Bold,
                fontSize = if (title.isEmpty()) 24.sp else 18.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            AppLogo(size = 42)

        }
    }
}