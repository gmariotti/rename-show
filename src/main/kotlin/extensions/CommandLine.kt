package extensions

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import terminal.TerminalInputs
import terminal.TerminalOption.*
import java.nio.file.Paths
import kotlin.system.exitProcess


fun CommandLine.ifHelp(options: Options): CommandLine {
	if (this.hasOption("h")) {
		HelpFormatter().printHelp("ShowRename", options)
		exitProcess(0)
	} else {
		return this
	}
}

fun CommandLine.checkMandatory(): CommandLine {
	val allMandatory = this.hasOption(DIR.shortName)
			&& this.hasOption(SHOW.shortName)
			&& this.hasOption(SEASON.shortName)
			&& this.hasOption(KEYWORDS.shortName)
	if (allMandatory) {
		return this
	} else {
		println("Missing one or more of mandatory arguments")
		exitProcess(1)
	}
}

fun CommandLine.parseTerminalOptions(): TerminalInputs = TerminalInputs(
		dir = Paths.get(this.getOptionValue(DIR.shortName)),
		show = this.getOptionValue(SHOW.shortName),
		season = if (this.getOptionValue(SEASON.shortName).toInt() > 0) this.getOptionValue(SEASON.shortName).toInt() else 1,
		keywords = this.getOptionValues(KEYWORDS.shortName).toList()
)