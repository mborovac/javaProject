/**
 * Package holding all the parsing classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.custom.scripting.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * Class used to create and maintain a document parser. Class takes one string, the document that is to be parsed.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SmartScriptParser {
	
	private DocumentNode documentNode;
	private String multiplePartString;
	private boolean isString = false;
	
	/**
	 * Class constructor. Takes one string, the document that is to be parsed.
	 * 
	 * @param document the document that is to be parsed
	 */
	public SmartScriptParser(String document) {
		documentNode = Parse(document);
	}
	
	/**
	 * Method used to parse the given document. It returns a document model of the parsed document in which a DocumentNode
	 * is the 1st node whose children are all the other nodes representing parts of the document.
	 * 
	 * @param document the document that is to be parsed
	 * @return returns a document model of the parsed document
	 * @throws SmartScriptParserException if the document is ill-formed
	 */
	public DocumentNode Parse(String document) {
		documentNode = new DocumentNode();
		ObjectStack stack = new ObjectStack();
		stack.push(documentNode);
		document = document.replace("\\r\\n", "\n");
		String[] docParts = document.split("\\{|\\}");
		boolean textCurlyBracket = false;		//variables used to differentiate "\{" and "{"
		boolean thisStringIsText = false;
		for(String splitDocParts: docParts) {
			if(textCurlyBracket) {
				thisStringIsText = true;
				textCurlyBracket = false;
			}
			if(splitDocParts.endsWith("\\")) {
				textCurlyBracket = true;
				splitDocParts += "{";
			}
			if(splitDocParts.contains("$") && !thisStringIsText) {		//function
				if(splitDocParts.matches("\\s*\\$\\s*=[^\\$]*\\$\\s*")) {	// =
					splitDocParts = splitDocParts.substring(splitDocParts.indexOf("=") + 1, 
							splitDocParts.lastIndexOf("$")).trim();
					String[] echoNodeExpressions = CreateStringArrayForTokens(splitDocParts);				
					Token[] tokens = new Token[echoNodeExpressions.length];
					for(int i = 0; i < echoNodeExpressions.length; i++) {
						tokens[i] = CreateToken(echoNodeExpressions[i]);
					}
					////// test
					List<Token> listOfTokens = new ArrayList<>();
					for(int i = 0; i < tokens.length; i++) {
						if(tokens[i] instanceof TokenString && tokens[i].getValue().equals("")) {
							continue;
						}
						listOfTokens.add(tokens[i]);
					}
					tokens = new Token[listOfTokens.size()];
					for(int i = 0; i < listOfTokens.size(); i++) {
						tokens[i] = listOfTokens.get(i);
					}
					EchoNode echoNode = new EchoNode(tokens);
					((Node) stack.peek()).addChildNode(echoNode);
				} else if(splitDocParts.matches("\\s*\\$\\s*[fF][oO][rR].*\\$\\s*")) {		// FOR
					splitDocParts = splitDocParts.substring(splitDocParts.toUpperCase().indexOf("FOR") + 3, 
							splitDocParts.lastIndexOf("$")).trim();
					String[] ForLoopExpressions = CreateStringArrayForTokens(splitDocParts);
					ForLoopNode forLoopNode = null;
					Token[] tokens = new Token[ForLoopExpressions.length];
					for(int i = 0; i < ForLoopExpressions.length; i++) {
						tokens[i] = CreateToken(ForLoopExpressions[i]);
					}
					////// test
					List<Token> listOfTokens = new ArrayList<>();
					for(int i = 0; i < tokens.length; i++) {
						if(tokens[i] instanceof TokenString && tokens[i].getValue().equals("")) {
							continue;
						}
						listOfTokens.add(tokens[i]);
					}
					tokens = new Token[listOfTokens.size()];
					for(int i = 0; i < listOfTokens.size(); i++) {
						tokens[i] = listOfTokens.get(i);
					}
					if(!(tokens[0] instanceof TokenVariable)) {
						throw new SmartScriptParserException("1st expression in a for loop must be a variable! "
								+ "$ FOR " + splitDocParts + " $");
					}
					if(ForLoopExpressions.length == 4) {
						forLoopNode = new ForLoopNode((TokenVariable)tokens[0], tokens[1], tokens[2], tokens[3]);
					}  else if(ForLoopExpressions.length == 3) {
						forLoopNode = new ForLoopNode((TokenVariable)tokens[0], tokens[1], tokens[2]);
					}
					((Node) stack.peek()).addChildNode(forLoopNode);
					stack.push(forLoopNode);
				} else if(splitDocParts.matches("\\s*\\$\\s*[eE][nN][dD]\\s*\\$\\s*")) {		// END
					stack.pop();
					if(stack.isEmpty()) {
						throw new SmartScriptParserException("Document contains more {$END$} tags than opened " +
								"non-empty tags!");
					}
				} else {
					throw new SmartScriptParserException("Function " + splitDocParts + " is badly defined!");
				}
			} else if(!splitDocParts.isEmpty()){		//text
				if(thisStringIsText) {
					thisStringIsText = false;
					splitDocParts += "}";
				}
				TextNode textNode = new TextNode(splitDocParts);
				((Node) stack.peek()).addChildNode(textNode);
			}
		}
		if(!(stack.peek() instanceof DocumentNode)) {
			throw new SmartScriptParserException("Document doesn't contain enough {$END$} tags");
		}
		return documentNode;
	}
	
	/**
	 * Method for creating a suitable token out of a string. The method creates and returns a single adequate token or
	 * throws a SmartScriptParserException if a token can not be created from the given string.
	 * 
	 * @param string the string from which a token is to be created
	 * @return returns a single Token
	 * @throws throws SmartScriptParserException if a token can not be created
	 */
	private Token CreateToken(String string) {
		string = string.trim();
		if(isString) {
			if(string.contains("\"")) {
				multiplePartString += " " + string.substring(0, string.length() - 1);
				isString = false;
				TokenString stringToken = new TokenString(multiplePartString);
				return stringToken;
			} else {
				multiplePartString += " " + string;
			}
			TokenString stringToken = new TokenString("");
			return stringToken;
		}
		if(string.matches("@[a-zA-Z][\\w]*")) {	
			TokenFunction funcToken = new TokenFunction(string);
			return funcToken;
		} else if(string.matches("[a-zA-Z][\\w]*")) {
			TokenVariable varToken = new TokenVariable(string);
			return varToken;
		} else if(string.matches("[\\+\\-\\*\\/\\%]")) {
			TokenOperator operatorToken = new TokenOperator(string);
			return operatorToken;
		} else if(string.matches("\"[^\"]+\"")) {
			TokenString stringToken = new TokenString(string.substring(1, string.length() - 1));
			return stringToken;
		} else if(string.matches("[\\d]+\\.[\\d]+")) {
			TokenConstantDouble doubleToken = new TokenConstantDouble(Double.parseDouble(string));
			return doubleToken;
		} else if(string.matches("[\\d]+")) {
			TokenConstantInteger integerToken = new TokenConstantInteger(Integer.parseInt(string));
			return integerToken;
		} else if(string.matches("\"[\\s]*[^\"]*")) {
			multiplePartString = string.substring(1);
			isString = true;
			TokenString stringToken = new TokenString("");
			return stringToken;
		} else if(string.matches("[\"]+") && isString) {
			multiplePartString += " " + string.substring(1);
			TokenString stringToken = new TokenString("");
			return stringToken;
		}  else if(string.matches("[^\"]*\"")) {
			multiplePartString += " " + string.substring(0, string.length() - 1);
			isString = false;
			TokenString stringToken = new TokenString(multiplePartString);
			return stringToken;
		} else if(string.matches("\n")) { 
			TokenString stringToken = new TokenString(string);
			return stringToken;
		}  else if(string.matches("")) { 
			TokenString stringToken = new TokenString(string);
			return stringToken;
		} else {
			throw new SmartScriptParserException("Can not create a token with string \"" + string + "\", it is not a " +
					"function, variable, operator, string or a constant!");
		}
	}
	
	/**
	 * Method creates an array of strings later used to create parsing tokens. It accepts a single string representing
	 * parts of document. The method splits the given string as much as it can without changing the plain text surrounded
	 * by non-escaped quotations. It differentiates escaped and non-escaped quotations. Method returns an array of strings.
	 * 
	 * @param splitDocParts a single string representing a part of the document
	 * @return returns an array of strings
	 */
	private String[] CreateStringArrayForTokens(String splitDocParts) {
		String[] nodeExpressions;
		if(splitDocParts.contains("\\\"")) {
			nodeExpressions = splitDocParts.split("(((?<!\\\\{1})\")|((?<!\\\\{1})\"))");
			for(int k = 0; k < nodeExpressions.length; k++) {
				if(nodeExpressions[k].contains("\\\"")) {
					nodeExpressions[k] = "\"" + nodeExpressions[k] + "\"";
				}
			}
			ArrayBackedIndexedCollection listOfExpressions = new ArrayBackedIndexedCollection();
			for(String expression: nodeExpressions) {
				if(!expression.equals("") && !expression.equals(" ")) {
					if(expression.matches("\".+\"")) {
						listOfExpressions.add(expression.trim());
					} else {
						String[] partialExpressions = expression.trim().split(" ");
						for(String splitPartialExpressions: partialExpressions) {
							if(!splitPartialExpressions.equals("") && splitPartialExpressions != null) {
								listOfExpressions.add(splitPartialExpressions);
							}
						}
					}
				}
			}
			int sizeOfList = listOfExpressions.size();
			nodeExpressions = new String[sizeOfList];
			for(int k = 0; k < sizeOfList; k++) {
				nodeExpressions[k] = (String) listOfExpressions.get(k);
			}
		} else {
			nodeExpressions = splitDocParts.split(" ");
		}
		List<String> expressions = new ArrayList<>();
		for(int i = 0; i < nodeExpressions.length; i++) {
			String temp = nodeExpressions[i];
			if(temp.contains("\n")) {
				while(temp.contains("\n")) {
					if(temp.matches("\"[^\"]+\"")) {
						expressions.add(temp);
						break;
					}
					int index = temp.indexOf("\n");
					expressions.add(temp.substring(0, index));
					expressions.add("\n");
					if(!temp.substring(index + 1, temp.length()).contains("\n")) {
						expressions.add(temp.substring(index + 1, temp.length()));
						break;
					} else {
						temp = temp.substring(index + 1, temp.length());
					}
				}
			} else {
				expressions.add(temp);
			}
		}
		String[] arrayExpressions = new String[expressions.size()];
		for(int i = 0; i < expressions.size(); i++) {
			arrayExpressions[i] = expressions.get(i);
		}
		return arrayExpressions;
	}
	
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
}
