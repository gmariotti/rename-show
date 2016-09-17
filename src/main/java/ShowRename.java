import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import domain.Episode;
import domain.EpisodeFormat;
import javaslang.Tuple;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tvmaze.TvMaze;
import tvmaze.TvMazeManager;
import tvmaze.pojo.TvMazeSearch;
import tvmaze.pojo.search.Show;
import utilities.FilesUtilities;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;


public class ShowRename {

	public static void main(String... args) {
		Terminal terminal = Terminal.newInstance(args);

		final String dirPath = terminal.getDirectory();
		Path directory = Paths.get(dirPath); // throw exception if not a valid path
		String showName = terminal.getShow();
		final int seasonNum = terminal.getSeason();
		final List<String> keywords = terminal.getKeywords();
		System.out.println("Working directory is " + dirPath);
		System.out.println("Show = " + showName);
		System.out.println("Season = " + seasonNum);
		System.out.println("List of keyword to search for is " +
				keywords.stream()
				        .collect(joining(", "))
		);

		// TODO must go to terminal
		final String fileFormat = "SxxExx";
		final String seasonStr = "S" + (seasonNum > 9 ? String.valueOf(seasonNum) : "0" + seasonNum);

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://api.tvmaze.com")
				//.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		TvMaze tvMaze = retrofit.create(TvMaze.class);
		List<Show> shows = TvMazeManager.getTvShowFromName(tvMaze, showName)
		                                .orElse(Collections.emptyList())
		                                .stream()
		                                .map(TvMazeSearch::getShow)
		                                .collect(toList());
		int showID = 0;
		if (shows.size() > 1) {
			System.out.println("Select the correct show in the list");
			IntStream.range(0, shows.size())
			         .forEach(i -> System.out.println(
					         String.format("(%d) - %s", i, shows.get(i).getName())
			         ));
			System.out.println();
			boolean isOk = false;
			String selection = null;
			while (!isOk) {
				try {
					selection = new BufferedReader(new InputStreamReader(System.in)).readLine();
					int index = Integer.parseInt(selection);
					if (0 <= index && index < shows.size()) {
						showID = shows.get(index).getId();
						showName = shows.get(index).getName();
						isOk = true;
					} else {
						System.out.println("Not valid as option");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (shows.size() == 1) {
			showID = shows.get(0).getId();
		} else {
			System.out.println(showName + " not found!");
			System.exit(-1);
		}

		System.out.println("ID for " + showName + " is " + showID);

		final String showNameFinal = showName;
		Function<Episode, String> episodeFormatter = EpisodeFormat.createFormatter(fileFormat);
		Map<Integer, String> episodes =
				TvMazeManager.getEpisodes(tvMaze, showID)
				             .orElse(Collections.emptyList())
				             .stream()
				             .filter(tvMazeEpisode -> tvMazeEpisode.getSeason() == seasonNum)
				             .map(episode -> Episode.createEpisode(episode, showNameFinal))
				             .collect(toMap(Episode::getNumber, episodeFormatter));

		if (episodes.size() == 0) {
			System.out.println("The list of episodes for " + showName + " is empty.");
			System.exit(1);
		}

		Map<Integer, List<Path>> filenames =
				FilesUtilities.getFilesInDirectory(directory)
				              .orElse(Stream.empty())
				              .filter(file -> TvMazeManager.searchForKeyword(file.getFileName(), keywords))
				              .filter(file -> TvMazeManager.isOfSeason(file.getFileName(), seasonStr))
				              .collect(groupingBy(ShowRename::getEpisodeNumber));

		filenames.forEach(
				(key, list) -> list.stream()
				                   .map(path -> Tuple.of(path, path.getFileName().toString()))
				                   .map(tuple -> Tuple.of(tuple._1(),
						                   FilesUtilities.getFilename(episodes.get(key), FilesUtilities.getFileExtension(tuple
								                   ._2()))))
				                   .map(tuple -> Tuple.of(tuple._1(), Paths.get(dirPath, tuple._2())))
				                   .forEach(tuple -> FilesUtilities.moveFile(tuple._1(), tuple._2())));

		// TODO debug
		System.out.println("===============");
		episodes.forEach((key, episode) -> System.out.println(key + "\t-->\t" + episode));
		System.out.println("===============");
		filenames.forEach((key, list) -> {
			System.out.print(key + "\t-->\t");
			list.forEach(path -> System.out.print(path.getFileName().toString() + ", "));
			System.out.println();
		});
	}

	private static Integer getEpisodeNumber(Path path) {
		// TODO hardcoded, must be removed
		List<String> episodes = Arrays.asList("E01", "E02", "E03", "E04", "E05", "E06", "E07", "E08",
				"E09", "E10", "E11", "E12", "E13", "E14", "E15", "E16", "E17", "E18", "E19", "E20", "E21");
		for (String episode : episodes) {
			if (path.getFileName().toString().toUpperCase().contains(episode)) {
				return episodes.indexOf(episode) + 1;
			}
		}
		return -1;
	}

}
