package hr.fer.zemris.java.hw11.jvdraw;

import hr.fer.zemris.java.hw11.jvdraw.component.JColorArea;
import hr.fer.zemris.java.hw11.jvdraw.component.JDrawingCanvas;
import hr.fer.zemris.java.hw11.jvdraw.component.SelectedColorLabel;
import hr.fer.zemris.java.hw11.jvdraw.model.BoundingBox;
import hr.fer.zemris.java.hw11.jvdraw.model.DrawingObjectListModel;
import hr.fer.zemris.java.hw11.jvdraw.model.GeomObjDrawingModel;
import hr.fer.zemris.java.hw11.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw11.jvdraw.objects.DrawingModel;
import hr.fer.zemris.java.hw11.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw11.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw11.jvdraw.objects.Line;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * CLass used to create a GUI giving the user options to draw lines, circles or filled circles. User is
 * allowed to change the color of the line, rim color of the circle and rim and filling of the filled
 * circle. User is also allowed to open an old <code>jvd</code> file, save the current file in a 
 * <code>jvd</code> format or export the current image to <code>jpg</code>, <code>gif</code> or 
 * <code>png</code> format.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class JVDraw extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private DrawingModel model;
	private File currentFile;
	
	/**
	 * Class constructor. Constructor initiates the GUI.
	 */
	public JVDraw() {
		model = new GeomObjDrawingModel();
		currentFile = null;
		initGUI();
	}
	
	/**
	 * Method main used to start the program. Method takes no arguments.
	 * 
	 * @param args program arguments, all passed arguments will be ignored
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new JVDraw().setVisible(true);
			}
		});
	}
	
	/**
	 * Method used to create the GUI. It is a huge method covering the creation and addition of all
	 * of the components and creation of all of their listeners.
	 */
	private void initGUI() {
		setLocation(200, 100);
		setPreferredSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setTitle("JVDraw");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		// Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setToolTipText("Exit application");
		JMenuItem open = new JMenuItem("Open");
		open.setToolTipText("Open file.");
		final JMenuItem save = new JMenuItem("Save");
		save.setToolTipText("Save file.");
		final JMenuItem saveAs = new JMenuItem("Save As");
		saveAs.setToolTipText("Save file.");
		JMenuItem export = new JMenuItem("Export");
		export.setToolTipText("Export current file.");
		JMenuItem exit = new JMenuItem("Exit");
		exit.setToolTipText("Exit current file.");
		file.add(open);
		file.add(save);
		file.add(saveAs);
		file.add(export);
		file.add(exit);
		menuBar.add(file);
		this.setJMenuBar(menuBar);
		// Toolbar
		JToolBar toolbar = new JToolBar();
		GroupLayout layout = new GroupLayout(toolbar);
		toolbar.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		JColorArea foregroundColor = new JColorArea(Color.RED);
		JColorArea backgroundColor = new JColorArea(Color.BLUE);
		JToggleButton lineButton = new JToggleButton("Line");
		JToggleButton circleButton = new JToggleButton("Circle");
		JToggleButton filledCircleButton = new JToggleButton("Filled circle");
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.getSelection();
		buttonGroup.add(lineButton);
		buttonGroup.add(circleButton);
		buttonGroup.add(filledCircleButton);
		layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(foregroundColor)
				.addComponent(backgroundColor).addComponent(lineButton).addComponent(circleButton)
				.addComponent(filledCircleButton));
		layout.setVerticalGroup(layout.createParallelGroup().addComponent(foregroundColor)
				.addComponent(backgroundColor).addComponent(lineButton).addComponent(circleButton)
				.addComponent(filledCircleButton));
		add(toolbar, BorderLayout.NORTH);
		// Bottom component showing current selected colors as RGB components
		SelectedColorLabel botLabel = new SelectedColorLabel(foregroundColor, backgroundColor);
		getContentPane().add(botLabel, BorderLayout.SOUTH);
		// Canvas
		final JDrawingCanvas canvas = new JDrawingCanvas(model, foregroundColor, backgroundColor);
		canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		canvas.setMinimumSize(new Dimension(200, 200));
		getContentPane().add(canvas, BorderLayout.CENTER);
		// Model containing all the drawn components
		DrawingObjectListModel listModel = new DrawingObjectListModel(model);
		// Left content panel
		JList<GeometricalObject> listOfObjects = new JList<>(listModel);
		listOfObjects.setVisible(true);
		JScrollPane scrollPane = new JScrollPane(listOfObjects);
		scrollPane.setPreferredSize(new Dimension(100, 600));
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		getContentPane().add(scrollPane, BorderLayout.EAST);
		
		// Mouse adapter, responds to double click
		MouseListener doubleClick = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				@SuppressWarnings("unchecked")
				JList<GeometricalObject> lista = (JList<GeometricalObject>) e.getSource();
				if(e.getClickCount() != 2) {
					return;
				}
				int index = lista.getSelectedIndex();
				if(index == -1) {
					return;
				}
				GeometricalObject selectedObject = model.getObject(index);
				selectedObject.modifyObject(model);
			}
		};
		listOfObjects.addMouseListener(doubleClick);
		
		// Button listeners
		// Line button listener
		lineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setIsLine();
			}
		});
		
		// Circle button listener
		circleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setIsCircle();
			}
		});
		
		// Filled circle button listener
		filledCircleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setIsFilledCircle();
			}
		});
		
		// Open button listener
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("*.jvd", "jvd"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				int chosen = fileChooser.showOpenDialog(null);
				File file;
				if (chosen == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
				} else {
					return;
				}
				BufferedReader br = null;
				try {
					model.clear();
					readFile(file, br);
					currentFile = file;
					model.setModified(false);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Can not read file " + file.getName() + "!", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		// Save button listener
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(currentFile == null) {
					saveAs.doClick();
				} else {
					BufferedWriter bw = null;
					try {
						writeToFile(currentFile, bw);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Can not read file " + currentFile.getName() + "!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
					JOptionPane.showMessageDialog(null, "File " + currentFile.getName() + " saved.", 
							"Save.", 
							JOptionPane.INFORMATION_MESSAGE);
					model.setModified(false);
				}
			}
		});
		
		// Save As button listener
		saveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("*.jvd", "jvd"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				FileFilter filter = fileChooser.getFileFilter();
				int chosen = fileChooser.showSaveDialog(null);
				File file;
				if (chosen == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					if(!filter.accept(file)) {
						JOptionPane.showMessageDialog(null, "File " + file.getName() + " can not be saved," +
								"unsupported file type.", 
								"Error", 
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				} else {
					return;
				}
				BufferedWriter bw = null;
				try {
					writeToFile(file, bw);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Can not read file " + file.getName() + "!", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
				JOptionPane.showMessageDialog(null, "File " + file.getName() + " saved.", 
						"Save.", 
						JOptionPane.INFORMATION_MESSAGE);
				model.setModified(false);
			}
		});
		
		// Export button listener
		export.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.png", "png"));
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.gif", "gif"));
				fileChooser.setFileFilter(new FileNameExtensionFilter("*.jpg", "jpg"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				int chosen = fileChooser.showSaveDialog(null);
				FileFilter filter = fileChooser.getFileFilter();
				File file = null;
				if (chosen == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					if(!filter.accept(file)) {
						JOptionPane.showMessageDialog(null, "File " + file.getName() + " can not be exported," +
								"unsupported file type.", 
								"Error", 
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				} else {
					return;
				}
				BufferedImage image = null;
				exportImage(file, image, canvas);
				JOptionPane.showMessageDialog(null, "File " + file.getName() + " exported.", 
						"Export.", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		// Exit button listener
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getModified()) {
					int selected = JOptionPane.showConfirmDialog(null, "File was changed, do you wish to save?", 
							"Save", JOptionPane.YES_NO_CANCEL_OPTION);
					if(selected == 0) {
						saveAs.doClick();
						if(!model.getModified()) {
							System.exit(0);
						} else {
							return;
						}
					} else if(selected == 1) {
						System.exit(0);
					} else {
						return;
					}
				} else {
					System.exit(0);
				}
			}
		});
	}
	
	/**
	 * Method used to read the contents of a <code>jvd</code> file and add the specified components
	 * to the model.
	 * 
	 * @param file file to read
	 * @param br buffered reader that will be used to read the file
	 * @throws IOException if file can not be read
	 */
	private void readFile(File file, BufferedReader br) throws IOException {
		br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
		String line = br.readLine();
		while(line != null && !line.isEmpty()) {
			String[] lineElements = line.split(" ");
			line = br.readLine();
			// Create a new element based on the 1st word in the line
			switch (lineElements[0]) {
				case "LINE":	if(lineElements.length != 8) {
									JOptionPane.showMessageDialog(null, "File " + file.getName() + " is illformed.", 
											"Error", 
											JOptionPane.ERROR_MESSAGE);
									return;
								}
								int x1 = (int) Double.parseDouble(lineElements[1]);
								int y1 = (int) Double.parseDouble(lineElements[2]);
								int x2 = (int) Double.parseDouble(lineElements[3]);
								int y2 = (int) Double.parseDouble(lineElements[4]);
								int r = Integer.parseInt(lineElements[5]);
								int g = Integer.parseInt(lineElements[6]);
								int b = Integer.parseInt(lineElements[7]);
								model.setNumberOfLines();
								model.add(new Line("Line " + model.numberOfLines(), x1, y1, x2, y2, 
										new Color(r, g, b)));
								break;
				case "CIRCLE":	if(lineElements.length != 7) {
									JOptionPane.showMessageDialog(null, "File " + file.getName() + " is illformed.", 
											"Error", 
											JOptionPane.ERROR_MESSAGE);
									return;
								}
								int x = (int) Double.parseDouble(lineElements[1]);
								int y = (int) Double.parseDouble(lineElements[2]);
								int radius = (int) Double.parseDouble(lineElements[3]);
								r = Integer.parseInt(lineElements[4]);
								g = Integer.parseInt(lineElements[5]);
								b = Integer.parseInt(lineElements[6]);
								model.setNumberOfCircles();
								model.add(new Circle("Circle " + model.numberOfCircles(), x, y, radius, 
										new Color(r, g, b)));
								break;
				case "FCIRCLE":	if(lineElements.length != 10) {
									JOptionPane.showMessageDialog(null, "File " + file.getName() + " is illformed.", 
											"Error", 
											JOptionPane.ERROR_MESSAGE);
									return;
								}
								x = (int) Double.parseDouble(lineElements[1]);
								y = (int) Double.parseDouble(lineElements[2]);
								radius = (int) Double.parseDouble(lineElements[3]);
								int r1 = Integer.parseInt(lineElements[4]);
								int g1 = Integer.parseInt(lineElements[5]);
								int b1 = Integer.parseInt(lineElements[6]);
								int r2 = Integer.parseInt(lineElements[7]);
								int g2 = Integer.parseInt(lineElements[8]);
								int b2 = Integer.parseInt(lineElements[9]);
								model.setNumberOfFilledCircles();
								model.add(new FilledCircle("Filled circle " + model.numberOfFilledCircles(), 
										x, y, radius, 
										new Color(r2, g2, b2), new Color(r1, g1, b1)));
								break;
				default: continue;
			}
		}
		br.close();
	}
	
	/**
	 * Method used to write to the <code>jvd</code> file. It writes the specification
	 * of every component in the model in to the file.
	 * 
	 * @param file file that will be created
	 * @param bw buffered writer that will be used to write to the file
	 * @throws IOException if it is impossible to write to the file
	 */
	private void writeToFile(File file, BufferedWriter bw) throws IOException {
		bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
		for(int i = 0; i < model.getSize(); i++) {
			GeometricalObject object = model.getObject(i);
			bw.write(object.toFile() + "\n");
		}
		bw.close();
	}
	
	/**
	 * Method used to export the current image to the specified file with of the specified type.
	 * Supported file formats are <code>jpg</code>, <code>gif</code> and <code>png</code>. Image will
	 * be resized to the minimal possible size at which every component is fully visible.
	 * 
	 * @param file file that will be created
	 * @param image buffered image that will be used to create the file
	 * @param canvas canvas containing the picture
	 */
	private void exportImage(File file, BufferedImage image, JDrawingCanvas canvas) {
		BoundingBox boundingBox = model.minBoundingBox();
		image = new BufferedImage(boundingBox.getX2() - boundingBox.getX1() + 1,
				boundingBox.getY2() - boundingBox.getY1() + 1, BufferedImage.TYPE_3BYTE_BGR);
		for(int i = 0; i < model.getSize(); i++) {
			GeometricalObject object = model.getObject(i);
			object.modifyComponent(boundingBox.getX1(), boundingBox.getY1());
		}
		Graphics2D g = image.createGraphics();
		canvas.paintAll(g);
		g.dispose();
		String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		try {
			ImageIO.write(image, extension, file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Export of file" + file.getName() + "failed.", 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		for(int i = 0; i < model.getSize(); i++) {
			GeometricalObject object = model.getObject(i);
			object.modifyComponent(-boundingBox.getX1(), -boundingBox.getY1());
		}
	}
}
