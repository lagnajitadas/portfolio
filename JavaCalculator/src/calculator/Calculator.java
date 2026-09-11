package calculator;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Timer;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.awt.*;

public class Calculator {

	public static void main(String[] args) {

		final double[] firstNumber = { 0 };
		final String[] operator = { "" };
		final boolean[] newNumber = { true };
		final double[] memory = {0};
		final StringBuilder history = new StringBuilder();
		final HashMap<String, JButton> buttonMap = new HashMap<>();
		final boolean[] darkMode = { false };

		JFrame frame = new JFrame("🎀 Hello Kitty Calculator 🎀");
		frame.setSize(430, 900);
		frame.setLocationRelativeTo(null);
		try {
		    Image icon = ImageIO.read(
		        Calculator.class.getResource("/calculator/kitty.jpg"));
		    frame.setIconImage(icon);
		} catch (Exception e) {
		    e.printStackTrace();
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.getContentPane().setBackground(new Color(255, 228, 240));

		JTextField display = new JTextField();
		display.setBounds(15,15,390,90);
		display.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
		
		
		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setEditable(false);
		display.setBackground(Color.WHITE);
		display.setForeground(new Color(255,20,147));
		display.setCaretColor(Color.WHITE);
		display.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(new Color(255,105,180), 3),
			    BorderFactory.createEmptyBorder(10,10,10,10)
			));
		display.setHorizontalAlignment(JTextField.CENTER);
		display.setText("Welcome");

		frame.add(display);
		JLabel clockLabel = new JLabel();
		clockLabel.setBounds(15, 98, 390, 25);
		clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		clockLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		clockLabel.setForeground(new Color(199, 21, 133));

		frame.add(clockLabel);
		frame.setFocusable(true);
		frame.requestFocusInWindow();
		

		String[] buttons = {
			    "7", "8", "9", "/",
			    "4", "5", "6", "*",
			    "1", "2", "3", "-",
			    "0", ".", "=", "+",
			    "C", "DEL", "%", "√",
			    "x²",  "x³", "H", "±",
			    "Dark", "1/x",
			    "MC", "MR", "M+", "M-",
			    "About", "Save",
			    "Scientific"
			    //"sin", "cos", "tan", "log",
			    //"ln", "π", "e", "n!"
			    
			};

		int x = 20;
		int y = 130;

