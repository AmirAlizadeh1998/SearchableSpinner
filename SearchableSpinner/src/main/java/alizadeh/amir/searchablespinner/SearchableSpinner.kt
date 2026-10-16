package alizadeh.amir.searchablespinner

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Immutable
data class SpinnerColors(
    val containerColor: Color,
    val textColor: Color,
    val labelColor: Color,
    val iconColor: Color
)

object SpinnerDefaults {
    @Composable
    fun colors(
        containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        labelColor: Color = MaterialTheme.colorScheme.outline,
        iconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
    ): SpinnerColors = SpinnerColors(
        containerColor = containerColor,
        textColor = textColor,
        labelColor = labelColor,
        iconColor = iconColor
    )
}

@Composable
fun <T> SearchableSpinner(
    modifier: Modifier = Modifier,
    items: List<T>,
    selectedItem: T?,
    itemText: (T) -> String,
    label: String,
    isSearchable: Boolean = false,
    placeholderSearch: String = "جستجو...",
    leadingIcon: (@Composable () -> Unit)? = null,
    colors: SpinnerColors = SpinnerDefaults.colors(),
    onItemSelected: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "arrowRotation"
    )

    val filteredItems = remember(searchText, items) {
        if (!isSearchable || searchText.isEmpty()) items
        else items.filter { itemText(it).contains(searchText, ignoreCase = true) }
    }

    val hasSelection = selectedItem != null
    val labelFraction by animateFloatAsState(
        targetValue = if (expanded || hasSelection) 1f else 0f,
        label = "labelFraction"
    )

    BoxWithConstraints(modifier = modifier) {
        val maxWidth = maxWidth

        OutlinedCard(
            colors = CardDefaults.cardColors(containerColor = colors.containerColor),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    text = label,
                    color = if (expanded) colors.textColor else colors.labelColor,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.graphicsLayer {
                        translationY = (1f - labelFraction) * 10f
                        scaleX = 0.85f + 0.15f * labelFraction
                        scaleY = 0.85f + 0.15f * labelFraction
                        alpha = labelFraction
                    }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = if (labelFraction > 0.5f) 18.dp else 0.dp)
                ) {
                    if (leadingIcon != null) {
                        leadingIcon()
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Text(
                        text = selectedItem?.let { itemText(it) }
                            ?: if (labelFraction < 0.5f) label else "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (selectedItem != null) colors.textColor else colors.labelColor,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        painter = rememberVectorPainter(Icons.Default.ArrowDropDown),
                        contentDescription = null,
                        tint = colors.iconColor,
                        modifier = Modifier
                            .size(24.dp)
                            .graphicsLayer { rotationZ = arrowRotation }
                    )
                }
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                searchText = ""
            },
            modifier = Modifier.width(maxWidth),
            offset = DpOffset(x = 0.dp, y = 4.dp)
        ) {
            if (isSearchable) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    placeholder = { Text(placeholderSearch) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            painter = rememberVectorPainter(Icons.Default.Search),
                            contentDescription = null
                        )
                    }
                )
            }

            Column(
                modifier = Modifier
                    .heightIn(max = 300.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                filteredItems.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = itemText(item),
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        onClick = {
                            onItemSelected(item)
                            expanded = false
                            searchText = ""
                        }
                    )
                }
            }
        }
    }
}