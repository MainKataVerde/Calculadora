import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Clase que en la que se va a ejecuta toda la mecanica de la calculadora
 */
public class Engine implements ActionListener {
    DefaultListCellRenderer renderer = new DefaultListCellRenderer();
    // Archivo con el historial
    File historialFile;
    // Marco de la ventana
    private JFrame frame;
    // Panel general que ocupa toda la ventana
    private JPanel contentPanel;
    // Panel Calculadora
    private JPanel calculadoraPanel;
    // Panel norte que contiene el display
    private JPanel displayPanel;
    // Panel sur que contiene los botones
    private JPanel buttonPanel;
    // Display
    private JLabel display;
    // Panel de historial
    private JPanel historyPanel;
    private JPanel historyPanelSur;
    // Scroll del historial
    private JScrollPane scroll;
    // Botton "borrar historial" y "añadir al display"
    private JButton clearHistory, addToDisplay;
    // elementos del historial
    private JList<String> historyList;
    private DefaultListModel<String> listModel;
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
    private JButton B2;
    private JButton B8;
    private JButton B10;
    private JButton B16;
    private JButton D;
    private JButton E;
    private JButton F;
    private JButton A;
    private JButton B;
    private JButton C;
    private JButton COPY;
    private JButton INFO;
    private JButton Owner;
    private JButton divide;
    private JButton multiply;
    private JButton subtract;
    private JButton add;
    private JButton equal;
    private JButton reset;
    private JButton negative;
    private JButton CASIO;

    // Tipos de boton
    private enum ButtonType {
        REGULAR, OPERATOR, BASE, ESPCIAL, HEXADECIMAL, MARCA
    }

    // Almacenar temporalmente ciertos valores
    private int num1, num2, result;
    // MODO = 1 -> DECIMAL , MODO = 2 -> BINARIO , MODO = 3 -> OCTAL , MODO = 4 ->
    // HEXADECIMAL
    private int modo;
    private String operation;
    // String con los elemento para calcular
    private String calculo;

