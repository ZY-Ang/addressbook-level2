package seedu.addressbook.ui;

import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.StringTokenizer;

import static seedu.addressbook.common.Messages.MESSAGE_GOODBYE;
import static seedu.addressbook.common.Messages.MESSAGE_INIT_FAILED;
import static seedu.addressbook.common.Messages.MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE;
import static seedu.addressbook.common.Messages.MESSAGE_USING_STORAGE_FILE;
import static seedu.addressbook.common.Messages.MESSAGE_WELCOME;

/**
 * Text formatter utility class for text UI.
 */
public class Formatter {


    /** The maximum console width in number of monospaced characters, not inclusive of LINE_PREFIX */
    public static final int MAX_CONSOLE_WIDTH = 72;
    private static final String SUBLINE_PREFIX = "    ";
    
    /** A decorative prefix added to the beginning of lines printed by AddressBook */
    private static final String LINE_PREFIX = "|| ";

    /** A platform independent line separator. */
    private static final String LS = System.lineSeparator();

    private static final String DIVIDER = String.join("", Collections.nCopies(MAX_CONSOLE_WIDTH, "="));

    /** Format of indexed list item */
    private static final String MESSAGE_INDEXED_LIST_ITEM = "\t%1$d. %2$s";
    
    /** Offset required to convert between 1-indexing and 0-indexing.  */
    public static final int DISPLAYED_INDEX_OFFSET = 1;


    //===================================== Message Constants ===========================================
    private static final String MESSAGE_PROMPT_USER_INPUT = LINE_PREFIX + "Enter command: ";
    private static final String MESSAGE_FEEDBACK_COMMAND_ECHO = "[Command entered:%1$s]";
    private static final String MESSAGE_FEEDBACK_WELCOME_MESSAGE = DIVIDER + "\n" + DIVIDER
            + "\n" + MESSAGE_WELCOME
            + "\n" + "%1$s"
            + "\n" + MESSAGE_PROGRAM_LAUNCH_ARGS_USAGE
            + "\n" + "%2$s"
            + "\n" + DIVIDER;
    private static final String MESSAGE_FEEDBACK_GOODBYE_MESSAGE = MESSAGE_GOODBYE + "\n" + DIVIDER + "\n" + DIVIDER;
    private static final String MESSAGE_FEEDBACK_INIT_FAILED = MESSAGE_INIT_FAILED + "\n" + DIVIDER + "\n" + DIVIDER;
    private static final String MESSAGE_FEEDBACK_COMMAND_RESULT = "%1$s" + "\n" + DIVIDER;

    /**
     * Returns a formatted string collection obtained from a big string into several,
     * smaller strings that can be shown to the user directly.
     * 
     * @param longString A very long string possibly separated by line breaks.
     * @return An array of line strings that can be shown to the user.
     */
    public static Queue<String> getFormattedLines(String longString) {
        String[] lines = longString.split("\n");
        Queue<String> formattedLines = new LinkedList<>();
        for (String line: lines) {
            if (line.length() > MAX_CONSOLE_WIDTH) {
                formattedLines.add(getTruncatedLines(line));
            } else {
                formattedLines.add(LINE_PREFIX + line);
            }
        }
        return formattedLines;
    }

    /**
     * Returns a single formatted string for multiple lines from a truncated line.
     * 
     * @param longLine a long string to be truncated into smaller strings
     * @return a formatted string with words reaching no more than the maximum screen width per line.
     */
    private static String getTruncatedLines(String longLine) {
        StringTokenizer wordsRemaining = new StringTokenizer(longLine, " ");
        StringBuilder output = new StringBuilder(longLine.length());
        output.append(LINE_PREFIX);
        int lineLength = 0;
        while (wordsRemaining.hasMoreTokens()) {
            String currentWord = wordsRemaining.nextToken();
            
            if (lineLength + currentWord.length() > MAX_CONSOLE_WIDTH) {
                output.append(getLineEnding() + SUBLINE_PREFIX);
                lineLength = SUBLINE_PREFIX.length();
            }
            output.append(currentWord + " ");
            lineLength += currentWord.length() + 1;
        }
        return output.toString();
    }
    
    private static String getLineEnding() {
        return LS + LINE_PREFIX;
    }
    
    public static String getUserInputPrompt() {
        return MESSAGE_PROMPT_USER_INPUT;
    }
    
    public static String getUserCommandEcho(String userCommand) {
        return String.format(MESSAGE_FEEDBACK_COMMAND_ECHO, userCommand);
    }
    
    public static String getWelcomeMessage(String version, String storageFilePath) {
        return String.format(MESSAGE_FEEDBACK_WELCOME_MESSAGE,
                version,
                String.format(MESSAGE_USING_STORAGE_FILE, storageFilePath));
    }
    
    public static String getGoodbyeMessage() {
        return MESSAGE_FEEDBACK_GOODBYE_MESSAGE;
    }
    
    public static String getInitFailedMessage() {
        return MESSAGE_FEEDBACK_INIT_FAILED;
    }
    
    public static String getCommandResultMessage(String feedbackMessage) {
        return String.format(MESSAGE_FEEDBACK_COMMAND_RESULT, feedbackMessage);
    }

    /** Returns a formatted list of strings as a viewable indexed list. */
    public static String getIndexedListForViewing(List<String> listItems) {
        final StringBuilder formatted = new StringBuilder();
        int displayIndex = 0 + DISPLAYED_INDEX_OFFSET;
        for (String listItem : listItems) {
            formatted.append(getIndexedListItem(displayIndex, listItem)).append("\n");
            displayIndex++;
        }
        return formatted.toString();
    }

    /**
     * Returns a formatted string as a viewable indexed list item.
     *
     * @param visibleIndex visible index for this listing
     */
    private static String getIndexedListItem(int visibleIndex, String listItem) {
        return String.format(MESSAGE_INDEXED_LIST_ITEM, visibleIndex, listItem);
    }
    
}
