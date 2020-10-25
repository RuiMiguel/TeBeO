package com.ruiskas.tebeo.ui.base.extensions

import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.*
import android.view.View
import androidx.annotation.ColorInt

private sealed class TextSpan<T> {
    abstract fun create(): T

    class ColorSpan(@ColorInt val color: Int) : TextSpan<ForegroundColorSpan>() {
        override fun create(): ForegroundColorSpan {
            return ForegroundColorSpan(color)
        }
    }

    class BoldSpan(val style: Int) : TextSpan<StyleSpan>() {
        override fun create(): StyleSpan {
            return StyleSpan(style)
        }
    }

    class LinkedSpan(val block: () -> Unit, val underline: Boolean) : TextSpan<ClickableSpan>() {
        override fun create(): ClickableSpan {
            return object : ClickableSpan() {
                override fun onClick(widget: View) {
                    block.invoke()
                }
            }
        }
    }

    class BackgroundSpan(@ColorInt val color: Int) : TextSpan<BackgroundColorSpan>() {
        override fun create(): BackgroundColorSpan {
            return BackgroundColorSpan(color)
        }
    }

    class SizeSpan(val size: Float) : TextSpan<RelativeSizeSpan>() {
        override fun create(): RelativeSizeSpan {
            return RelativeSizeSpan(size)
        }
    }

    object StrikeSpan : TextSpan<StrikethroughSpan>() {
        override fun create(): StrikethroughSpan {
            return StrikethroughSpan()
        }
    }
}

@JvmOverloads
fun CharSequence.foregroundColor(
    @ColorInt color: Int, replaceText: String = "",
    all: Boolean = false
) =
    applySpan(TextSpan.ColorSpan(color), replaceText, all, "C")

@JvmOverloads
fun CharSequence.bold(replaceText: String = "", all: Boolean = false) =
    applySpan(TextSpan.BoldSpan(Typeface.BOLD), replaceText, all, "B")

@JvmOverloads
fun CharSequence.italic(replaceText: String = "", all: Boolean = false) =
    applySpan(TextSpan.BoldSpan(Typeface.ITALIC), replaceText, all, "I")

@JvmOverloads
fun CharSequence.backgroundColor(
    replaceText: String = "", @ColorInt color: Int,
    all: Boolean = false
) =
    applySpan(TextSpan.BackgroundSpan(color), replaceText, all, "BC")

@JvmOverloads
fun CharSequence.relativeSize(replaceText: String = "", size: Float, all: Boolean = false) =
    applySpan(TextSpan.SizeSpan(size), replaceText, all, "S")

@JvmOverloads
fun CharSequence.strike(replaceText: String = "", all: Boolean = false) =
    applySpan(TextSpan.StrikeSpan, replaceText, all, "ST")

@JvmOverloads
fun CharSequence.linked(
    replaceText: String = "",
    block: () -> Unit,
    underline: Boolean = false,
    all: Boolean = false
) =
    applySpan(TextSpan.LinkedSpan(block, underline), replaceText, all, "L")

private fun CharSequence.toSpannable() = SpannableStringBuilder(this)

private fun CharSequence.applySpan(
    span: TextSpan<*>,
    replaceText: String = "",
    all: Boolean = false,
    pattern: String
): SpannableStringBuilder {
    val spannable = toSpannable()
    return when (all) {
        true -> {
            spannable.setSpan(span.create(), 0, length, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
            spannable
        }
        false -> {
            when {
                replaceText.isEmpty() -> {
                    spannable.applySpanToPattern(span, pattern)
                }
                else -> {
                    spannable.applySpanToText(span, replaceText)
                }
            }
        }
    }
}

private fun SpannableStringBuilder.applySpanToText(
    span: TextSpan<*>,
    replaceText: String
): SpannableStringBuilder {
    replaceText.toRegex().findAll(this).forEach {
        val start = it.range.first
        val end = it.range.last + 1

        setSpan(span.create(), start, end, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
    }

    return this
}

private fun SpannableStringBuilder.applySpanToPattern(
    span: TextSpan<*>,
    pattern: String = ""
): SpannableStringBuilder {
    val delimiter = '#'
    val startPattern = "$delimiter$pattern$delimiter"
    val endPattern = "$delimiter/$pattern$delimiter"
    val spanPattern = "$startPattern(.[^:$delimiter:]*)$endPattern"

    spanPattern.toRegex().findAll(this).forEach {
        val start = it.range.first
        val end = it.range.last + 1

        setSpan(span.create(), start, end, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
    }
    cleanPattern(startPattern, endPattern)

    return this
}

operator fun SpannableStringBuilder.set(
    old: CharSequence,
    new: SpannableStringBuilder
): SpannableStringBuilder {
    var index = indexOf(old.toString())
    while (index > -1) {
        replace(index, index + old.length, new, 0, new.length)
        index = indexOf(old.toString())
    }
    return this
}

private fun SpannableStringBuilder.cleanPattern(startPattern: String, endPattern: String) =
    set(startPattern, "".toSpannable()).set(endPattern, "".toSpannable())
