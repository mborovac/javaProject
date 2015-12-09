/**
 * Package holding the classes used in the lexical analysis of Java 8th homework.
 */
package hr.fer.zemris.java.filechecking.lexical;

/**
 * Enum representing all the possible values of the FilecheckingToken.
 * 
 * @author MarkoB
 * @version 1.0
 */
public enum FilecheckingTokenType {
	
	EOF,
	IDENT,
	KEYWORD,
	SWAP_WORD,
	STRING_CONTENT,
	COLON,
	SEMICOLON,
	COMMA,
	POINT,
	HYPHEN,
	EXCLAMATION,
	QUOTATION_MARK,
	SLASH,
	RESOLVE,
	CURLY_BRACKET_RESOLVE,
	RESOLVE_WORD,
	CURLY_BRACKET_OPEN,
	CURLY_BRACKET_CLOSED,
	MONKEY,
	PACKAGE_IDENT
}
