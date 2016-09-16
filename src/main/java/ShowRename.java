import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
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


public class ShowRename {

	public static void main(String... args) {
		// TODO - remove from here
		Options options = new Options();
		List<Option> listOfOptions = getOptions();
		listOfOptions.forEach(options::addOption);
		CommandLineParser parser = new DefaultParser();
		// TODO change with optional
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		if (cmd.hasOption("h")) {
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.printHelp("show-rename", options);
			System.exit(0);
		} else if (!isMandatoryPresent(cmd)){
			errorMandatoryAbsent();
		}

		final String dirPath = cmd.getOptionValue("dir");
		Path directory = Paths.get(dirPath); // throw exception if not a valid path
		final String showName = cmd.getOptionValue("show");
		final int seasonNum = Integer.parseInt(cmd.getOptionValue("season"));
		final List<String> keywords = Arrays.asList(cmd.getOptionValues("k"));
		System.out.println(dirPath + " " + showName + " " + seasonNum);
		System.out.println(keywords);

		// TODO debug
		System.exit(0);

		// TODO must go to terminal
		final String fileFormat = "SxxExx";
		final String seasonStr = "S" + (seasonNum > 9 ? String.valueOf(seasonNum) : "0" + seasonNum);

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://api.tvmaze.com")
				//.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		TvMaze tvMaze = retrofit.create(TvMaze.class);
		List<TvMazeSearch> shows = TvMazeManager.getTvShowFromName(tvMaze, showName)
		                                        .orElse(Collections.emptyList());
		Map<String, Integer> showsFound = shows.stream()
		                                       .map(TvMazeSearch::getShow)
		                                       .collect(Collectors.toMap(Show::getName, Show::getId));
		int showID = 0;
		if (showsFound.size() > 1) {
			// TODO allow user selection of the show in the list
			showsFound.forEach((key, id) -> System.out.println(id + " - " + key));
		} else if (showsFound.size() == 1) {
			showID = showsFound.get(showsFound.keySet().toArray()[0]);
		} else {
			System.out.println(showName + " not found!");
			System.exit(-1);
		}

		System.out.println("ID for " + showName + " is " + showID);

		Function<Episode, String> episodeFormatter = EpisodeFormat.createFormatter(fileFormat);
		Map<Integer, String> episodes =
				TvMazeManager.getEpisodes(tvMaze, showID)
				             .orElse(Collections.emptyList())
				             .stream()
				             .filter(tvMazeEpisode -> tvMazeEpisode.getSeason() == seasonNum)
				             .map(episode -> Episode.createEpisode(episode, showName))
				             .collect(Collectors.toMap(Episode::getNumber, episodeFormatter));

		if (episodes.size() == 0) {
			System.out.println("The list of episodes for " + showName + " is empty.");
			System.exit(1);
		}

		Map<Integer, List<Path>> filenames =
				FilesUtilities.getFilesInDirectory(directory)
				              .orElse(Stream.empty())
				              .filter(file -> TvMazeManager.searchForKeyword(file.getFileName(), keywords))
				              .filter(file -> TvMazeManager.isOfSeason(file.getFileName(), seasonStr))
				              .collect(Collectors.groupingBy(ShowRename::getEpisodeNumber));

		filenames.forEach(
				(key, list) -> list.stream()
				                   .map(path -> Tuple.of(path, path.getFileName().toString()))
				                   .map(tuple -> Tuple.of(tuple._1(),
						                   getFilename(episodes.get(key), getFileExtension(tuple._2()))))
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

	private static List<Option> getOptions() {
		Option helpOpt = new Option("h", "help", false,"Print help");
		Option showOpt = Option.builder("sh")
		                       .longOpt("show")
		                       .argName("SHOW")
		                       .desc("TODO")
		                       .hasArg()
		                       .build();
		Option dirOpt = Option.builder("d")
		                      .longOpt("dir")
		                      .argName("DIRECTORY")
		                      .desc("TODO")
		                      .hasArg()
		                      .build();
		Option seasonOpt = Option.builder("se")
		                         .longOpt("season")
		                         .argName("NUM")
		                         .desc("TODO")
		                         .hasArg()
		                         .type(Integer.class)
		                         .build();
		Option keywordsOpt = Option.builder("k")
		                           .longOpt("keywords")
		                           .argName("KEYWORD")
		                           .desc("TODO")
		                           .hasArgs()
		                           .build();
		return Arrays.asList(helpOpt, showOpt, dirOpt, seasonOpt, keywordsOpt);
	}

	private static boolean isMandatoryPresent(CommandLine cmd) {
		return cmd.hasOption("dir") && cmd.hasOption("show")
				&& cmd.hasOption("season") && cmd.hasOption("k");
	}

	private static void errorMandatoryAbsent() {
		System.out.println("Missing one or more of mandatory arguments [dir, show, season, keywords]");
		System.exit(1);
	}

	private static Integer getEpisodeNumber(Path path) {
		// TODO hardcoded, must be removed
		List<String> episodes = Arrays.asList("E01", "E02", "E03", "E04", "E05", "E06", "E07", "E08",
				"E09", "E10", "E11", "E12", "E13", "E14", "E15", "E16", "E17", "E18", "E19", "E20", "E21");
		for (String episode : episodes) {
			if (path.getFileName().toString().contains(episode)) {
				return episodes.indexOf(episode) + 1;
			}
		}
		return -1;
	}

	private static String getFileExtension(String filename) {
		if (filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0)
			return filename.substring(filename.lastIndexOf(".") + 1);
		else return "";
	}

	private static String getFilename(String filename, String extension) {
		return filename + "." + extension;
	}

}
