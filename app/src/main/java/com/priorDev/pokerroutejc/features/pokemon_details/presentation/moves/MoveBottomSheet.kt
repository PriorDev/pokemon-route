import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.priorDev.pokerroutejc.R
import com.priorDev.pokerroutejc.core.domain.pokemon.models.MoveDetailsData
import com.priorDev.pokerroutejc.core.presentation.components.ItemType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoveBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    move: MoveDetailsData?,
    sheetState: SheetState,
    modifier: Modifier = Modifier
) {
    if (isVisible && move != null) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
        ) {
            Column(
                modifier = modifier.padding(16.dp)
            ) {
                Text(
                    text = move.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                move.type?.let {
                    ItemType(
                        type = it,
                        modifier = Modifier.height(32.dp),
                        elevation = 0.dp
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.Damage, move.damageClass),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        textAlign = TextAlign.Center
                    )

                    if (move.machineNumber.isNotEmpty()) {
                        Text(
                            text = stringResource(id = R.string.machine, move.machineNumber),
                            modifier = Modifier.padding(horizontal = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    if (move.level > 0) {
                        Text(
                            text = stringResource(id = R.string.learned_at, move.level),
                            modifier = Modifier.padding(start = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.power, move.power),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = stringResource(id = R.string.pp, move.pp),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }

                HorizontalDivider(Modifier.padding(vertical = 8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.accuracy, move.accuracy),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = stringResource(id = R.string.priority, move.priority),
                        modifier = Modifier.padding(horizontal = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }

                HorizontalDivider(Modifier.padding(vertical = 8.dp))
            }

            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            ) {
                Text(
                    text = move.effect,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
