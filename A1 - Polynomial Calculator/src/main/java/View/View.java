package View;

import Model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private final Model model;

    private final JTextField userInputTf1 = new JTextField();
    private final JTextField userInputTf2 = new JTextField();
    private final JTextField total = new JTextField();

    private final JButton addBtn = new JButton("Add");
    private final JButton subtractBtn = new JButton("Subtract");
    private final JButton multiplyBtn = new JButton("Multiply");
    private final JButton divideBtn = new JButton("Divide");
    private final JButton differentiateBtn = new JButton("Differentiate");
    private final JButton integrateBtn = new JButton("Integrate");
    private final JButton oppositeBtn = new JButton("Get Opposite");

    public View(Model model) {
        this.model = model;
        this.setSize(new Dimension(600, 300));
        this.setLocationRelativeTo(null);
        Font font = new Font("Courier", Font.BOLD, 25);
        //interface layout
        total.setEditable(false);
        JPanel content = new JPanel();
        content.setSize(new Dimension(700, 500));
        content.setLayout(new BoxLayout(content,BoxLayout.X_AXIS));

        JPanel input = new JPanel();
        input.setLayout(new FlowLayout());
        input.setPreferredSize(new Dimension(200, 500));
        input.add(new JLabel("Input"));
        input.add(userInputTf1);
        input.add(new JLabel("Operand"));
        input.add(userInputTf2);
        input.setBackground(Color.orange);
        userInputTf1.setPreferredSize(new Dimension(190, 35));
        userInputTf2.setPreferredSize(new Dimension(190, 35));
        userInputTf1.setFont(font);
        userInputTf2.setFont(font);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        buttons.setPreferredSize(new Dimension(120, 700));
        buttons.setBackground(Color.yellow);
        addBtn.setPreferredSize(new Dimension(110,25));
        subtractBtn.setPreferredSize(new Dimension(110,25));
        multiplyBtn.setPreferredSize(new Dimension(110,25));
        divideBtn.setPreferredSize(new Dimension(110,25));
        differentiateBtn.setPreferredSize(new Dimension(110,25));
        integrateBtn.setPreferredSize(new Dimension(110,25));
        oppositeBtn.setPreferredSize(new Dimension(110,25));
        buttons.add(addBtn);
        buttons.add(subtractBtn);
        buttons.add(multiplyBtn);
        buttons.add(divideBtn);
        buttons.add(differentiateBtn);
        buttons.add(integrateBtn);
        buttons.add(oppositeBtn);

        addBtn.setBackground(Color.orange);
        subtractBtn.setBackground(Color.orange);
        divideBtn.setBackground(Color.orange);
        multiplyBtn.setBackground(Color.orange);
        differentiateBtn.setBackground(Color.orange);
        integrateBtn.setBackground(Color.orange);
        oppositeBtn.setBackground(Color.orange);

        content.add(input);
        content.add(buttons);
        content.add(total);
        total.setBackground(Color.orange);
        total.setFont(font);

        this.setContentPane(content);
        this.setTitle("Polynomial Calculator");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void reset() {
        model.reset();
    }

    public String[] getUserInput() {
        return new String[]{userInputTf1.getText(), userInputTf2.getText()};
    }

    public void setTotal(String newTotal) {
        total.setText(newTotal);
    }

    public void resetInput() {
        userInputTf1.setText("");
        userInputTf2.setText("");
    }

    public void addAddListener(ActionListener actionListener) {
        addBtn.addActionListener(actionListener);
    }

    public void addSubtractListener(ActionListener actionListener) {
        subtractBtn.addActionListener(actionListener);
    }

    public void addMultiplyListener(ActionListener actionListener) {
        multiplyBtn.addActionListener(actionListener);
    }

    public void addDivideListener(ActionListener actionListener) {
        divideBtn.addActionListener(actionListener);
    }

    public void addIntegrateListener(ActionListener actionListener) {
        integrateBtn.addActionListener(actionListener);
    }

    public void addDifferentiateListener(ActionListener actionListener) {
        differentiateBtn.addActionListener(actionListener);
    }

    public void addOppositeListener(ActionListener actionListener) {
        oppositeBtn.addActionListener(actionListener);
    }

    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
}
