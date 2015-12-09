package hr.fer.zemris.java.custom.scripting.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.tokens.Token;

/**
 * Class used to test the execution of the document tree parser and writer.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class TreeWriter {
	
	/**
	 * Method main used to run the program.
	 * 
	 * @param args program takes a single argument, path to the file to be parsed
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			throw new IllegalArgumentException("Expected 1 argument, path to a smart script.");
		}
		File file = new File(args[0]);

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		} catch (IOException e1) {
			throw new RuntimeException("Can not create a buffered reader for selected file, reason unknown.");
		}
		String docBody = "";
		String str;
		try {
			while ((str = reader.readLine()) != null) {
				docBody += str + "\n";
			}
		} catch (IOException e) {
			throw new RuntimeException("Can not read from selected file, reason unknown.");
		}
		try {
			reader.close();
		} catch (IOException ignore) {
		}
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
		// by the time the previous line completes its job, the document should have been written
		// on the standard output
	}
	
	/**
	 * Private class used to create a document tree out of a document node and its children using the visitor
	 * design pattern.
	 */
	private static class WriterVisitor implements INodeVisitor {

		/**
		 * @see hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor#visitTextNode(
		 * hr.fer.zemris.java.custom.scripting.nodes.TextNode)
		 */
		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
		}

		/**
		 * @see hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor#visitForLoopNode(
		 * hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode)
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print("{$ FOR " + node.getVariable().asText() + " " + node.getStartExpression().asText() +
					" " + node.getEndExpression().asText());
			if(node.getStepExpression() != null) {
				System.out.print(" " + node.getStepExpression().asText());
			}
			System.out.print(" $}");
			if(node.numberOfChildren() != 0) {
				for(int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}
			}
			System.out.print("{$END$}");
		}

		/**
		 * @see hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor#visitEchoNode(
		 * hr.fer.zemris.java.custom.scripting.nodes.EchoNode)
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			Token[] tokens = node.getTokens();
			System.out.print("{$ =");
			for(Token token: tokens) {
				System.out.print(" " + token.asText());
			}
			System.out.print(" $}");
			if(node.numberOfChildren() != 0) {
				for(int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}
			}
		}

		/**
		 * @see hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor#visitDocumentNode(
		 * hr.fer.zemris.java.custom.scripting.nodes.DocumentNode)
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			if(node.numberOfChildren() != 0) {
				for(int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}
			}
		}
	}
}
