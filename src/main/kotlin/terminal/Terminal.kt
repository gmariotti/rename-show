package terminal

import extensions.checkMandatory
import extensions.ifHelp
import extensions.parseTerminalOptions
import org.apache.commons.cli.*
import java.nio.file.Path
import java.util.*

data class TerminalInputs(val dir: Path, val show: String, val season: Int, val keywords: List<String>)

enum class TerminalOption(val argName: String, val shortName: String, val longName: String) {
	SHOW("SHOW", "sh", "show"), DIR("DIRECTORY", "d", "directory"),
	SEASON("SEASON", "se", "season"), KEYWORDS("KEYWORDS", "k", "keywords"),
	HELP("HELP", "h", "help")
}

fun parseTerminal(vararg args: String): TerminalInputs {
	val options = buildOptions()
	val cmd = options.parseCommandLine(*args)
	return cmd.ifHelp(options)
			.checkMandatory()
			.parseTerminalOptions()
}

fun buildOptions(): Options {
	val options = Options()
	val helpOpt = Option(TerminalOption.HELP.shortName, TerminalOption.HELP.longName, false, "Print help.")
	val showOpt = Option.builder(TerminalOption.SHOW.shortName)
			.longOpt(TerminalOption.SHOW.longName)
			.argName(TerminalOption.SHOW.argName)
			.desc("Name of the show to search.")
			.hasArg()
			.build()
	val dirOpt = Option.builder(TerminalOption.DIR.shortName)
			.longOpt(TerminalOption.DIR.longName)
			.argName(TerminalOption.DIR.argName)
			.desc("Directory containing the file to rename.")
			.hasArg()
			.build()
	val seasonOpt = Option.builder(TerminalOption.SEASON.shortName)
			.longOpt(TerminalOption.SEASON.longName)
			.argName(TerminalOption.SEASON.argName)
			.desc("Number of season.")
			.hasArg()
			.type(Int::class.java)
			.build()
	val keywordsOpt = Option.builder(TerminalOption.KEYWORDS.shortName)
			.longOpt(TerminalOption.KEYWORDS.longName)
			.argName(TerminalOption.KEYWORDS.argName)
			.desc("List of keywords for helping search.")
			.hasArgs()
			.build()
	Arrays.asList(helpOpt, showOpt, dirOpt, seasonOpt, keywordsOpt)
			.forEach { options.addOption(it) }
	return options
}

fun Options.parseCommandLine(vararg args: String): CommandLine {
	try {
		return DefaultParser().parse(this, args)
	} catch (e: ParseException) {
		throw RuntimeException(e.message, e.cause)
	}
}

