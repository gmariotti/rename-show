import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.List;

public class Terminal {
	private static final String title = "ShowRename";
	private static final String helpShort = "h";
	private static final String dirShort = "d";
	private static final String showShort = "sh";
	private static final String seasonShort = "se";
	private static final String keywordShort = "k";

	private CommandLine cmd;

	private Terminal(CommandLine cmd) {
		this.cmd = cmd;
	}

	public static Terminal newInstance(String... args) {
		Options options = getOptions();
		CommandLine cmd = getCommandLine(options, args);
		if (cmd.hasOption(helpShort)) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(title, options);
			System.exit(0);
		} else if (!isMandatoryPresent(cmd)) {
			errorMandatoryAbsent();
		}
		return new Terminal(cmd);
	}

	private static Options getOptions() {
		Options options = new Options();

		// create each single option
		Option helpOpt = new Option(helpShort, "help", false, "Print help");
		Option showOpt = Option.builder(showShort)
		                       .longOpt("show")
		                       .argName("SHOW")
		                       .desc("Name of the show to search.")
		                       .hasArg()
		                       .build();
		Option dirOpt = Option.builder(dirShort)
		                      .longOpt("dir")
		                      .argName("DIRECTORY")
		                      .desc("Directory containing the file to rename.")
		                      .hasArg()
		                      .build();
		Option seasonOpt = Option.builder(seasonShort)
		                         .longOpt("season")
		                         .argName("NUM")
		                         .desc("Number of season.")
		                         .hasArg()
		                         .type(Integer.class)
		                         .build();
		Option keywordsOpt = Option.builder(keywordShort)
		                           .longOpt("keywords")
		                           .argName("KEYWORDS")
		                           .desc("List of keywords for helping search.")
		                           .hasArgs()
		                           .build();
		Arrays.asList(helpOpt, showOpt, dirOpt, seasonOpt, keywordsOpt).forEach(options::addOption);
		return options;
	}

	private static CommandLine getCommandLine(Options options, String... args) {
		try {
			return new DefaultParser().parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	private static boolean isMandatoryPresent(CommandLine cmd) {
		return cmd.hasOption("dir") && cmd.hasOption("show")
				&& cmd.hasOption("season") && cmd.hasOption("k");
	}

	private static void errorMandatoryAbsent() {
		System.out.println("Missing one or more of mandatory arguments [dir, show, season, keywords]");
		System.exit(1);
	}

	public String getDirectory() {
		return cmd.getOptionValue(dirShort);
	}

	public String getShow() {
		return cmd.getOptionValue(showShort);
	}

	public int getSeason() {
		int season = Integer.parseInt(cmd.getOptionValue(seasonShort));
		return season > 0 ? season : 1;
	}

	public List<String> getKeywords() {
		return Arrays.asList(cmd.getOptionValues(keywordShort));
	}
}
