/**
 * Package holding all the testing classes created and used in 2nd Java homework
 * 
 */
package hr.fer.zemris.java.hw2;


import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.custom.scripting.tokens.Token;

/**
 * Class used to create a tester for SmartScriptParser. Class requires no arguments
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SmartScriptTester {

	/**
	 * Method main which calls all other needed methods.
	 * 
	 * @param args no arguments are required, all given arguments will be ignored
	 */
	public static void main(String[] args) {
		
		String docBody = "This is sample text.\r\n" +
				"{$ FOR i 1 10 1 $}\r\n" +
				"  This is {$= i $}-th time this message is generated.\r\n" +
				"{$END$}\r\n" +
				"{$FOR i 0 10 2 $}\r\n" +
				"  sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\r\nž" +
				"{$ END $}";
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
		System.out.println("Unable to parse document!");
		System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.print(originalDocumentBody); 	// should write something like original content of docBody
	}
	
	/**
	 * Method used to recreate the original document that was parsed using the SmartScriptParser. Method requires the
	 * DocumentNode of the parsed document.
	 * 
	 * @param document the DocumentNode of the parsed document
	 * @return returns the string recreated from the given DocumentNode
	 */
	private static String createOriginalDocumentBody(Node document) {
		String originalDocumentBody = new String();
		for(int i = 0; i < document.numberOfChildren(); i++) {
			if(document.getChild(i) instanceof TextNode) {
				TextNode currentNode = (TextNode) document.getChild(i);
				originalDocumentBody = originalDocumentBody.concat(currentNode.getText());
				if(currentNode.numberOfChildren() != 0) {
					originalDocumentBody += createOriginalDocumentBody((Node) currentNode);
				}
			} else if(document.getChild(i) instanceof EchoNode) {
				EchoNode currentNode = (EchoNode) document.getChild(i);
				Token[] tokens = currentNode.getTokens();
				originalDocumentBody = originalDocumentBody + "{$ =";
				for(Token token: tokens) {
					originalDocumentBody = originalDocumentBody + " " + token.asText();
				}
				originalDocumentBody = originalDocumentBody + " $}";
				if(currentNode.numberOfChildren() > 0) {
					originalDocumentBody += createOriginalDocumentBody((Node) currentNode);
				}
			} else if(document.getChild(i) instanceof ForLoopNode) {
				ForLoopNode currentNode = (ForLoopNode) document.getChild(i);
				originalDocumentBody = originalDocumentBody + "{$ FOR";
				originalDocumentBody = originalDocumentBody + " " + currentNode.getVariable().asText() + " " + 
				currentNode.getStartExpression().asText() + " " + currentNode.getEndExpression().asText();
				if(currentNode.getStepExpression() != null) {
					originalDocumentBody = originalDocumentBody + " " + currentNode.getStepExpression().asText();
				}
				originalDocumentBody = originalDocumentBody + " $}";
				if(currentNode.numberOfChildren() > 0) {
					originalDocumentBody += createOriginalDocumentBody((Node) currentNode);
				}
				originalDocumentBody = originalDocumentBody + "{$END$}";
			} else {
				return null;
			}
		}
		return originalDocumentBody;
	}
}
