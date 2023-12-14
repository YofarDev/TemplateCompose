package fr.yofardev.templatecompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.yofardev.templatecompose.R
import fr.yofardev.templatecompose.ui.theme.BlueYofardev
import fr.yofardev.templatecompose.ui.theme.rubik
import fr.yofardev.templatecompose.viewmodels.UserViewModel

@Composable
fun AppDrawer(userViewModel: UserViewModel) {
    ModalDrawerSheet(drawerContainerColor = Color.White) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(BlueYofardev), contentAlignment = Alignment.Center) {

                Box(Modifier.padding(16.dp)) {
                    AppLogo(size = 120)
                }

            }
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.weight(1f))
            Text(
                userViewModel.currentUser.value!!.email,
                fontFamily = rubik,
                color = Color.Black.copy(alpha = 0.6f),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { userViewModel.signOut() }) {
                Text(stringResource(id = R.string.sign_out))
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

    }

}

