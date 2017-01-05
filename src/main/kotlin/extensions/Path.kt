@file:JvmName("PathUtilities")

package extensions

import java.nio.file.Path

fun Path.isKeywordPresent(keywords: List<String>): Boolean {
	val filename = this.toString()
	return keywords.indexOfFirst { filename.contains(it) } != -1
}

fun Path.toUpperCaseString(): String = this.toString().toUpperCase()