import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import javafx.scene.layout.BorderWidths;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que en la que se va a ejecuta toda la mecanica de la calculadora
 * 
 */
public class Engine implements ActionListener {
    // Marco de la ventana
    private JFrame frame;
    // Panel general que ocupa toda la ventana
    private JPanel contentPanel;
    // Panel norte que contiene el display
    private JPanel displayPanel;
    // Panel sur que contiene los botones
    private JPanel buttonPanel;
    // Display
    private JLabel display;
    // Botones
    private JButton n0;
    private JButton n1;
    private JButton n2;
    private JButton n3;
    private JButton n4;
    private JButton n5;
    private JButton n6;
    private JButton n7;
    private JButton n8;
    private JButton n9;
    private JButton divide;
    private JButton multiply;
    private JButton subtract;
    private JButton add;
    private JButton equal;
    private JButton reset;
    private JButton negative;

    // Tipos de boton
    private enum ButtonType {
        REGULAR, OPERATOR
    }

    // Almacenar temporalmente ciertos valores
    private int num1, num2, result;
    private char operation;

    private String calculo;

    public Engine() {
        // Marco de la ventana
        this.frame = new JFrame("Calculadora");
        // Panel general que ocupa toda la ventana
        this.contentPanel = new JPanel();
        // Panel norte que contiene el display
        this.displayPanel = new JPanel();
        // Panel sur que contiene los botones
        this.buttonPanel = new JPanel();
        // Display
        this.display = new JLabel("0");
        // Botones
        this.n0 = new JButton("0");
        this.n1 = new JButton("1");
        this.n2 = new JButton("2");
        this.n3 = new JButton("3");
        this.n4 = new JButton("4");
        this.n5 = new JButton("5");
        this.n6 = new JButton("6");
        this.n7 = new JButton("7");
        this.n8 = new JButton("8");
        this.n9 = new JButton("9");
        this.divide = new JButton(" / ");
        this.multiply = new JButton(" X ");
        this.subtract = new JButton(" - ");
        this.add = new JButton(" + ");
        this.equal = new JButton("=");
        this.reset = new JButton("C");
        this.negative = new JButton("-");
        this.calculo = "";
        // configuramos la ventana
        setSettings();
        addActionEvent();
    }

    /**
     * Este método establece la configuración principal de todos
     * los componentes visuales de la ventana
     */
    public void setSettings() {
        // Ponemos los layouts
        this.contentPanel.setLayout(new BorderLayout());
        this.displayPanel.setLayout(new FlowLayout());
        this.buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        // setemaos tamaños
        this.display.setPreferredSize(new Dimension(800, 150));
        this.display.setFont(new Font("Serif", Font.BOLD, 70));
        this.display.setHorizontalAlignment(JTextField.RIGHT);
        this.display.setForeground(new Color(255, 255, 255));
        this.contentPanel.setBackground(new Color(0, 113, 45));
        // añimos panel de texto al display
        this.displayPanel.add(this.display);
        // añadimos los botones
        this.buttonPanel.add(this.n7);
        this.buttonPanel.add(this.n8);
        this.buttonPanel.add(this.n9);
        this.buttonPanel.add(this.multiply);
        this.buttonPanel.add(this.n4);
        this.buttonPanel.add(this.n5);
        this.buttonPanel.add(this.n6);
        this.buttonPanel.add(this.subtract);
        this.buttonPanel.add(this.n1);
        this.buttonPanel.add(this.n2);
        this.buttonPanel.add(this.n3);
        this.buttonPanel.add(this.add);
        this.buttonPanel.add(this.reset);
        this.buttonPanel.add(this.n0);
        this.buttonPanel.add(this.divide);
        this.buttonPanel.add(this.equal);
        this.buttonPanel.add(this.negative);
        this.buttonPanel.setBackground(new Color(0, 113, 45));
        this.displayPanel.setBackground(new Color(0, 113, 45));
        this.contentPanel.add(this.display, BorderLayout.NORTH);
        this.contentPanel.add(this.buttonPanel, BorderLayout.CENTER);
        // añadimos el diseño
        setFeaturesButton(this.n0, ButtonType.REGULAR);
        setFeaturesButton(this.n1, ButtonType.REGULAR);
        setFeaturesButton(this.n2, ButtonType.REGULAR);
        setFeaturesButton(this.n3, ButtonType.REGULAR);
        setFeaturesButton(this.n4, ButtonType.REGULAR);
        setFeaturesButton(this.n5, ButtonType.REGULAR);
        setFeaturesButton(this.n6, ButtonType.REGULAR);
        setFeaturesButton(this.n7, ButtonType.REGULAR);
        setFeaturesButton(this.n8, ButtonType.REGULAR);
        setFeaturesButton(this.n9, ButtonType.REGULAR);
        setFeaturesButton(this.add, ButtonType.OPERATOR);
        setFeaturesButton(this.multiply, ButtonType.OPERATOR);
        setFeaturesButton(this.subtract, ButtonType.OPERATOR);
        setFeaturesButton(this.equal, ButtonType.OPERATOR);
        setFeaturesButton(this.reset, ButtonType.OPERATOR);
        setFeaturesButton(this.divide, ButtonType.OPERATOR);
        setFeaturesButton(this.negative, ButtonType.REGULAR);
        // configuracion de la ventana
        this.frame.add(this.contentPanel);
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(new Dimension(400, 700));
    }