    /**
     * Contructora de Engine
     */
    public Engine() {
        // Marco de la ventana
        this.frame = new JFrame("Calculadora");
        // Panel general que ocupa toda la ventana
        this.contentPanel = new JPanel();
        // Panel Calculadora
        this.calculadoraPanel = new JPanel();
        // Panel norte que contiene el display
        this.displayPanel = new JPanel();
        // Panel sur que contiene los botones
        this.buttonPanel = new JPanel();
        // Panel oeste que contiene el historial
        this.historyPanel = new JPanel();
        this.historyPanelSur = new JPanel();
        // Display
        this.display = new JLabel("0");
        // lista de historial
        this.listModel = new DefaultListModel<>();
        this.historyList = new JList<>(this.listModel);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        this.historyList.setCellRenderer(renderer);
        this.scroll = new JScrollPane(this.historyList);
        // Botones
        this.n0 = new JButton("0");
        this.COPY = new JButton("Copiar resultado");
        this.n1 = new JButton("1");
        this.n2 = new JButton("2");
        this.n3 = new JButton("3");
        this.n4 = new JButton("4");
        this.n5 = new JButton("5");
        this.n6 = new JButton("6");
        this.n7 = new JButton("7");
        this.n8 = new JButton("8");
        this.n9 = new JButton("9");
        this.B2 = new JButton("B2");
        this.B8 = new JButton("B8");
        this.B10 = new JButton("B10");
        this.B16 = new JButton("B16");
        this.D = new JButton("D");
        this.E = new JButton("E");
        this.F = new JButton("F");
        this.A = new JButton("A");
        this.B = new JButton("B");
        this.C = new JButton("C");
        this.divide = new JButton(" / ");
        this.multiply = new JButton(" x ");
        this.subtract = new JButton(" - ");
        this.add = new JButton(" + ");
        this.equal = new JButton("=");
        this.reset = new JButton("R");
        this.negative = new JButton("N");
        this.INFO = new JButton("INFO");
        this.Owner = new JButton("Owner");
        this.CASIO = new JButton("CASIO");
        this.calculo = "";
        this.addToDisplay = new JButton("Usar para calculo");
        this.clearHistory = new JButton("Borrar historial");
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
        this.contentPanel.setLayout(new GridLayout(1, 2));
        this.calculadoraPanel.setLayout(new BorderLayout());
        this.displayPanel.setLayout(new FlowLayout());
        this.buttonPanel.setLayout(new GridLayout(8, 4, 10, 10));
        // setemaos tamaños
        this.display.setPreferredSize(new Dimension(800, 150));
        this.display.setFont(new Font("Serif", Font.BOLD, 70));
        this.scroll.getViewport().getView().setBackground(new Color(0, 113, 45));
        this.scroll.getViewport().getView().setFont(new Font("Serif", Font.BOLD, 50));
        this.scroll.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.scroll.getViewport().getView().setForeground(new Color(255, 255, 255));
        this.scroll.setBorder(null);
        this.display.setHorizontalAlignment(JTextField.RIGHT);
        this.display.setForeground(new Color(255, 255, 255));
        this.calculadoraPanel.setBackground(new Color(0, 113, 45));
        this.contentPanel.setBackground(new Color(0, 0, 0));
        this.historyPanelSur.setBackground(new Color(0, 113, 45));
        this.historyPanel.setBackground(new Color(0, 113, 45));
        this.scroll.setPreferredSize(new Dimension(470, 390));
        // configuramos el panel de historial
        this.historyPanel.setLayout(new BorderLayout());
        this.historyPanelSur.setLayout(new FlowLayout());
        this.historyPanelSur.add(this.clearHistory);
        this.historyPanelSur.add(this.addToDisplay);
        this.historyPanelSur.add(this.COPY);
        this.historyPanel.add(this.historyPanelSur, BorderLayout.SOUTH);
        this.historyPanel.add(this.scroll, BorderLayout.CENTER);
        this.historyPanel.add(this.CASIO, BorderLayout.NORTH);
        // añimos panel de texto al display
        this.displayPanel.add(this.display);
        // añadimos los botones
        this.buttonPanel.add(this.B2);
        this.buttonPanel.add(this.B8);
        this.buttonPanel.add(this.B10);
        this.buttonPanel.add(this.B16);
        this.buttonPanel.add(this.D);
        this.buttonPanel.add(this.E);
        this.buttonPanel.add(this.F);
        this.buttonPanel.add(this.INFO);
        this.buttonPanel.add(this.A);
        this.buttonPanel.add(this.B);
        this.buttonPanel.add(this.C);
        this.buttonPanel.add(this.Owner);
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
        this.calculadoraPanel.add(this.display, BorderLayout.NORTH);
        this.calculadoraPanel.add(this.buttonPanel, BorderLayout.CENTER);
        this.contentPanel.add(this.calculadoraPanel);
        this.contentPanel.add(this.historyPanel);
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
        setFeaturesButton(this.B2, ButtonType.BASE);
        setFeaturesButton(this.B8, ButtonType.BASE);
        setFeaturesButton(this.B10, ButtonType.BASE);
        setFeaturesButton(this.B16, ButtonType.BASE);
        setFeaturesButton(this.A, ButtonType.HEXADECIMAL);
        setFeaturesButton(this.B, ButtonType.HEXADECIMAL);
        setFeaturesButton(this.C, ButtonType.HEXADECIMAL);
        setFeaturesButton(this.D, ButtonType.HEXADECIMAL);
        setFeaturesButton(this.E, ButtonType.HEXADECIMAL);
        setFeaturesButton(this.F, ButtonType.HEXADECIMAL);
        setFeaturesButton(this.INFO, ButtonType.ESPCIAL);
        setFeaturesButton(this.Owner, ButtonType.ESPCIAL);
        setFeaturesButton(this.add, ButtonType.OPERATOR);
        setFeaturesButton(this.multiply, ButtonType.OPERATOR);
        setFeaturesButton(this.subtract, ButtonType.OPERATOR);
        setFeaturesButton(this.equal, ButtonType.OPERATOR);
        setFeaturesButton(this.reset, ButtonType.OPERATOR);
        setFeaturesButton(this.divide, ButtonType.OPERATOR);
        setFeaturesButton(this.negative, ButtonType.OPERATOR);
        setFeaturesButton(this.addToDisplay, ButtonType.OPERATOR);
        setFeaturesButton(this.clearHistory, ButtonType.OPERATOR);
        setFeaturesButton(this.COPY, ButtonType.OPERATOR);
        setFeaturesButton(this.CASIO, ButtonType.MARCA);
        // configuracion de la ventana
        this.frame.add(this.contentPanel);
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(new Dimension(1500, 700));
    }

