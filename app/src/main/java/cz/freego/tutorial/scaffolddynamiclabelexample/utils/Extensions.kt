package cz.freego.tutorial.scaffolddynamiclabelexample.utils

fun String.baseRoute(): String = this.substringBefore("/")

fun String.emojiCodeToEscapeSequence() = String(Character.toChars(this.toInt(16)))
