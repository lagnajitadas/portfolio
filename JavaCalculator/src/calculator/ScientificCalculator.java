package calculator;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ScientificCalculator extends JFrame {

	private static final long serialVersionUID = 1L;

	public ScientificCalculator() {

		setTitle("Scientific Calculator");
		setSize(420, 850);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(255, 235, 245));

		JTextField display = new JTextField();
		display.setBounds(20, 20, 360, 50);
		display.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
		display.setHorizontalAlignment(JTextField.RIGHT);
		display.setEditable(false);
		add(display);

		JPanel panel = new JPanel();
		panel.setBounds(20, 90, 360, 650);
		panel.setLayout(new GridLayout(7, 4, 10, 10));
		panel.setBackground(new Color(255, 235, 245));
		add(panel);

		String[] buttons = {

				"7", "8", "9", "C",

				"4", "5", "6", ".",

				"1", "2", "3", "+/-",

				"0", "sin", "cos", "tan",

				"log", "ln", "π", "√",

				"n!", "EXP", "Back", "x²",

				"x³", "1/x", "", ""

		};

		for (String text : buttons) {

			JButton btn = new JButton(text);

			btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
			btn.setBackground(new Color(255, 192, 203));
			btn.setForeground(new Color(199, 21, 133));

			if (text.isEmpty()) {
				btn.setEnabled(false);
				btn.setVisible(false);
			}

			panel.add(btn);

			btn.addActionListener(e -> {

				switch (text) {

				case "Back":
					dispose();
					break;

				case "π":
					display.setText(formatNumber(Math.PI));
					break;

				case "EXP":
					display.setText(formatNumber(Math.E));
					break;

				case "C":
					display.setText("");
					break;

				case "0":
				case "1":
				case "2":
				case "3":
				case "4":
				case "5":
				case "6":
				case "7":
				case "8":
				case "9":
				case ".":
					display.setText(display.getText() + text);
					break;
				case "sin":
					if (!display.getText().isEmpty()) {
						double num = Double.parseDouble(display.getText());
						double result = Math.sin(Math.toRadians(num));
						display.setText(formatNumber(result));
					}
					break;
				case "cos":
					if (!display.getText().isEmpty()) {
						double num = Double.parseDouble(display.getText());
						double result = Math.cos(Math.toRadians(num));
						display.setText(formatNumber(result));
					}
					break;
				case "tan":
					if (!display.getText().isEmpty()) {
						double num = Double.parseDouble(display.getText());
						double result = Math.tan(Math.toRadians(num));

						if (Math.abs(result - Math.round(result)) < 1E-10) {
							display.setText(String.valueOf((long) Math.round(result)));
						} else {
							display.setText(String.format("%.10f", result));
						}
					}
					break;
				case "log":
					if (!display.getText().isEmpty()) {
						double num = Double.parseDouble(display.getText());

						if (num > 0) {
							display.setText(formatNumber(Math.log10(num)));
						} else {
							display.setText("Error");
						}
					}
					break;
				case "ln":
					if (!display.getText().isEmpty()) {
						double num = Double.parseDouble(display.getText());

						if (num > 0) {
							display.setText(formatNumber(Math.log(num)));
						} else {
							display.setText("Error");
						}
					}
					break;
				case "n!":
					if (!display.getText().isEmpty()) {

						int n = Integer.parseInt(display.getText());

						if (n < 0) {
							display.setText("Error");
						} else {

							long fact = 1;

							for (int i = 1; i <= n; i++) {
								fact *= i;
							}

							display.setText(String.valueOf(fact));
						}
					}
					break;
				case "√":
					if (!display.getText().isEmpty()) {

						double num = Double.parseDouble(display.getText());

						if (num >= 0) {
							display.setText(formatNumber(Math.sqrt(num)));
						} else {
							display.setText("Error");
						}
					}
					break;
				case "+/-":

					if (!display.getText().isEmpty()) {

						double num = Double.parseDouble(display.getText());
						double result = -num;

						if (Math.abs(result - Math.round(result)) < 1E-10) {
							display.setText(String.valueOf((long) Math.round(result)));
						} else {
							display.setText(
									String.format("%.10f", result).replaceAll("0+$", "").replaceAll("\\.$", ""));
						}
					}

					break;
				case "x²":

					if (!display.getText().isEmpty()) {

						double num = Double.parseDouble(display.getText());

						display.setText(formatNumber(num * num));

					}

					break;
				case "x³":

					if (!display.getText().isEmpty()) {

						double num = Double.parseDouble(display.getText());

						display.setText(formatNumber(num * num * num));

					}

					break;
				case "1/x":

					if (!display.getText().isEmpty()) {

						double num = Double.parseDouble(display.getText());

						if (num == 0)
							display.setText("Error");
						else
							display.setText(formatNumber(1 / num));

					}

					break;

				default:

					break;
				}

			});

		}
		display.setFocusable(true);

		display.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {

				char ch = e.getKeyChar();

				if ((ch >= '0' && ch <= '9') || ch == '.') {
					display.setText(display.getText() + ch);
				}

				if (ch == '\b') { // Backspace
					String txt = display.getText();
					if (!txt.isEmpty()) {
						display.setText(txt.substring(0, txt.length() - 1));
					}
				}
			}
		});
		display.requestFocusInWindow();

		setVisible(true);
	}

	private String formatNumber(double num) {

		if (Math.abs(num - Math.round(num)) < 1E-10) {
			return String.valueOf((long) Math.round(num));
		}

		return String.format("%.10f", num).replaceAll("0+$", "").replaceAll("\\.$", "");
	}

}
