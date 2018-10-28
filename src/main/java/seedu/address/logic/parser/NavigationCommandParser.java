package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import seedu.address.logic.commands.NavigationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and creates a new NavigationCommand object
 */
public class NavigationCommandParser implements Parser<NavigationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NavigationCommand
     * and returns an NavigationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public NavigationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);
        if (!arePrefixesPresent(argMultimap)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NavigationCommand.MESSAGE_USAGE));
        }

        return new NavigationCommand();
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
