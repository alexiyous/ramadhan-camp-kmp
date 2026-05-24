package com.iqbalwork.ramadhancamp.shared.common.navigation

import androidx.navigation3.runtime.NavKey
import io.github.aakira.napier.log
import kotlinx.coroutines.flow.SharedFlow

class NavigationManagerImpl(
    private val resultRepository: ResultNavigationRepository,
    private var currentNode: BackStackNode,
    private var _tabState: TabState
) : NavigationManager {

    internal fun update(node: BackStackNode, tabState: TabState) {
        this.currentNode = node
        this._tabState   = tabState
    }

    override fun navigateTo(dest: NavKey, withReplace: Boolean) {
        val stack = currentNode.backStack
        if (withReplace) stack[stack.lastIndex] = dest else stack.add(dest)
    }

    override fun navigateToInsideTab(dest: NavKey, withReplace: Boolean) {
        val stack = currentNode.nodeAtLevel(TAB_LEVEL)?.backStack ?: return
        if (withReplace) stack[stack.lastIndex] = dest else stack.add(dest)
    }

    override fun startNewFlow(dest: NavKey) {
        val stack = currentNode.nodeAtLevel(ROOT_LEVEL)?.backStack ?: return
        stack.clear()
        stack.add(dest)
    }

    override fun back(navigationResult: NavigationResult?) {
        val node = currentNode
        navigationResult?.let {
            log(tag = "CACHE") { "Back with result: ${it.key}" }
            sendResult(it)
        }
        if (node.backStack.size > 1) {
            node.backStack.removeLast()
        } else {
            node.parent?.backStack?.removeLastOrNull()
        }
    }

    override fun backToScreen(key: NavKey, navigationResult: NavigationResult?) {
        val node = currentNode
        navigationResult?.let { sendResult(it) }
        val stack = node.backStack
        while (stack.size > 1 && stack.last() != key) {
            stack.removeLastOrNull()
        }
    }

    override fun switchTab(tab: FeatureTab) {
        _tabState.select(tab)
    }

    override fun showDialog(dialog: DialogDestination) {
        currentNode.nodeAtLevel(TAB_LEVEL)?.backStack?.add(dialog)
    }

    override fun hideDialog() {
        val stack = currentNode.nodeAtLevel(TAB_LEVEL)?.backStack ?: return
        if (stack.lastOrNull() is DialogDestination) stack.removeLastOrNull()
    }

    override fun sendResult(value: NavigationResult) {
        resultRepository.sendResult(value.key, value)
    }

    override fun subscribeToResult(key: String): SharedFlow<NavigationResult> {
        return resultRepository.getResultFlow(key)
    }

    override fun releaseKey(key: String) {
        resultRepository.releaseKey(key)
    }
}

