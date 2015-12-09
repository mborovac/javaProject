package hr.fer.zemris.java.hw11.notepadpp;

import hr.fer.zemris.java.hw11.notepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.notepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.notepadpp.local.swing.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.notepadpp.local.swing.LJMenu;
import hr.fer.zemris.java.hw11.notepadpp.local.swing.LJToolBar;
import hr.fer.zemris.java.hw11.notepadpp.local.swing.LocalizableAction;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Class used to provide a Notepad++ - like program. Program supports 2 languages, english and croatian.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class NotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextArea editor;
	private Path openedFilePath;
	private String copiedText;
	private FormLocalizationProvider flp =  new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	private List<JButton> buttonList;
	private List<JMenuItem> menuItemList;
	private List<LJMenu> menuList;
	private boolean modified;
	private LJToolBar toolBar;
	
	/**
	 * Class constructor. Constructor takes no arguments. Constructor creates a main frame in which the
	 * program is set and calls initGUI() method to fill the rest.
	 */
	public NotepadPP() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Notepad++");
		setLocation(150, 40);
		setSize(850, 650);
		copiedText = "";
		buttonList = new ArrayList<>();
		menuItemList = new ArrayList<>();
		menuList = new ArrayList<>();
		modified = false;
		initGUI();
		// Listener on the localization interface, informs all buttons, menus and menu items about the change
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				for(JButton button: buttonList) {
					button.setText(LocalizationProvider.getInstance().getString(
							((LocalizableAction) button.getAction()).getKey()));
				}
				for(JMenuItem menuItem: menuItemList) {
					menuItem.setText(LocalizationProvider.getInstance().getString(
							((LocalizableAction) menuItem.getAction()).getKey()));
				}
				for(LJMenu menu: menuList) {
					menu.setText(LocalizationProvider.getInstance().getString(
							menu.getKey()));
				}
				toolBar.setName(LocalizationProvider.getInstance().getString(
						toolBar.getKey()));
				System.out.println(toolBar.getName());
			}
		});
		// Document listener on the main text area. If anything changes it sets the flag accordingly
		Document doc = editor.getDocument();
		doc.addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				modified = true;
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				modified = true;
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				modified = true;
			}
		});
	}
	
	/**
	 * Method used to initiate the GUI
	 */
	private void initGUI() {
		
		editor = new JTextArea();
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(new JScrollPane(editor), BorderLayout.CENTER);
		
		createMenus();
		createToolbars();
	}

	/**
	 * Method used to create and add the menu bars
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		LJMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);
		menuList.add(fileMenu);
		JMenuItem newDoc = new JMenuItem(newDocumentAction);
		fileMenu.add(newDoc);
		this.menuItemList.add(newDoc);
		JMenuItem open = new JMenuItem(openDocumentAction);
		fileMenu.add(open);
		this.menuItemList.add(open);
		JMenuItem save = new JMenuItem(saveDocumentAction);
		fileMenu.add(save);
		this.menuItemList.add(save);
		JMenuItem saveAs = new JMenuItem(saveAsDocumentAction);
		fileMenu.add(saveAs);
		this.menuItemList.add(saveAs);
		fileMenu.addSeparator();
		JMenuItem exit = new JMenuItem(exitAction);
		fileMenu.add(exit);
		this.menuItemList.add(exit);
		
		LJMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);
		menuList.add(editMenu);
		JMenuItem cut = new JMenuItem(cutSelectedPartAction);
		editMenu.add(cut);
		this.menuItemList.add(cut);
		JMenuItem copy = new JMenuItem(copySelectedPartAction);
		editMenu.add(copy);
		this.menuItemList.add(copy);
		JMenuItem paste = new JMenuItem(pasteSelectedPartAction);
		editMenu.add(paste);
		this.menuItemList.add(paste);
		JMenuItem delete = new JMenuItem(deleteSelectedPartAction);
		editMenu.add(delete);
		this.menuItemList.add(delete);
		JMenuItem toggleCase = new JMenuItem(toggleCaseAction);
		editMenu.add(toggleCase);
		this.menuItemList.add(toggleCase);
		
		LJMenu languages = new LJMenu("languages", flp);
		menuBar.add(languages);
		menuList.add(languages);
		JMenuItem english = new JMenuItem(englishLanguageAction);
		languages.add(english);
		this.menuItemList.add(english);
		JMenuItem croatian = new JMenuItem(croatianLanguageAction);
		languages.add(croatian);
		this.menuItemList.add(croatian);
		this.setJMenuBar(menuBar);
	}

	/**
	 * Method used to create and the toolbar
	 */
	private void createToolbars() {
		toolBar = new LJToolBar("toolBar", flp);
		toolBar.setFloatable(true);
		
		JButton newDoc = new JButton(newDocumentAction);
		toolBar.add(newDoc);
		buttonList.add(newDoc);
		JButton open = new JButton(openDocumentAction);
		toolBar.add(open);
		buttonList.add(open);
		JButton save = new JButton(saveDocumentAction);
		toolBar.add(save);
		buttonList.add(save);
		JButton saveAs = new JButton(saveAsDocumentAction);
		toolBar.add(saveAs);
		buttonList.add(saveAs);
		toolBar.addSeparator();
		JButton cut = new JButton(cutSelectedPartAction);
		toolBar.add(cut);
		buttonList.add(cut);
		JButton copy = new JButton(copySelectedPartAction);
		toolBar.add(copy);
		buttonList.add(copy);
		JButton paste = new JButton(pasteSelectedPartAction);
		toolBar.add(paste);
		buttonList.add(paste);
		JButton delete = new JButton(deleteSelectedPartAction);
		toolBar.add(delete);
		buttonList.add(delete);
		JButton toggleCase = new JButton(toggleCaseAction);
		toolBar.add(toggleCase);
		buttonList.add(toggleCase);
		
		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Method used to define the action for the "New" menu option.
	 * It opens a new file and lets the user save the old one if it had been changed since the last save.
	 */
	private Action newDocumentAction = new LocalizableAction("new", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(modified) {
				int selected = JOptionPane.showConfirmDialog(null, flp.getString("changedFile"), 
						flp.getString("save"), JOptionPane.YES_NO_CANCEL_OPTION);
				if(selected == 0) {
					saveDocumentAction.actionPerformed(e);
					if(!modified) {
						editor.setText("");
						openedFilePath = null;
						modified = false;
						return;
					} else {
						return;
					}
				} else if(selected == 1) {
					editor.setText("");
					openedFilePath = null;
					modified = false;
					return;
				} else {
					return;
				}
			}
			editor.setText("");
			openedFilePath = null;
			modified = false;
		}
	};
	
	/**
	 * Method used to define the action for the "Open" menu option.
	 * It lets the user open another file and lets the user save the old one if it had been changed
	 * since the last save.
	 */
	private Action openDocumentAction = new LocalizableAction("open", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(modified) {
				int selected = JOptionPane.showConfirmDialog(null, flp.getString("changedFile"), 
						flp.getString("save"), JOptionPane.YES_NO_CANCEL_OPTION);
				if(selected == 0) {
					saveDocumentAction.actionPerformed(e);
					if(modified) {
						return;
					}
				} else if(selected == 2) {
					return;
				}
			}
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(NotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						NotepadPP.this, 
						flp.getString("nofile") + ": " + fileName.getAbsolutePath(),
						flp.getString("error"), 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			byte[] okteti;
			try {
				okteti = Files.readAllBytes(filePath);
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(
						NotepadPP.this, 
						flp.getString("readingError") + ": " +fileName.getAbsolutePath(), 
						flp.getString("error"), 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String tekst = new String(okteti, StandardCharsets.UTF_8);
			editor.setText(tekst);
			openedFilePath = filePath;
			modified = false;
		}
	};
	
	/**
	 * Method used to define the action for the "Save" menu option.
	 * It lets the user save the current file. If the file name has been defined it simply overrides the old
	 * file, if it hasn't the method asks the user to define the name and the location of the file.
	 */
	private Action saveDocumentAction = new LocalizableAction("save", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(openedFilePath == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if(jfc.showSaveDialog(NotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(
							NotepadPP.this, 
							flp.getString("notSaved"), 
							flp.getString("warning"), 
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			byte[] podatci = editor.getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						NotepadPP.this, 
						flp.getString("saveError") + " " + openedFilePath.toFile().getAbsolutePath() +
						".\n" + flp.getString("saveErrorWarning"), 
						flp.getString("error"), 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(
					NotepadPP.this, 
					flp.getString("saved"), 
					flp.getString("info"), 
					JOptionPane.INFORMATION_MESSAGE);
			modified = false;
		}
	};
	
	/**
	 * Method used to define the action for the "Save As" menu option.
	 * It lets the user save the current file to the desired location and with desired name.
	 */
	private Action saveAsDocumentAction = new LocalizableAction("saveAs", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if(jfc.showSaveDialog(NotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						NotepadPP.this, 
						flp.getString("notSaved"), 
						flp.getString("warning"), 
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			openedFilePath = jfc.getSelectedFile().toPath();
			byte[] podatci = editor.getText().getBytes(StandardCharsets.UTF_8);
			try {
				Files.write(openedFilePath, podatci);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						NotepadPP.this, 
						flp.getString("saveError") + " " + openedFilePath.toFile().getAbsolutePath() +
						".\n" + flp.getString("saveErrorWarning"), 
						flp.getString("error"), 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(
					NotepadPP.this, 
					flp.getString("saved"), 
					flp.getString("info"), 
					JOptionPane.INFORMATION_MESSAGE);
			modified = false;
		}
	};
	
	/**
	 * Method used to define the action for the "Cut" menu option.
	 * It lets the user copy a part of the text and delete it from the text area.
	 */
	private Action cutSelectedPartAction = new LocalizableAction("cut", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			if(len == 0) {
				return;
			}
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				copiedText = doc.getText(offset, len);
				doc.remove(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	/**
	 * Method used to define the action for the "Copy" menu option.
	 * It lets the user copy a part of the text.
	 */
	private Action copySelectedPartAction = new LocalizableAction("copy", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			if(len == 0) {
				return;
			}
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				copiedText = doc.getText(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	/**
	 * Method used to define the action for the "Paste" menu option.
	 * It lets the user paste the previously copied text to the desired location. User can also select a part
	 * of the text and paste the copied text over it. This will remove the selected text.
	 */
	private Action pasteSelectedPartAction = new LocalizableAction("paste", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				if(len > 0) {
					doc.remove(offset, len);
					offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
				}
				if(offset == 0) {
					offset = editor.getCaret().getMark();
				}
				doc.insertString(offset, copiedText, null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	/**
	 * Method used to define the action for the "Delete" menu option.
	 * It lets the user delete the selected part of the text.
	 */
	private Action deleteSelectedPartAction = new LocalizableAction("delete", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			if(len == 0) {
				return;
			}
			int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			try {
				doc.remove(offset, len);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	/**
	 * Method used to define the action for the "Toggle Case" menu option.
	 * It lets the user switch the Case of the selected text. If no text is selected the whole text will 
	 * switch Case.
	 */
	private Action toggleCaseAction = new LocalizableAction("toggleCase", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Document doc = editor.getDocument();
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			int offset = 0;
			if(len != 0) {
				offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			} else {
				len = doc.getLength();
			}
			try {
				String text = doc.getText(offset, len);
				text = changeCase(text);
				doc.remove(offset, len);
				doc.insertString(offset, text, null);
			} catch(BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		/**
		 * Method used to change the case of the given String. It changes the case of every character
		 * one-by-one.
		 * 
		 * @param text
		 * @return
		 */
		private String changeCase(String text) {
			char[] znakovi = text.toCharArray();
			for(int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if(Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if(Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			return new String(znakovi);
		}
	};
	
	/**
	 * Method used set the current localization language to english.
	 */
	private Action englishLanguageAction = new LocalizableAction("en", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	
	/**
	 * Method used set the current localization language to croatian.
	 */
	private Action croatianLanguageAction = new LocalizableAction("hr", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};
	
	/**
	 * Method used to define the action for the "Exit" menu option.
	 * If the file has changed since the last save it lets the user save it before exiting.
	 */
	private Action exitAction = new LocalizableAction("exit", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(modified) {
				int selected = JOptionPane.showConfirmDialog(null, flp.getString("changedFile"), 
						flp.getString("save"), JOptionPane.YES_NO_CANCEL_OPTION);
				if(selected == 0) {
					saveDocumentAction.actionPerformed(e);
					if(!modified) {
						System.exit(0);
					} else {
						return;
					}
				} else if(selected == 1) {
					System.exit(0);
				} else {
					return;
				}
			}
			System.exit(0);
		}
	};
	
	/**
	 * Method main used to run the program. Method takes no arguments.
	 * 
	 * @param args arguments of the method main, all given arguments will be ignored
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new NotepadPP().setVisible(true);
			}
		});
	}
}
