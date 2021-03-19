package Controller;

import Model.Model;
import Model.PMOperations.PolOp;
import Model.Polynomial;
import View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    public Controller(Model model, View view) {

        view.addAddListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] operands = view.getUserInput();
                Polynomial p1 = StringProcessor.toPolynomial(operands[0]);
                Polynomial p2 = StringProcessor.toPolynomial(operands[1]);
                view.setTotal(PolOp.add(p1, p2).toString());
            }
        });

        view.addSubtractListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] operands = view.getUserInput();
                Polynomial p1 = StringProcessor.toPolynomial(operands[0]);
                Polynomial p2 = StringProcessor.toPolynomial(operands[1]);
                view.setTotal(PolOp.subtract(p1, p2).toString());
            }
        });

        view.addMultiplyListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] operands = view.getUserInput();
                Polynomial p1 = StringProcessor.toPolynomial(operands[0]);
                Polynomial p2 = StringProcessor.toPolynomial(operands[1]);
                view.setTotal(PolOp.multiply(p1, p2).toString());
            }
        });

        view.addDivideListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] operands = view.getUserInput();
                Polynomial p1 = StringProcessor.toPolynomial(operands[0]);
                Polynomial p2 = StringProcessor.toPolynomial(operands[1]);
                try {
                    Polynomial[] result = PolOp.divide(p1, p2);
                    view.setTotal(result[0].toString() + ", " + result[1].toString());
                } catch (Exception ex) {
                    view.setTotal("undefined");
                    view.resetInput();
                    view.showError(ex.getMessage());
                }
            }
        });

        view.addDifferentiateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] operands = view.getUserInput();
                Polynomial p1 = StringProcessor.toPolynomial(operands[0]);
                view.setTotal(PolOp.differentiate(p1).toString());
            }
        });

        view.addIntegrateListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] operands = view.getUserInput();
                Polynomial p1 = StringProcessor.toPolynomial(operands[0]);
                view.setTotal(PolOp.integrate(p1).toString());
            }
        });

        view.addOppositeListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] operands = view.getUserInput();
                Polynomial p1 = StringProcessor.toPolynomial(operands[0]);
                view.setTotal(PolOp.opposite(p1).toString());
            }
        });
        /*
        Monomial m1 = new Monomial(-2, 6);
        Monomial m2 = new Monomial(-4, 4);
        Monomial m3 = new Monomial(4, 2);
        Monomial m4 = new Monomial(2, 1);
        Monomial m5 = new Monomial(-1, 0);

        Polynomial p1 = new Polynomial();
        p1.addMonomial(m1);
        //p1.addMonomial(m2);
        //p1.addMonomial(m3);
        p1.addMonomial(m4);
        p1.addMonomial(m5);

        Polynomial p2 = new Polynomial();
        p2.addMonomial(new Monomial(1, 6));
        //p2.addMonomial(new Monomial(-2, 4));
        //p2.addMonomial(new Monomial(3, 3));
        //p2.addMonomial(new Monomial(1, 2));
        p2.addMonomial(new Monomial(-2, 1));
        System.out.println("p1 = " + p1.toString());
        System.out.println("p2 = " + p2);
        System.out.println("p1 + p2 = " + PolOp.add(p1, p2));
        System.out.println("p1 + p2 = " + PolOp.subtract(p1, p2));
        System.out.println("p1 * p2 = " + PolOp.multiply(p1, p2));
        try {
            System.out.println("p1 / 2 = " + PolOp.divide(p1, 2));
        } catch (OperationException ignored) {}
        System.out.println("p1 * 2 = " + PolOp.multiply(p1, 2));
        System.out.println("p1 * x^2 = " + PolOp.multiply(p1, new Monomial(2)));
        System.out.println("integral of p1 = " + PolOp.integrate(p1));
        System.out.println("derivative of p1 = " + PolOp.differentiate(p1));
        System.out.println("copy of p1 = " + PolOp.copy(p1));
        System.out.println();

        Polynomial p3 = new Polynomial();
        p3.addMonomial(new Monomial(1, 2));
        p3.addMonomial(new Monomial(4, 5));
        p3.addMonomial(new Monomial(2, 2));
        p3.addMonomial(new Monomial(-1, 6));
        p3.addMonomial(new Monomial(-1, 5));
        System.out.println("p3 = " + p3);
        System.out.println("p1 degree is " + p1.getDegree());
        System.out.println("p2 degree is " + p2.getDegree());
        System.out.println("p3 degree is " + p3.getDegree());

        Polynomial p4 = new Polynomial();
        Polynomial p5 = new Polynomial();
        p4.addMonomial(new Monomial(1, 3));
        p4.addMonomial(new Monomial(-12, 2));
        p4.addMonomial(new Monomial(-42, 0));

        p5.addMonomial(new Monomial(1, 1));
        p5.addMonomial(new Monomial(-3, 0));

        System.out.println("p4 = " + p4);
        System.out.println("p5 = " + p5);
        Polynomial q = new Polynomial();
        Polynomial r = new Polynomial();
        Polynomial[] div = new Polynomial[0];
        try {
            div = PolOp.divide(p4, p5);
        } catch (OperationException ignored) {}

        System.out.println(div[0]);
        System.out.println(div[1]);

        System.out.println(PolOp.add(p1, PolOp.zero()));
        System.out.println(PolOp.multiply(p1, PolOp.zero()));
        System.out.println(PolOp.integrate(PolOp.zero()));

         */
    }
}
