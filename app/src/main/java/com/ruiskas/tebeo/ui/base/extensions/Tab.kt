package com.ruiskas.tebeo.ui.base.extensions

import com.google.android.material.tabs.TabLayout

fun TabLayout.increase(inc: Int? = 1) {
    inc?.let { move(inc) }
}

fun TabLayout.decrease(dec: Int? = 1) {
    dec?.let { move(-dec) }
}

private fun TabLayout.move(moves: Int) {
    getTabAt((selectedTabPosition + moves) % (tabCount - 1))?.select()
}