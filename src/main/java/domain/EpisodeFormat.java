package domain;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class EpisodeFormat {
	public final static List<String> validFormats = Arrays.asList("SxxExx", "sxxexx");

	public static Function<Episode, String> createFormatter(String format) {
		switch (format) {
			case "SxxExx":
				return EpisodeFormat::SxxExxFormatter;
			case "sxxexx":
				return EpisodeFormat::sxxexxFormatter;
			default:
				throw new RuntimeException(format + " is not a valid one");
		}
	}

	private static String SxxExxFormatter(Episode episode) {
		String seasonStr = getZeroFormattedNumber(episode.getSeason());
		String episodeStr = getZeroFormattedNumber(episode.getNumber());
		return episode.getShow() + " - S" + seasonStr + "E" + episodeStr + " - " + episode.getName();
	}

	private static String sxxexxFormatter(Episode episode) {
		String seasonStr = getZeroFormattedNumber(episode.getSeason());
		String episodeStr = getZeroFormattedNumber(episode.getNumber());
		return episode.getShow() + " - s" + seasonStr + "e" + episodeStr + " - " + episode.getName();
	}

	@NotNull
	private static String getZeroFormattedNumber(int number) {
		return number > 9 ? String.valueOf(number) : "0" + number;
	}
}