    /**
     * Dependiendo del boton que pongamos cambiara su estilo
     * 
     * @param _button El botón sobre el que se van a cambiar las características
     * @param _type   De qué tipo es el botón sobre el que se van a cambiar
     *                las características
     */
    public void setFeaturesButton(JButton _button, ButtonType _type) {

        _button.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, java.awt.Graphics g, int x, int y, int width, int height) {
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }

            @Override
            public java.awt.Insets getBorderInsets(Component c) {
                return new java.awt.Insets(0, 0, 0, 0);
            }
        });

        if (_type.equals(ButtonType.OPERATOR)) {
            _button.setBackground(new Color(255, 145, 0));
            _button.setFont(new Font("", Font.BOLD, 20));
            _button.setForeground(new Color(255, 251, 230));
        } else if (_type.equals(ButtonType.BASE)) {
            _button.setBackground(new Color(97, 245, 23));
            _button.setFont(new Font("", Font.BOLD, 20));
            _button.setForeground(new Color(255, 251, 230));
        } else if (_type.equals(ButtonType.HEXADECIMAL)) {
            _button.setBackground(new Color(215, 245, 23));
            _button.setFont(new Font("", Font.BOLD, 20));
            _button.setForeground(new Color(255, 251, 230));
        } else if (_type.equals(ButtonType.ESPCIAL)) {
            _button.setBackground(new Color(247, 254, 14));
            _button.setFont(new Font("", Font.BOLD, 20));
            _button.setForeground(new Color(255, 251, 230));

        } else if (_type.equals(ButtonType.MARCA)) {
            _button.setBackground(new Color(0, 113, 45));
            _button.setFont(new Font("", Font.BOLD, 20));
            _button.setForeground(new Color(255, 251, 230));

        } else {
            _button.setBackground(new Color(213, 237, 159));
            _button.setFont(new Font("", Font.BOLD, 20));
            _button.setForeground(new Color(0, 113, 45));
        }
    }

    /**
     * Este método registra los ActionListener para todos los botones de la
     * aplicación
     */
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
        this.clearHistory.addActionListener(this);
        this.addToDisplay.addActionListener(this);
        this.B2.addActionListener(this);
        this.B8.addActionListener(this);
        this.B10.addActionListener(this);
        this.B16.addActionListener(this);
        this.D.addActionListener(this);
        this.E.addActionListener(this);
        this.F.addActionListener(this);
        this.A.addActionListener(this);
        this.B.addActionListener(this);
        this.C.addActionListener(this);
        this.INFO.addActionListener(this);
        this.Owner.addActionListener(this);
        this.COPY.addActionListener(this);
    }

    /**
     * Metodo de la clase ActionListener , en este metodo vamos analizar que boton
     * se ha pulsado y con eso sabremos que hacer dependiendo de que boton se ha
     * pulsado
     * 
     * @param e Objeto ActionEvent en el que viene toda la informacion del boton que
     *          hayamos pulsado
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String cadena[] = this.display.getText().split(" ");
        Object source = e.getSource();
        String input_text = e.getActionCommand();
        int oldmod = this.modo;
        if (source.equals(this.reset)) {
            this.display.setText("0");
            this.calculo = "";
        } else if (source.equals(this.equal)) {
            if (this.modo == 4) {
                this.num1 = Integer.parseInt(cadena[0], 16);
                this.num2 = Integer.parseInt(cadena[cadena.length - 1], 16);
            } else {
                this.num1 = Integer.parseInt(cadena[0]);
                this.num2 = Integer.parseInt(cadena[cadena.length - 1]);
            }

            this.operation = cadena[cadena.length - 2];
            System.out.println(this.num1 + "/" + this.operation + "/" + this.num2);
            operation();
            this.calculo = this.result + "";
            this.display.setText(this.calculo);
            if (this.modo == 4) {
                String newresult = Integer.toHexString(this.result);
                this.listModel.addElement(
                        cadena[0] + " " + this.operation + " " + cadena[cadena.length - 1] + " = " + newresult);
            } else {
                this.listModel.addElement(this.num1 + " " + this.operation + " " + this.num2 + " = " + this.result);
            }
        } else if (source.equals(this.negative)) {
            String cosota = "";
            cadena[cadena.length - 1] = "-" + cadena[cadena.length - 1];

            for (String string : cadena) {
                cosota += string + " ";
            }

            this.calculo = cosota;
            this.display.setText(this.calculo);
        } else if (source.equals(this.clearHistory)) {
            this.listModel.clear();
        } else if (source.equals(this.addToDisplay)) {
            String seleccion[] = this.listModel.getElementAt(this.historyList.getSelectedIndex()).split(" ");
            this.calculo = seleccion[seleccion.length - 1];
            this.display.setText(this.calculo);
        } else if (source.equals(this.B2)) {
            this.modo = 2;
            if (oldmod == 4) {
                display.setText(Integer.toBinaryString(Integer.parseInt(display.getText(), 16)));
            } else if (oldmod == 3) {
                display.setText(Integer.toBinaryString(Integer.parseInt(display.getText(), 8)));
            } else if (oldmod == 2) {
                display.setText(Integer.toBinaryString(Integer.parseInt(display.getText(), 2)));
            } else {
                display.setText(display.getText());
            }
            DialogMod(this.modo);
        } else if (source.equals(this.B8)) {
            this.modo = 3;
            if (oldmod == 4) {
                display.setText(Integer.toOctalString(Integer.parseInt(display.getText(), 16)));
            } else if (oldmod == 3) {
                display.setText(Integer.toOctalString(Integer.parseInt(display.getText(), 8)));
            } else if (oldmod == 2) {
                display.setText(Integer.toOctalString(Integer.parseInt(display.getText(), 2)));
            } else {
                display.setText(display.getText());
            }
            DialogMod(this.modo);
        } else if (source.equals(this.B10)) {
            this.modo = 1;
            if (oldmod == 4) {
                display.setText(Integer.parseInt(display.getText(), 16) + "");
            } else if (oldmod == 3) {
                display.setText(Integer.parseInt(display.getText(), 8) + "");
            } else if (oldmod == 2) {
                display.setText(Integer.parseInt(display.getText(), 2) + "");
            } else {
                display.setText(display.getText());
            }
            DialogMod(this.modo);
        } else if (source.equals(this.B16)) {
            this.modo = 4;
            if (oldmod == 4) {
                display.setText(Integer.toHexString(Integer.parseInt(display.getText(), 16)));
            } else if (oldmod == 3) {
                display.setText(Integer.toHexString(Integer.parseInt(display.getText(), 8)));
            } else if (oldmod == 2) {
                display.setText(Integer.toHexString(Integer.parseInt(display.getText(), 2)));
            } else {
                display.setText(display.getText());
            }
            DialogMod(this.modo);
        } else if (source.equals(this.INFO)) {

        } else if (source.equals(this.Owner)) {
            JDialog dialog1 = new JDialog(this.frame, "Dueño");
            dialog1.setSize(500, 90);
            dialog1.setLayout(new FlowLayout());
            JLabel text1 = new JLabel("Creado por Oscar Nzabarinda Mukeshimana");
            text1.setFont(new Font("", Font.BOLD, 15));
            text1.setForeground(new Color(18, 198, 0));
            dialog1.add(text1);
            dialog1.setVisible(true);
        } else if (source.equals(this.COPY)) {
            String myString = this.display.getText();
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection strse1 = new StringSelection(myString);
            clipboard.setContents(strse1, strse1);
            JDialog dialog1 = new JDialog(this.frame, "Copy");
            dialog1.setSize(300, 90);
            dialog1.setLayout(new FlowLayout());
            JLabel text1 = new JLabel("Resultado copiado al porta papeles");
            text1.setFont(new Font("", Font.BOLD, 15));
            text1.setForeground(new Color(18, 198, 0));
            dialog1.add(text1);
            dialog1.setVisible(true);
        } else {
            this.calculo += input_text;
            this.display.setText(this.calculo);
        }
    }

    /**
     * Método que se encarga de crear el dialogo dependien del modo
     */

    public void DialogMod(int modo) {
        switch (modo) {
            case 1:
                JDialog dialog1 = new JDialog(this.frame, "Decimal");
                dialog1.setSize(500, 120);
                dialog1.setLayout(new FlowLayout());
                JLabel text1 = new JLabel("ESTAS EN MODO DECIMAL");
                text1.setFont(new Font("", Font.BOLD, 30));
                text1.setForeground(new Color(18, 198, 0));
                dialog1.add(text1);
                dialog1.setVisible(true);
                break;
            case 2:
                JDialog dialog = new JDialog(this.frame, "Binario");
                dialog.setSize(500, 120);
                dialog.setLayout(new FlowLayout());
                JLabel text = new JLabel("ESTAS EN MODO BIUNARIO");
                text.setFont(new Font("", Font.BOLD, 30));
                text.setForeground(new Color(18, 198, 0));
                dialog.add(text);
                dialog.setVisible(true);
                break;
            case 3:
                JDialog dialog3 = new JDialog(this.frame, "Octal");
                dialog3.setSize(500, 120);
                dialog3.setLayout(new FlowLayout());
                JLabel text3 = new JLabel("ESTAS EN MODO OCTAL");
                text3.setFont(new Font("", Font.BOLD, 30));
                text3.setForeground(new Color(18, 198, 0));
                dialog3.add(text3);
                dialog3.setVisible(true);
                break;
            case 4:
                JDialog dialog4 = new JDialog(this.frame, "Hexadecimal");
                dialog4.setSize(500, 120);
                dialog4.setLayout(new FlowLayout());
                JLabel text4 = new JLabel("ESTAS EN MODO HEXADECIMAL");
                text4.setFont(new Font("", Font.BOLD, 30));
                text4.setForeground(new Color(18, 198, 0));
                dialog4.add(text4);
                dialog4.setVisible(true);
                break;
            default:
                break;
        }
    }

    /**
     * Comprueba qué operación se debe realizar
     */
    public void operation() {
        switch (this.modo) {
            case 1:
                switch (this.operation) {
                    case "x":
                        this.result = this.num1 * this.num2;
                        break;
                    case "/":
                        this.result = this.num1 / this.num2;
                        break;
                    case "+":
                        this.result = this.num1 + this.num2;
                        break;
                    case "-":
                        this.result = this.num1 - this.num2;
                        break;

                    default:
                        break;
                }
                break;
            case 2:
                switch (this.operation) {
                    case "x":
                        this.result = Integer.parseInt(Integer.toBinaryString(this.num1), 2)
                                * Integer.parseInt(Integer.toBinaryString(this.num2), 2);
                        break;
                    case "/":
                        this.result = Integer.parseInt(Integer.toBinaryString(this.num1), 2)
                                / Integer.parseInt(Integer.toBinaryString(this.num2), 2);
                        break;
                    case "+":
                        this.result = Integer.parseInt(Integer.toBinaryString(this.num1), 2)
                                + Integer.parseInt(Integer.toBinaryString(this.num2), 2);
                        break;
                    case "-":
                        this.result = Integer.parseInt(Integer.toBinaryString(this.num1), 2)
                                - Integer.parseInt(Integer.toBinaryString(this.num2), 2);
                        break;

                    default:
                        break;
                }
                break;
            case 3:
                switch (this.operation) {
                    case "x":
                        this.result = Integer.parseInt(Integer.toOctalString(this.num1), 8)
                                * Integer.parseInt(Integer.toOctalString(this.num2), 8);
                        break;
                    case "/":
                        this.result = Integer.parseInt(Integer.toOctalString(this.num1), 8)
                                / Integer.parseInt(Integer.toOctalString(this.num2), 8);
                        break;
                    case "+":
                        this.result = Integer.parseInt(Integer.toOctalString(this.num1), 8)
                                + Integer.parseInt(Integer.toOctalString(this.num2), 8);
                        break;
                    case "-":
                        this.result = Integer.parseInt(Integer.toOctalString(this.num1), 8)
                                - Integer.parseInt(Integer.toOctalString(this.num2), 8);
                        break;

                    default:
                        break;
                }
            case 4:
                switch (this.operation) {
                    case "x":
                        this.result = Integer.parseInt(Integer.toHexString(this.num1), 16)
                                * Integer.parseInt(Integer.toHexString(this.num2), 16);
                        break;
                    case "/":
                        this.result = Integer.parseInt(Integer.toHexString(this.num1), 16)
                                / Integer.parseInt(Integer.toHexString(this.num2), 16);
                        break;
                    case "+":
                        this.result = Integer.parseInt(Integer.toHexString(this.num1), 16)
                                + Integer.parseInt(Integer.toHexString(this.num2), 16);
                        break;
                    case "-":
                        this.result = Integer.parseInt(Integer.toHexString(this.num1), 16)
                                - Integer.parseInt(Integer.toHexString(this.num2), 16);
                        break;

                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    // optimizar el cambio de base
    // leer el pdf
}
