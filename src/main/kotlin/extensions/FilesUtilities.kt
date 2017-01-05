@file:JvmName("FilesUtilities")

package extensions

import java.io.IOException
import java.nio.file.CopyOption
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.stream.Stream

fun Path.getStreamOfFiles(): Optional<Stream<Path>> {
	try {
		return Optional.of(Files.list(this))
	} catch (e: IOException) {
		e.printStackTrace()
		return Optional.empty()
	}
}

fun Path.moveFile(dest: Path, vararg options: CopyOption) {
	try {
		Files.move(this, dest, *options)
	} catch (e: IOException) {
		e.printStackTrace()
	}
}

fun String.addExtension(extension: String): String = this + "." + extension

fun getExtension(filename: String): String {
	if (filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0)
		return filename.substring(filename.lastIndexOf(".") + 1)
	else
		return ""
}
