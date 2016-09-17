package utilities;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class FilesUtilities {
	public static Optional<Stream<Path>> getFilesInDirectory(Path directory) {
		try {
			return Optional.of(Files.list(directory));
		} catch (IOException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public static void moveFile(@NotNull Path source, @NotNull Path dest, CopyOption... options) {
		try {
			Files.move(source, dest, options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getFileExtension(@NotNull String filename) {
		if (filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0)
			return filename.substring(filename.lastIndexOf(".") + 1);
		else return "";
	}

	public static String getFilename(@NotNull String filename, @NotNull String extension) {
		return filename + "." + extension;
	}
}
