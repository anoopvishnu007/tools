package sionea.sourcesearch.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to create pattern using pattern constructor class
 * 
 * @author anoopvn
 * 
 */
public final class PatternHelper {

	/**
	 * Escape character
	 */
	private static final String ESCAPE_CHAR = "\\\\";

	/**
	 * List of characters to skip from any pattern substitution operation
	 */
	public static List<Character> characterList = new ArrayList<Character>();
	static {
		characterList.add('(');
		characterList.add(')');
		characterList.add('{');
		characterList.add('}');
		characterList.add('.');
		characterList.add('[');
		characterList.add(']');
		characterList.add('$');
		characterList.add('^');
		characterList.add('+');
		characterList.add('|');
	}

	/**
	 * Constructor
	 */
	private PatternHelper() {

	}


	/**
	 * inserts the regular expression conditions to the pattern
	 * 
	 * @param pattern
	 *            pattern string
	 * @param buffer
	 *            string buffer
	 * @return
	 */
	public static StringBuffer appendAsRegEx(final String pattern,
			final StringBuffer buffer) {
		boolean isEscaped = false;
		for (int i = 0; i < pattern.length(); i++) {
			final char chr = pattern.charAt(i);

			if (chr == '\\') {
				isEscaped = true;
			} else if (characterList.contains(Character.valueOf(chr))) {
				isEscaped = appendEscape(buffer, isEscaped);
				buffer.append('\\');
				buffer.append(chr);
			} else if (chr == '?' || chr == '*') {
				if (isEscaped) {
					buffer.append('\\');
					buffer.append(chr);
					isEscaped = false;

				} else {
					buffer.append(chr == '?' ? '.' : ".*");
				}
			} else {
				isEscaped = appendEscape(buffer, isEscaped);
				buffer.append(chr);
			}

		}
		isEscaped = appendEscape(buffer, isEscaped);
		return buffer;
	}

	/**
	 * Appends escape characters to the buffer
	 * 
	 * @param buffer
	 *            the string buffer
	 * @param isEscaped
	 *            true for appending the escape character else false
	 * @return if escaped return false else true
	 */
	private static boolean appendEscape(final StringBuffer buffer,
			final boolean isEscaped) {
		boolean flag = isEscaped;
		if (flag) {
			buffer.append(ESCAPE_CHAR);
			flag = false;
		}
		return flag;
	}

	 
}
