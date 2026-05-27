package com.iqbalwork.ramadhancamp.feature.bookmark.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.model.Category
import com.iqbalwork.ramadhancamp.feature.bookmark.domain.repository.BookmarkRepository
import com.iqbalwork.ramadhancamp.shared.common.navigation.LocalBackStackNode
import com.iqbalwork.ramadhancamp.shared.common.navigation.LocalCurrentTab
import com.iqbalwork.ramadhancamp.shared.common.navigation.NavigationManager
import com.iqbalwork.ramadhancamp.shared.common.ui.components.button.RamadhanButton
import com.iqbalwork.ramadhancamp.shared.common.ui.components.button.RamadhanButtonProps
import com.iqbalwork.ramadhancamp.shared.common.ui.theme.RamadhanTheme
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun BookmarkCreateCategorySheet() {
    val colors = RamadhanTheme.colors
    val typography = RamadhanTheme.typography

    val node = LocalBackStackNode.current
    val tabState = LocalCurrentTab.current
    val navigationManager: NavigationManager = koinInject(parameters = { parametersOf(node, tabState) })
    val bookmarkRepository: BookmarkRepository = koinInject()

    var categoryName by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = "Create Category",
            style = typography.headlineSmall,
            color = colors.textPrimary
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Category name", color = colors.textMuted) },
            textStyle = typography.bodyLarge.copy(color = colors.textPrimary),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.accentPrimary,
                unfocusedBorderColor = colors.divider,
                cursorColor = colors.accentPrimary
            ),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        RamadhanButton(
            onClick = {
                val name = categoryName.trim()
                if (name.isNotEmpty()) {
                    scope.launch {
                        bookmarkRepository.addCategory(Category(id = 0, name = name))
                        navigationManager.back()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            variant = RamadhanButtonProps.Variant.Primary,
            text = "Save",
            enabled = categoryName.trim().isNotEmpty()
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}
