package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class used to execute the smart scripts.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class SmartScriptEngine {
	
	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack = new ObjectMultistack();
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * @see hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor#visitTextNode(
		 * hr.fer.zemris.java.custom.scripting.nodes.TextNode)
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.err.print("I/O error has occured!");
				System.exit(-1);
			}
		}

		/* (non-Javadoc)
		 * @see hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor#visitForLoopNode(hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode)
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().asText();
			String start = node.getStartExpression().asText();
			int startValue = Integer.parseInt(start);
			int endValue = Integer.parseInt(node.getEndExpression().asText());
			int step;
			if(node.getStepExpression() == null) {
				step = 1;
			} else {
				step = Integer.parseInt(node.getStepExpression().asText());
			}
			multistack.push(variable, new ValueWrapper(startValue));
			while(multistack.peek(variable).numCompare(endValue) <= 0) {
				if(node.numberOfChildren() != 0) {
					for(int i = 0; i < node.numberOfChildren(); i++) {
						node.getChild(i).accept(this);
					}
				}
				multistack.peek(variable).increment(step);
			}
			multistack.pop(variable);
		}

		/* (non-Javadoc)
		 * @see hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor#visitEchoNode(hr.fer.zemris.java.custom.scripting.nodes.EchoNode)
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<ValueWrapper> stack = new Stack<ValueWrapper>();
			Token[] tokens = node.getTokens();
			for(int i = 0; i < node.getTokens().length; i++) {
				if(tokens[i] instanceof TokenConstantDouble || tokens[i] instanceof TokenConstantInteger 
						|| tokens[i] instanceof TokenString) {
					stack.push(new ValueWrapper(tokens[i].getValue()));
				} else if(tokens[i] instanceof TokenVariable) {
					stack.push(new ValueWrapper(multistack.multistack.get(tokens[i].asText()).getValue().getValue()));
				} else if(tokens[i] instanceof TokenOperator) {
					ValueWrapper value1 = stack.pop();
					ValueWrapper value2 = stack.pop();
					switch (tokens[i].asText()) {
						case "+":
							value1.increment(value2.getValue());
							break;
						case "-":
							value1.decrement(value2.getValue());
							break;
						case "*":
							value1.multiply(value2.getValue());
							break;
						case "/":
							value1.divide(value2.getValue());
							break;
					}
					stack.push(value1);
				} else if(tokens[i] instanceof TokenFunction) {
					switch (tokens[i].asText()) {
						case "@sin":
							sin(stack);
							break;
						case "@decfmt":
							decfmt(stack);
							break;
						case "@dup":
							dup(stack);
							break;
						case "@swap":
							swap(stack);
							break;
						case "@setMimeType":
							setMimeType(stack);
							break;
						case "@paramGet":
							paramget(stack);
							break;
						case "@pparamGet":
							pparamget(stack);
							break;
						case "@pparamSet":
							pparamSet(stack);
							break;
						case "@pparamDel":
							pparamDel(stack);
							break;
						case "@tparamGet":
							tparamGet(stack);
							break;
						case "@tparamSet":
							tparamSet(stack);
							break;
						case "@tparamDel":
							tparamDel(stack);
							break;
					}
				}
			}
			Stack<ValueWrapper> tempStack = new Stack<ValueWrapper>();
			while(!stack.isEmpty()) {
				ValueWrapper temp = stack.pop();
				tempStack.push(temp);
			}
			stack = tempStack;
			while(!stack.isEmpty()) {
				try {
					requestContext.write(stack.pop().getValue().toString());
				} catch (IOException e) {
					throw new RuntimeException("Can not write for some reason!");
				}
			}
		}

		/* (non-Javadoc)
		 * @see hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor#visitDocumentNode(hr.fer.zemris.java.custom.scripting.nodes.DocumentNode)
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			if(node.numberOfChildren() != 0) {
				for(int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}
			}
		}
	
		/**
		 * Method calculates a sinus of the given number.
		 * 
		 * @param stack current value stack
		 */
		private void sin(Stack<ValueWrapper> stack) {
			Double x = Double.parseDouble(stack.pop().getValue().toString());
			stack.push(new ValueWrapper(Math.sin(x*Math.PI/180.0)));
		}
	
		/**
		 * Method creates a decimal format of the given number.
		 * 
		 * @param stack current value stack
		 */
		private void decfmt(Stack<ValueWrapper> stack) {
			String format = stack.pop().getValue().toString();
			DecimalFormat f = new DecimalFormat(format);
			ValueWrapper x = stack.pop();
			stack.push(new ValueWrapper(f.format(x.getValue())));
		}
	
		/**
		 * Method duplicates the top element on the stack.
		 * 
		 * @param stack current value stack
		 */
		private void dup(Stack<ValueWrapper> stack) {
			ValueWrapper x = stack.pop();
			stack.push(x);
			stack.push(x);
		}
		
		/**
		 * Method swaps the two top elements on the stack.
		 * 
		 * @param stack current value stack
		 */
		private void swap(Stack<ValueWrapper> stack) {
			ValueWrapper a = stack.pop();
			ValueWrapper b = stack.pop();
			stack.push(a);
			stack.push(b);
		}
		
		/**
		 * Method sets the mime type.
		 * 
		 * @param stack current value stack
		 */
		private void setMimeType(Stack<ValueWrapper> stack) {
			String x = stack.pop().getValue().toString();
			requestContext.setMimeType(x);
		}
		
		/**
		 * Method pushes the parameter value to the stack.
		 * 
		 * @param stack current value stack
		 */
		private void paramget(Stack<ValueWrapper> stack) {
			ValueWrapper dv = stack.pop();
			String name = stack.pop().getValue().toString();
			String value = requestContext.getParameter(name);
			stack.push(value == null ? dv : new ValueWrapper(value));
		}
		
		/**
		 * Method pushes the private parameter value to the stack.
		 * 
		 * @param stack current value stack
		 */
		private void pparamget(Stack<ValueWrapper> stack) {
			ValueWrapper dv = stack.pop();
			String name = stack.pop().getValue().toString();
			String value = requestContext.getPersistentParameter(name);
			stack.push(value == null ? dv : new ValueWrapper(value));
		}
		
		/**
		 * Method sets the private parameter value.
		 * 
		 * @param stack current value stack
		 */
		private void pparamSet(Stack<ValueWrapper> stack) {
			String name = stack.pop().getValue().toString();
			String value = stack.pop().getValue().toString();
			requestContext.setPersistentParameter(name, value);
		}
		
		/**
		 * Method deletes a private parameter value.
		 * 
		 * @param stack current value stack
		 */
		private void pparamDel(Stack<ValueWrapper> stack) {
			String name = stack.pop().getValue().toString();
			requestContext.removePersistentParameter(name);
		}
		
		/**
		 * Method gets the temporary parameter value.
		 * 
		 * @param stack current value stack
		 */
		private void tparamGet(Stack<ValueWrapper> stack) {
			ValueWrapper dv = stack.pop();
			String name = stack.pop().getValue().toString();
			String value = requestContext.getTemporaryParameter(name);
			stack.push(value == null ? dv : new ValueWrapper(value));
		}
		
		/**
		 * Method sets a temporary parameter value.
		 * 
		 * @param stack current value stack
		 */
		private void tparamSet(Stack<ValueWrapper> stack) {
			String name = stack.pop().getValue().toString();
			String value = stack.pop().getValue().toString();
			requestContext.setTemporaryParameter(name, value);
		}
		
		/**
		 * Method deletes a temporary parameter value.
		 * 
		 * @param stack current value stack
		 */
		private void tparamDel(Stack<ValueWrapper> stack) {
			String name = stack.pop().getValue().toString();
			requestContext.removeTemporaryParameter(name);
		}
	};
	
	/**
	 * Class constructor. Constructor takes 2 arguments, a document node to run and the current request context.
	 * 
	 * @param documentNode document node to run
	 * @param requestContext current request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		if(documentNode == null) {
			throw new IllegalArgumentException("Document node can not be null.");
		}
		if(requestContext == null) {
			throw new IllegalArgumentException("Request context can not be null.");
		}
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/**
	 * Method used to execute the smart script engine.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
