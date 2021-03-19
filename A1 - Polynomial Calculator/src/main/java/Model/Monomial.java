package Model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Monomial {
    private double coefficient;
    private int power;

    public Monomial(double coefficient, int power) {
        this.coefficient = coefficient;
        this.power = power;
    }

    public Monomial(int power) {
        this(1, power);
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monomial monomial = (Monomial) o;
        return Double.compare(monomial.coefficient, coefficient) == 0 &&
                power == monomial.power;
    }

    @Override
    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.0");

        if (power == 0) return formatter.format(coefficient) + "";
        String m = "";
        if (coefficient == 1) {
            m += "x";
        } else if (coefficient == -1) {
            m += "-x";
        } else {
            m += formatter.format(coefficient) + "x";
        }
        if (power != 1) {
            m += "^" + power;
        }
        return m;
    }
}