		for (String text : buttons) {

			JButton btn = new JButton(text);
			buttonMap.put(text, btn);
			btn.setBackground(new Color(255,192,203));
			btn.setForeground(new Color(199,21,133));
			btn.setFocusPainted(false);
			btn.setBorder(BorderFactory.createLineBorder(new Color(255,105,180),2));
			btn.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
			btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			if (text.equals("Scientific")) {
			    btn.setBounds(x, y, 175, 60);
			} else {
			    btn.setBounds(x, y, 80, 60);
			}
			btn.addMouseListener(new java.awt.event.MouseAdapter() {

			    @Override
			    public void mouseEntered(java.awt.event.MouseEvent e) {
			        btn.setBackground(new Color(255,105,180));
			        btn.setForeground(Color.WHITE);
			    }

			    @Override
			    public void mouseExited(java.awt.event.MouseEvent e) {
			    	if (darkMode[0]) {
			            btn.setBackground(new Color(90, 90, 90));
			            btn.setForeground(Color.WHITE);
			        } else {
			            btn.setBackground(new Color(255,192,203));
			            btn.setForeground(new Color(199,21,133));
			        }  
			    }
			});
			

			btn.addActionListener(e -> {

				String value = btn.getText();
				

				// Number
				if (value.matches("[0-9.]")) {
					if (display.getText().equals("Welcome")) {
					    display.setHorizontalAlignment(JTextField.RIGHT);
					    display.setText("");
					}

					if (newNumber[0]) {
						display.setText(value);
						newNumber[0] = false;
					} else {
						display.setText(display.getText() + value);
					}
				}

				// Operator
				else if (value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/")) {

					if (!display.getText().isEmpty()) {
						firstNumber[0] = Double.parseDouble(display.getText());
						operator[0] = value;
						newNumber[0] = true;
					}
				}

				// Equal
				else if (value.equals("=")) {

					if (!display.getText().isEmpty()) {

						double secondNumber = Double.parseDouble(display.getText());

						double result = 0;

						switch (operator[0]) {

						case "+":
							result = firstNumber[0] + secondNumber;
							break;

						case "-":
							result = firstNumber[0] - secondNumber;
							break;

						case "*":
							result = firstNumber[0] * secondNumber;
							break;

						case "/":
							if (secondNumber == 0) {
								display.setText("Cannot divide by zero");
								newNumber[0] = true;
								return;
							}
							result = firstNumber[0] / secondNumber;
							break;
						}
						String expression =
						        (firstNumber[0] == (long) firstNumber[0] ?
						                String.valueOf((long) firstNumber[0]) :
						                String.valueOf(firstNumber[0]))
						        + " " + operator[0] + " " +
						        (secondNumber == (long) secondNumber ?
						                String.valueOf((long) secondNumber) :
						                String.valueOf(secondNumber))
						        + " = " +
						        (result == (long) result ?
						                String.valueOf((long) result) :
						                String.valueOf(result));

						history.append(expression).append("\n");

						if (result == (long) result) {
						    display.setText(String.valueOf((long) result));
						} else {
						    display.setText(String.valueOf(result));
						}

						

						newNumber[0] = true;
					}
				}

				// Clear
				else if (value.equals("C")) {

					display.setText("");
					firstNumber[0] = 0;
					operator[0] = "";
					newNumber[0] = true;
				}
				// Backspace
				else if (value.equals("DEL")) {

				    String current = display.getText();

				    if (!current.isEmpty()) {
				        display.setText(current.substring(0, current.length() - 1));
				    }
				}
				// Percentage
				else if (value.equals("%")) {

				    if (!display.getText().isEmpty()) {

				        double number = Double.parseDouble(display.getText());
				        number = number / 100;

				        if (number == (long) number) {
				            display.setText(String.valueOf((long) number));
				        } else {
				            display.setText(String.valueOf(number));
				        }

				        newNumber[0] = true;
				    }
				}
				// Square Root
				else if (value.equals("√")) {

				    if (!display.getText().isEmpty()) {

				        double number = Double.parseDouble(display.getText());

				        if (number < 0) {
				            display.setText("Invalid");
				        } else {

				            double result = Math.sqrt(number);

				            String expression;

				            if (result == (long) result) {
				                expression = "√" + (long) number + " = " + (long) result;
				                display.setText(String.valueOf((long) result));
				            } else {
				                expression = "√" + number + " = " + result;
				                display.setText(String.valueOf(result));
				            }

				            history.append(expression).append("\n");
				            newNumber[0] = true;
				        }
				    }
				}
				// Square
				else if (value.equals("x²")) {

				    if (!display.getText().isEmpty()) {

				        double number = Double.parseDouble(display.getText());
				        double result = number * number;

				        String expression;

				        if (result == (long) result) {
				            expression = (long) number + "² = " + (long) result;
				            display.setText(String.valueOf((long) result));
				        } else {
				            expression = number + "² = " + result;
				            display.setText(String.valueOf(result));
				        }

				        history.append(expression).append("\n");
				        newNumber[0] = true;
				    }
				}
				else if (value.equals("x³")) {

				    if (!display.getText().isEmpty()) {

				        double number = Double.parseDouble(display.getText());
				        double result = number * number * number;

				        String expression;

				        if (result == (long) result) {
				            expression = (long) number + "³ = " + (long) result;
				            display.setText(String.valueOf((long) result));
				        } else {
				            expression = number + "³ = " + result;
				            display.setText(String.valueOf(result));
				        }

				        history.append(expression).append("\n");
				        newNumber[0] = true;  
				    }
				}
				// Positive / Negative
				else if (value.equals("±")) {

				    if (!display.getText().isEmpty()) {

				        double number = Double.parseDouble(display.getText());

				        number = -number;

				        if (number == (long) number) {
				            display.setText(String.valueOf((long) number));
				        } else {
				            display.setText(String.valueOf(number));
				        }
				    }
				}
				// Reciprocal
				else if (value.equals("1/x")) {

				    if (!display.getText().isEmpty()) {

				        double number = Double.parseDouble(display.getText());

				        if (number == 0) {
				            display.setText("Cannot divide by zero");
				        } else {

				            double result = 1 / number;

				            String expression;

				            if (number == (long) number) {
				                expression = "1/" + (long) number + " = " + result;
				            } else {
				                expression = "1/" + number + " = " + result;
				            }

				            history.append(expression).append("\n");

				            if (result == (long) result) {
				                display.setText(String.valueOf((long) result));
				            } else {
				                display.setText(String.valueOf(result));
				            }

				            newNumber[0] = true;   
				        }
				    }
				}
				

				// History
				else if (value.equals("H")) {

				    if (history.length() == 0) {
				        JOptionPane.showMessageDialog(frame,
				                "No calculations yet!",
				                "History",
				                JOptionPane.INFORMATION_MESSAGE);
				    } else {

				        JTextArea area = new JTextArea(history.toString());
				        area.setEditable(false);
				        area.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));

				        JOptionPane.showMessageDialog(
				                frame,
				                new JScrollPane(area),
				                "Calculation History",
				                JOptionPane.INFORMATION_MESSAGE);
				    }
				}
				// About
				else if (value.equals("About")) {

				    JOptionPane.showMessageDialog(
				            frame,
				            "🎀 Hello Kitty Calculator 🎀\n\n"
				            + "Developed by:\n"
				            + "Lagnajita Das\n\n"
				            + "BCA Student\n"
				            + "MAKAUT\n\n"
				            + "Version 1.0",
				            "About",
				            JOptionPane.INFORMATION_MESSAGE);
				}
				// Save History
				else if (value.equals("Save")) {

				    JFileChooser chooser = new JFileChooser();

				    int option = chooser.showSaveDialog(frame);

				    if (option == JFileChooser.APPROVE_OPTION) {

				        File file = chooser.getSelectedFile();

				        try {

				        	

				        	if (!file.getName().toLowerCase().endsWith(".txt")) {
				        	    file = new File(file.getAbsolutePath() + ".txt");
				        	}

				        	FileWriter writer = new FileWriter(file);
				            writer.write(history.toString());
				            writer.close();

				            JOptionPane.showMessageDialog(
				                    frame,
				                    "History saved successfully!",
				                    "Saved",
				                    JOptionPane.INFORMATION_MESSAGE);

				        } catch (Exception ex) {

				            JOptionPane.showMessageDialog(
				                    frame,
				                    "Error saving file!",
				                    "Error",
				                    JOptionPane.ERROR_MESSAGE);
				        }
				    }
				}
				// Memory Clear
				else if (value.equals("MC")) {
				    memory[0] = 0;
				    JOptionPane.showMessageDialog(frame, "Memory Cleared");
				}

				// Memory Recall
				else if (value.equals("MR")) {

				    if (memory[0] == (long) memory[0]) {
				        display.setText(String.valueOf((long) memory[0]));
				    } else {
				        display.setText(String.valueOf(memory[0]));
				    }

				    newNumber[0] = true;
				}

				// Memory Plus
				else if (value.equals("M+")) {

				    try {
				        if (!display.getText().isEmpty()) {
				            memory[0] += Double.parseDouble(display.getText());
				        }
				        newNumber[0] = true;

				    } catch (NumberFormatException ex) {
				        JOptionPane.showMessageDialog(
				                frame,
				                "Please enter a valid number first.");
				    }
				}

				// Memory Minus
				else if (value.equals("M-")) {

				    try {
				        if (!display.getText().isEmpty()) {
				            memory[0] -= Double.parseDouble(display.getText());
				        }
				        newNumber[0] = true;

				    } catch (NumberFormatException ex) {
				        JOptionPane.showMessageDialog(
				                frame,
				                "Please enter a valid number first.");
				    }
				}
				
				// Pi
				else if (value.equals("π")) {

				    display.setText(String.valueOf(Math.PI));
				    newNumber[0] = true;
				}
				// e
				else if (value.equals("e")) {

				    display.setText(String.valueOf(Math.E));
				    newNumber[0] = true;
				}
				// Log (Base 10)
				else if (value.equals("log")) {

				    if (!display.getText().isEmpty()) {

				        double number = Double.parseDouble(display.getText());

				        if (number <= 0) {
				            display.setText("Invalid");
				        } else {

				            double result = Math.log10(number);

				            history.append("log(" + number + ") = " + result + "\n");
				            display.setText(String.valueOf(result));
				            newNumber[0] = true;
				        }
				    }
				}
				// Natural Log
				else if (value.equals("ln")) {

				    if (!display.getText().isEmpty()) {

				        double number = Double.parseDouble(display.getText());

				        if (number <= 0) {
				            display.setText("Invalid");
				        } else {

				            double result = Math.log(number);

				            history.append("ln(" + number + ") = " + result + "\n");
				            display.setText(String.valueOf(result));
				            newNumber[0] = true;
				        }
				    }
				}
				// Sine
				else if (value.equals("sin")) {

				    if (!display.getText().isEmpty()) {

				        double number = Double.parseDouble(display.getText());

				        double result = Math.sin(Math.toRadians(number));

				        history.append("sin(" + number + ") = " + result + "\n");

				        display.setText(String.valueOf(result));

				        newNumber[0] = true;
				    }
				}
				// Cosine
				else if (value.equals("cos")) {

				    if (!display.getText().isEmpty()) {

				        double number = Double.parseDouble(display.getText());

				        double result = Math.cos(Math.toRadians(number));

				        history.append("cos(" + number + ") = " + result + "\n");

				        display.setText(String.valueOf(result));

				        newNumber[0] = true;
				    }
				}
				// Tangent
				else if (value.equals("tan")) {

				    if (!display.getText().isEmpty()) {

				        double number = Double.parseDouble(display.getText());

				        double result = Math.tan(Math.toRadians(number));

				        if (Math.abs(result - Math.round(result)) < 1E-10) {
				            history.append("tan(" + number + ") = " + (long) Math.round(result) + "\n");
				        } else {
				            history.append("tan(" + number + ") = " + String.format("%.10f", result) + "\n");
				        }
				        if (Math.abs(result - Math.round(result)) < 1E-10) {
				            display.setText(String.valueOf((long) Math.round(result)));
				        } else {
				            display.setText(String.format("%.10f", result));
				        }

				        newNumber[0] = true;
				    }
				}
				// Factorial
				else if (value.equals("n!")) {

				    if (!display.getText().isEmpty()) {

				        int number = Integer.parseInt(display.getText());

				        if (number < 0) {
				            display.setText("Invalid");
				        } else {

				            long fact = 1;

				            for (int i = 1; i <= number; i++) {
				                fact *= i;
				            }

				            history.append(number + "! = " + fact + "\n");
				            display.setText(String.valueOf(fact));
				            newNumber[0] = true;
				        }
				    }
				}
				else if (value.equals("Scientific")) {

				    ScientificCalculator scientific = new ScientificCalculator();
				    scientific.setVisible(true);
				}
				
				
				else if (value.equals("Dark")) {

				    darkMode[0] = !darkMode[0];

				    if (darkMode[0]) {

				        frame.getContentPane().setBackground(new Color(40,40,40));

				        display.setBackground(new Color(60,60,60));
				        display.setForeground(Color.WHITE);

				        for (JButton b : buttonMap.values()) {
				            b.setBackground(new Color(90,90,90));
				            b.setForeground(Color.WHITE);
				        }

				    } else {

				        frame.getContentPane().setBackground(new Color(255,228,240));

				        display.setBackground(Color.WHITE);
				        display.setForeground(new Color(255,20,147));

				        for (JButton b : buttonMap.values()) {
				            b.setBackground(new Color(255,192,203));
				            b.setForeground(new Color(199,21,133));
				        }
				    }
				}

				

				});

			

			frame.add(btn);

			if (text.equals("Scientific")) {
			    x = 20;
			    y += 65;
			} else {
			    x += 95;

			    if (x > 305) {
			        x = 20;
			        y += 65;
			    }
			}
		}
		InputMap im = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = frame.getRootPane().getActionMap();
		
		// NUMPAD +
		im.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ADD,0),"NUM+");
		am.put("NUM+", new AbstractAction() {
		    @Override
		    public void actionPerformed(java.awt.event.ActionEvent e) {
		        buttonMap.get("+").doClick();
		    }
		});

		// NUMPAD -
		im.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SUBTRACT,0),"NUM-");
		am.put("NUM-", new AbstractAction() {
		    @Override
		    public void actionPerformed(java.awt.event.ActionEvent e) {
		        buttonMap.get("-").doClick();
		    }
		});

		// NUMPAD *
		im.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_MULTIPLY,0),"NUM*");
		am.put("NUM*", new AbstractAction() {
		    @Override
		    public void actionPerformed(java.awt.event.ActionEvent e) {
		        buttonMap.get("*").doClick();
		    }
		});

		// NUMPAD /
		im.put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DIVIDE,0),"NUM/");
		am.put("NUM/", new AbstractAction() {
		    @Override
		    public void actionPerformed(java.awt.event.ActionEvent e) {
		        buttonMap.get("/").doClick();
		    }
		});
		// Numbers 1-9
		for (char c = '1'; c <= '9'; c++) {
		    String key = String.valueOf(c);
		    im.put(KeyStroke.getKeyStroke(c), key);
		    am.put(key, new AbstractAction() {
		        @Override
		        public void actionPerformed(java.awt.event.ActionEvent e) {
		            buttonMap.get(key).doClick();
		        }
		    });
		}

		// Dot
		im.put(KeyStroke.getKeyStroke('.'), ".");
		am.put(".", new AbstractAction() {
		    @Override
		    public void actionPerformed(java.awt.event.ActionEvent e) {
		        buttonMap.get(".").doClick();
		    }
		});

		// Operators
		String[] ops = {"+", "-", "*", "/"};

		for (String op : ops) {
		    im.put(KeyStroke.getKeyStroke(op.charAt(0)), op);
		    am.put(op, new AbstractAction() {
		        @Override
		        public void actionPerformed(java.awt.event.ActionEvent e) {
		            buttonMap.get(op).doClick();
		        }
		    });
		}

		// Enter
		im.put(KeyStroke.getKeyStroke("ENTER"), "=");
		am.put("=", new AbstractAction() {
		    @Override
		    public void actionPerformed(java.awt.event.ActionEvent e) {
		        buttonMap.get("=").doClick();
		    }
		});

		// Backspace
		im.put(KeyStroke.getKeyStroke("BACK_SPACE"), "DEL");
		am.put("DEL", new AbstractAction() {
		    @Override
		    public void actionPerformed(java.awt.event.ActionEvent e) {
		        buttonMap.get("DEL").doClick();
		    }
		});
		im.put(KeyStroke.getKeyStroke('0'), "0");
		am.put("0", new AbstractAction() {
		    @Override
		    public void actionPerformed(java.awt.event.ActionEvent e) {
		        buttonMap.get("0").doClick();
		    }
		});
		// ESC = Clear
		im.put(KeyStroke.getKeyStroke("ESCAPE"), "C");
		am.put("C", new AbstractAction() {
		    @Override
		    public void actionPerformed(java.awt.event.ActionEvent e) {
		        buttonMap.get("C").doClick();
		    }
		});
		DateTimeFormatter formatter =
		        DateTimeFormatter.ofPattern("dd MMM yyyy | hh:mm:ss a");

		Timer timer = new Timer(1000, e -> {
		    clockLabel.setText(
		            "Date & Time :  " + LocalDateTime.now().format(formatter));
		});

		timer.start();
		

		frame.setVisible(true);
		frame.requestFocus();
		frame.requestFocusInWindow();
	}
}