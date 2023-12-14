

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fr.yofardev.templatecompose.ui.theme.PurpleBlue
import fr.yofardev.templatecompose.viewmodels.PublicationViewModel

@Composable
fun AnimatedFab(publicationViewModel: PublicationViewModel) {
    val transition = updateTransition(targetState = publicationViewModel.isFabExploded, label = "FABTransition")
    val fabSize by transition.animateDp(label = "FABSize") { exploded ->
        if (exploded.value) 4000.dp else 56.dp
    }
    val fabColor by animateColorAsState(
        targetValue = if (publicationViewModel.isFabExploded.value) Color.White else PurpleBlue,
        label = "animatedFab"
    )

    FloatingActionButton(
        onClick = {    publicationViewModel.displayAddPublicationScreen() },
        containerColor = fabColor,
        elevation = FloatingActionButtonDefaults.elevation(0.dp),
        modifier = Modifier.size(fabSize)
           .animateContentSize()



            //.align(Alignment.BottomCenter).padding(bottom = 56.dp).animateContentSize()
    ) {
       Icon(
            Icons.Rounded.Add,
            contentDescription = "Add",
            tint = Color.White,
            modifier = Modifier.size(42.dp)
        )
    }
}
