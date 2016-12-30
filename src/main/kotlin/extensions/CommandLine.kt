package extensions

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import terminal.TerminalInputs
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
	val allMandatory = this.hasOption("dir") && this.hasOption("show") && this.hasOption("season")
			&& this.hasOption("k")
	if (allMandatory) {
		return this
	} else {
		println("Missing one or more of mandatory arguments")
		exitProcess(1)
	}
}

fun CommandLine.parseTerminalOptions(): TerminalInputs = TerminalInputs(
		dir = Paths.get(this.getOptionValue("d")), show = this.getOptionValue("sh"),
		season = if (this.getOptionValue("se").toInt() > 0) this.getOptionValue("se").toInt() else 1,
		keywords = this.getOptionValues("k").toList()
)