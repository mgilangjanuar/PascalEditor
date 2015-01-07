import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

/**
 *
 * contoh method main pada kelas tester :
 *
 * public static void main (String[] args){ 
 *     PascalEditor editor = new PascalEditor(); 
 *     editor.buildApp();
 * } 
 * 
 * resource : http://stackoverflow.com/a/14403065 (Syntax highlight)
 * 
 * @author muhammadgilangjanuar at SEMARDEV
 * @since 2015
 *
 */
 public class PascalEditor extends JFrame {
	String codered;
	String codeblue;
	JPanel panelatas;
	JPanel panelbawah;
	JPanel paneltengah;
	JTextPane textarea;
	JTextField textfield;
	JTextField textfieldName;
	JLabel dummy;
	JButton browse;
	JButton save;
	JButton open;
	JButton compile;

	/**
	 * Mendefinisikan semua instance variable yang akan digunakan atau menyusun
	 * program.
	 */
	public PascalEditor() {
		panelatas = new JPanel();
		paneltengah = new JPanel(new GridLayout());
		panelbawah = new JPanel();
		textarea = new JTextPane();
		textfield = new JTextField(20);
		textfieldName = new JTextField(10);
		dummy = new JLabel("\\");
		browse = new JButton("Browse");
		save = new JButton("Save");
		open = new JButton("Open");
		compile = new JButton("Compile + Run");
		try {
			FileReader fr = new FileReader("config.smrdv");
			Scanner in = new Scanner(fr);
			codered = in.nextLine();
			codeblue = in.nextLine();
		} catch (FileNotFoundException e) {
			try {
				PrintWriter pw = new PrintWriter("config.smrdv");
				pw.println("read|readln|write|writeln|crt|for|to|do|while|repeat|until|if|else|case|of");
				pw.println("begin|end|.|;|'|uses|program|function|procedure|var|integer|string|longint|float|char|smallint|byte|shortint|cardinal|int64|double");
				pw.close();
				codered = "read|readln|write|writeln|crt|for|to|do|while|repeat|until|if|else|case|of";
				codeblue = "begin|end|.|;|'|uses|program|function|procedure|var|integer|string|longint|float|char|smallint|byte|shortint|cardinal|int64|double";
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null,
						"file not found exception on ln 105");
			}
		}
	}

	/**
	 * Method yang berfungsi untuk menyimpan file.
	 */
	public void savefunction() {
		try {
			PrintWriter pw = new PrintWriter(textfield.getText()
					+ dummy.getText() + textfieldName.getText());
			pw.println(textarea.getText());
			pw.close();
			JOptionPane.showMessageDialog(null, "file saved");
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null,
						"file not found exception on ln 122");
		}
	}

	/**
	 * Method yang berfungsi untuk membuka file text yang ada di local machine.
	 */
	public void openfunction() {
		JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();

			textfieldName.setText(selectedFile.getName());
			textfield.setText(selectedFile.getAbsolutePath().substring(
					0,
					selectedFile.getAbsolutePath().length()
							- selectedFile.getName().length() - 1));

			textarea.setText("");
			FileReader file;
			try {
				file = new FileReader(selectedFile.getAbsolutePath());
				Scanner in = new Scanner(file);
				while (in.hasNextLine()) {
					String s = in.nextLine();
					textarea.setText(textarea.getText() + s + "\n");
				}
				in.close();
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(null,
						"file not found exception on ln 152");
			}
		}
	}

	/**
	 * Method yang melakukan fungsi compile dan run sekaligus
	 */
	public void compilefunction() {
		try {
			PrintWriter pw = new PrintWriter(textfield.getText()
					+ dummy.getText() + "PascalEditorConfig.bat");
			pw.println("@ECHO OFF");
			pw.println("cd " + textfield.getText());
			pw.println("color f0");
			pw.println("fpc " + textfieldName.getText());
			pw.println("cls");
			pw.println(textfieldName.getText().substring(0,
					textfieldName.getText().indexOf('.'))
					+ ".exe");
			pw.println("set /p key=Press return key to exit...");
			pw.println("exit");
			pw.close();
			Runtime.getRuntime().exec(
					"cmd /c start " + textfield.getText() + dummy.getText()
							+ "PascalEditorConfig.bat");
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null,
						"file not found exception on ln 180");
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,
						"fatal error on ln 183");
		}
	}

	/**
	 * Method yang membangun panel bagian atas.
	 */
	public void buildPanelAtas() {
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				savefunction();
			}
		});

		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openfunction();
			}
		});

		compile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				compilefunction();
			}
		});

		panelatas.add(save);
		panelatas.add(open);
		panelatas.add(compile);
	}

	/**
	 * Helper method dari fitur syntax highlight.
	 */
	private int findLastNonWordChar(String text, int index) {
		while (--index >= 0) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) {
				break;
			}
		}
		return index;
	}

	/**
	 * Helper method dari fitur syntax highlight.
	 */
	private int findFirstNonWordChar(String text, int index) {
		while (index < text.length()) {
			if (String.valueOf(text.charAt(index)).matches("\\W")) {
				break;
			}
			index++;
		}
		return index;
	}

	/**
	 * Method yang membagun panel bagian tengah.
	 */
	public void buildPanelTengah() {
		final StyleContext cont = StyleContext.getDefaultStyleContext();
		final AttributeSet attr = cont.addAttribute(cont.getEmptySet(),
				StyleConstants.Foreground, Color.RED);
		final AttributeSet attrBlue = cont.addAttribute(cont.getEmptySet(),
				StyleConstants.Foreground, Color.BLUE);
		final AttributeSet attrBlack = cont.addAttribute(cont.getEmptySet(),
				StyleConstants.Foreground, Color.BLACK);
		DefaultStyledDocument doc = new DefaultStyledDocument() {

			@Override
			public void insertString(int offset, String str, AttributeSet a)
					throws BadLocationException {
				super.insertString(offset, str, a);

				String text = getText(0, getLength());
				int before = findLastNonWordChar(text, offset);
				if (before < 0)
					before = 0;
				int after = findFirstNonWordChar(text, offset + str.length());
				int wordL = before;
				int wordR = before;

				while (wordR <= after) {
					if (wordR == after
							|| String.valueOf(text.charAt(wordR))
									.matches("\\W")) {
						if (text.substring(wordL, wordR).matches(
								"(\\W)*(" + codered + ")")) {
							setCharacterAttributes(wordL, wordR - wordL, attr,
									false);
						} else if (text.substring(wordL, wordR).matches(
								"(\\W)*(" + codeblue + ")")) {
							setCharacterAttributes(wordL, wordR - wordL,
									attrBlue, false);
						} else {
							setCharacterAttributes(wordL, wordR - wordL,
									attrBlack, false);
						}
						wordL = wordR;
					}
					wordR++;
				}
			}

			@Override
			public void remove(int offs, int len) throws BadLocationException {
				super.remove(offs, len);

				String text = getText(0, getLength());
				int before = findLastNonWordChar(text, offs);
				if (before < 0)
					before = 0;
				int after = findFirstNonWordChar(text, offs);

				if (text.substring(before, after).matches(
						"(\\W)*(" + codered + ")")) {
					setCharacterAttributes(before, after - before, attr, false);
				} else if (text.substring(before, after).matches(
						"(\\W)*(" + codeblue + ")")) {
					setCharacterAttributes(before, after - before, attrBlue,
							false);
				} else {
					setCharacterAttributes(before, after - before, attrBlack,
							false);
				}
			}
		};

		textarea.setStyledDocument(doc);
		textarea.setFont(new Font("Courier New", 0, 16));
		paneltengah.add(new JScrollPane(textarea));
	}

	/**
	 * Method pembagun panel bagian bawah.
	 */
	public void buildPanelBawah() {
		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechooser = new JFileChooser();
				filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				filechooser.setAcceptAllFileFilterUsed(false);
				if (filechooser.showOpenDialog(filechooser) == JFileChooser.APPROVE_OPTION) {
					textfield.setText(filechooser.getSelectedFile()
							.getAbsolutePath());
				}
			}
		});

		panelbawah.add(textfield);
		panelbawah.add(dummy);
		panelbawah.add(textfieldName);
		panelbawah.add(browse);
	}

	/**
	 * Method yang membagun menubar pada program.
	 */
	public MenuBar buildMenubar() {
		MenuBar mb = new MenuBar();
		Menu about = new Menu("About");
		Menu setting = new Menu("Setting");
		Menu execute = new Menu("Execute");
		MenuItem compile = new MenuItem("Compile");
		compile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PrintWriter pw = new PrintWriter(textfield.getText()
							+ dummy.getText() + "PascalEditorConfig.bat");
					pw.println("@ECHO OFF");
					pw.println("cd " + textfield.getText());
					pw.println("color f0");
					pw.println("cls");
					pw.println("fpc " + textfieldName.getText());
					pw.println("set /p key=Press return key to exit...");
					pw.println("exit");
					pw.close();
					Runtime.getRuntime().exec(
							"cmd /c start " + textfield.getText()
									+ dummy.getText()
									+ "PascalEditorConfig.bat");
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null,
						"file not found exception on ln 375");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,
						"fatal error on ln 378");
				}
			}
		});

		MenuItem testCompiler = new MenuItem("Test Compiler");
		testCompiler.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PrintWriter pw = new PrintWriter(System
							.getProperty("user.home") + "\\TestCompilerFPC.pas");
					pw.println("begin writeln('Success!'); writeln('-Pascal Editor Test Compiler'); writeln(); end.");
					pw.close();

					PrintWriter pw1 = new PrintWriter(System
							.getProperty("user.home") + "\\TestCompilerFPC.bat");
					pw1.println("@ECHO OFF");
					pw1.println("cd " + System.getProperty("user.home"));
					pw1.println("color f0");
					pw1.println("fpc TestCompilerFPC.pas");
					pw1.println("cls");
					pw1.println("TestCompilerFPC.exe");
					pw1.println("set /p key=Press return key to exit...");
					pw1.println("exit");
					pw1.close();
					Runtime.getRuntime().exec(
							"cmd /c start " + System.getProperty("user.home")
									+ "\\TestCompilerFPC.bat");
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null,
						"file not found exception on ln 410");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,
						"fatal error on ln 413");
				}
			}
		});

		MenuItem syntax = new MenuItem("Syntax");
		syntax.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Syntax");
				JPanel panelatas = new JPanel(new GridLayout(2, 1));
				JPanel panelbawah = new JPanel();
				JTextArea text = new JTextArea();
				JTextArea text1 = new JTextArea();
				JButton ok = new JButton("OK");
				ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						codered = text.getText();
						codeblue = text1.getText();
						frame.dispose();
					}
				});
				text.setText(codered);
				text1.setText(codeblue);
				panelatas.add(new JScrollPane(text));
				panelatas.add(new JScrollPane(text1));
				panelbawah.add(ok);
				frame.add(panelatas, BorderLayout.CENTER);
				frame.add(panelbawah, BorderLayout.SOUTH);
				frame.setVisible(true);
				frame.setSize(350, 200);
				frame.setResizable(false);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setLocationRelativeTo(null);
			}
		});
		MenuItem font = new MenuItem("Font");
		font.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Font Setting");
				JTextField font = new JTextField(7);
				font.setText("Courier New");
				JTextField style = new JTextField(7);
				style.setText("0");
				JTextField size = new JTextField(7);
				size.setText("16");
				JButton ok = new JButton("OK");
				ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							textarea.setFont(new Font(font.getText(), Integer
									.parseInt(style.getText().trim()), Integer
									.parseInt(size.getText().trim())));
							frame.dispose();
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null,
						        "fatal error on ln 476");
							System.exit(0);
						}
					}
				});

				frame.add(font);
				frame.add(style);
				frame.add(size);
				frame.add(ok);

				frame.setLayout(new GridLayout(4, 1));
				frame.setVisible(true);
				frame.setSize(200, 150);
				frame.setResizable(false);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setLocationRelativeTo(null);
			}
		});
		MenuItem mi = new MenuItem("About program");
		mi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("About");
				JLabel text = new JLabel();
				text.setHorizontalAlignment(JLabel.CENTER);
				text.setText("Pascal Editor");
				JLabel text1 = new JLabel();
				text1.setHorizontalAlignment(JLabel.CENTER);
				text1.setText("SEMARDEV Project © 2015");

				frame.add(text);
				frame.add(text1);
				frame.setLayout(new GridLayout(2, 1));
				frame.setVisible(true);
				frame.setSize(300, 100);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.setLocationRelativeTo(null);
			}
		});
		setting.add(font);
		setting.add(syntax);
		setting.add(testCompiler);
		about.add(mi);
		execute.add(compile);
		mb.add(execute);
		mb.add(setting);
		mb.add(about);
		return mb;
	}

	/**
	 * Method yang membagun hot keys / key binding pada program (masih dalam
	 * tahap uji coba).
	 */
	public void buildShortcut() {
		// save
		getRootPane().getActionMap().put("save", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				savefunction();
			}
		});
		InputMap im = getRootPane().getInputMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK),
				"save");

		// open
		getRootPane().getActionMap().put("open", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openfunction();
			}
		});
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK),
				"open");

		// compile
		getRootPane().getActionMap().put("compile", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				compilefunction();
			}
		});
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "compile");
	}

	/**
	 * Method utama yang harus dipanggil saat kelas ini dibuat sebagai objek.
	 * Karena kelas ini yang menyatukan seluruh fungsi method untuk membentuk
	 * program menjadi satu kesatuan.
	 */
	public void buildApp() {
		setMenuBar(buildMenubar());
		buildPanelAtas();
		buildPanelTengah();
		buildPanelBawah();
		buildShortcut();
		add(panelatas, BorderLayout.NORTH);
		add(paneltengah, BorderLayout.CENTER);
		add(panelbawah, BorderLayout.SOUTH);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					PrintWriter pw = new PrintWriter("config.smrdv");
					pw.println(codered);
					pw.println(codeblue);
					pw.close();
				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(null,
						"file not found exception on ln 589");
				}
			}

		});

		setTitle("Pascal IDE");
		setSize(600, 400);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
