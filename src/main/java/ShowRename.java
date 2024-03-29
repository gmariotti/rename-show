import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import domain.Episode;
import domain.EpisodeFormat;
import extensions.FilesUtilities;
import extensions.PathUtilities;
import extensions.TvMazeUtilities;
import javaslang.Tuple;
import javaslang.Tuple2;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import terminal.TerminalInputs;
import terminal.TerminalKt;
import tvmaze.TvMaze;
import tvmaze.pojo.TvMazeSearch;
import tvmaze.pojo.search.Show;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;


public class ShowRename {

	public static void main(String... args) {
		final TerminalInputs terminal = TerminalKt.parseTerminal(args);
		final Path directory = terminal.getDir();
		final String showName = terminal.getShow();
		final int seasonNum = terminal.getSeason();
		final List<String> keywords = terminal.getKeywords();
		System.out.println("Working directory is " + directory);
		System.out.println("Show = " + showName);
		System.out.println("Season = " + seasonNum);
		System.out.println("List of keyword to search for is " +
				keywords.stream().collect(joining(", "))
		);

		// TODO must go to terminal
		final String fileFormat = "SxxExx";
		final String seasonStr = "S" + (seasonNum > 9 ? String.valueOf(seasonNum) : "0" + seasonNum);

		CompletableFuture<Map<Integer, String>> requestEpisodes = CompletableFuture.supplyAsync(() -> {
			final Retrofit retrofit = new Retrofit.Builder()
					.baseUrl("http://api.tvmaze.com")
					.addConverterFactory(GsonConverterFactory.create())
					.build();
			Optional<Tuple2<String, Integer>> show = getShow(retrofit, showName);

			Map<Integer, String> episodes = Collections.emptyMap();
			if (show.isPresent()) {
				episodes = getEpisodesMap(retrofit, show.get()._2(), show.get()._1(), seasonNum, fileFormat);
			}
			return episodes;
		});


		Map<Integer, List<Path>> filenames = FilesUtilities
				.getStreamOfFiles(directory).orElse(Stream.empty())
				.filter(file -> PathUtilities.isKeywordPresent(file.getFileName(), keywords))
//				.filter(file -> PathUtilities.toUpperCaseString(file.getFileName()).contains(seasonStr))
				.filter(file -> {
					String name = PathUtilities.toUpperCaseString(file.getFileName());
					List<String> possibilities = new ArrayList<>();
					possibilities.add("S" + (seasonNum > 9 ? String.valueOf(seasonNum) : "0" + seasonNum));
					possibilities.add("[" + seasonNum);
					for (String possibility : possibilities) {
						if (name.contains(possibility)) {
							return true;
						}
					}
					return false;
				})
				.collect(groupingBy(ShowRename::getEpisodeNumber));

		try {
			final Map<Integer, String> episodes = requestEpisodes.get(30, TimeUnit.SECONDS);
			filenames.forEach((key, list) ->
					list.stream()
							.map(path -> Tuple.of(path, path.getFileName().toString()))
							.map(tuple -> Tuple.of(tuple._1(),
									FilesUtilities.addExtension(episodes.get(key),
											FilesUtilities.getExtension(tuple._2()))))
							.map(tuple -> Tuple.of(tuple._1(), Paths.get(directory.toString(), tuple._2())))
							.forEach(tuple -> FilesUtilities.moveFile(tuple._1(), tuple._2()))
			);

			// TODO debug
			System.out.println("===============");
			episodes.forEach((key, episode) -> System.out.println(key + "\t-->\t" + episode));
			System.out.println("===============");
			filenames.forEach((key, list) -> {
				System.out.print(key + "\t-->\t");
				list.forEach(path -> System.out.print(path.getFileName().toString() + ", "));
				System.out.println();
			});
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
	}

	private static Map<Integer, String> getEpisodesMap(Retrofit retrofit, int showID, String showName, int seasonNum, String fileFormat) {
		System.out.println("ID for " + showName + " is " + showID);

		Function<Episode, String> episodeFormatter = EpisodeFormat.createFormatter(fileFormat);
		Map<Integer, String> mapOfEpisodes =
				TvMazeUtilities.getEpisodes(retrofit.create(TvMaze.class), showID)
						.orElse(Collections.emptyList())
						.stream()
						.filter(tvMazeEpisode -> tvMazeEpisode.getSeason() == seasonNum)
						.map(episode -> Episode.Companion.createEpisode(episode, showName))
						.collect(toMap(Episode::getNumber, episodeFormatter));

		if (mapOfEpisodes.size() == 0) {
			System.out.println("The list of episodes for " + showName + " is empty.");
			return Collections.emptyMap();
		} else {
			return mapOfEpisodes;
		}
	}

	private static Optional<Tuple2<String, Integer>> getShow(Retrofit retrofit, String showName) {
		TvMaze tvMaze = retrofit.create(TvMaze.class);
		List<Show> shows = TvMazeUtilities.getTvShowFromName(tvMaze, showName)
				.orElse(Collections.emptyList())
				.stream()
				.map(TvMazeSearch::getShow)
				.collect(toList());

		Optional<Tuple2<String, Integer>> show = Optional.empty();
		if (shows.size() > 1) {
			System.out.println("Select the correct show in the list");
			IntStream.range(0, shows.size())
					.forEach(i -> System.out.println(
							String.format("(%d) - %s", i, shows.get(i).getName())
					));
			System.out.println("\nInsert show number");
			boolean isOk = false;
			String selection = null;
			while (!isOk) {
				try {
					selection = new BufferedReader(new InputStreamReader(System.in)).readLine();
					int index = Integer.parseInt(selection);
					if (0 <= index && index < shows.size()) {
						String newShowName = shows.get(index).getName();
						int showID = shows.get(index).getId();
						show = Optional.of(Tuple.of(newShowName, showID));
						isOk = true;
					} else {
						System.out.println("Not valid as option. Try again.");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (shows.size() == 1) {
			Integer showID = shows.get(0).getId();
			show = Optional.of(Tuple.of(showName, showID));
		} else {
			System.out.println(showName + " not found!");
		}

		return show;
	}

	private static Integer getEpisodeNumber(@NotNull Path path) {
		Pattern pattern1 = Pattern.compile("(e|E)\\d+");
		Pattern pattern2 = Pattern.compile("(\\.)\\d+(])");
		List<Pattern> patterns = new ArrayList<>();
		patterns.add(pattern1);
		patterns.add(pattern2);
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(path.getFileName().toString());
			if (matcher.find()) {
				return Integer.parseInt(matcher.group(0).replaceAll("[^\\d]",""));
			}
		}
		return -1;
	}
}