    /**
     * Dependiendo del boton que pongamos cambiara su estilo
     * 
     * @param _button El botón sobre el que se van a cambiar las características
     * @param _type   De qué tipo es el botón sobre el que se van a cambiar
     *                las características
     */
    public void setFeaturesButton(JButton _button, ButtonType _type) {
        if (_type.equals(ButtonType.OPERATOR)) {
            _button.setBackground(new Color(255, 145, 0));
            _button.setFont(new Font("", Font.BOLD, 20));
            _button.setForeground(new Color(255, 251, 230));
        } else {
            _button.setBackground(new Color(213, 237, 159));
            _button.setFont(new Font("", Font.BOLD, 20));
            _button.setForeground(new Color(0, 113, 45));
        }
    }

    public void addActionEvent() {
        this.n0.addActionListener(this);
        this.n1.addActionListener(this);
        this.n2.addActionListener(this);
        this.n3.addActionListener(this);
        this.n4.addActionListener(this);
        this.n5.addActionListener(this);
        this.n6.addActionListener(this);
        this.n7.addActionListener(this);
        this.n8.addActionListener(this);
        this.n9.addActionListener(this);
        this.add.addActionListener(this);
        this.reset.addActionListener(this);
        this.equal.addActionListener(this);
        this.divide.addActionListener(this);
        this.multiply.addActionListener(this);
        this.subtract.addActionListener(this);
        this.negative.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String input_text = e.getActionCommand();
        // si el numero es igual a borrar o al igual lo coges , si no es ninguno de esos
        // solo escribimos los que es
        if (source.equals(this.reset)) {
            this.display.setText("0");
            this.calculo = "";
        } else if (source.equals(this.equal)) {
            String cadena[] = this.display.getText().split(" ");
            this.num1 = Integer.parseInt(cadena[0]);
            this.num2 = Integer.parseInt(cadena[2]);
            this.operation = cadena[1].charAt(0);
            operation();
            this.calculo = this.result + "";
            this.display.setText(this.calculo);

        } else {
            this.calculo += input_text;
            this.display.setText(this.calculo);
        }
    }

    public void operation() {
        switch (this.operation) {
            case 'X':
                this.result = this.num1 * this.num2;
                break;
            case '/':
                this.result = this.num1 / this.num2;
                break;
            case '+':
                this.result = this.num1 + this.num2;
                break;
            case '-':
                this.result = this.num1 - this.num2;
                break;

            default:
                break;
        }
    }
}
