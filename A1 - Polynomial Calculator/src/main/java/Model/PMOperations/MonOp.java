package Model.PMOperations;

import Model.PMOperations.Exceptions.OperationException;
import Model.Monomial;

public class MonOp {
    public static Monomial add(Monomial m1, Monomial m2) throws OperationException {
        if(m1.getPower() != m2.getPower()) throw new OperationException("Invalid sum/subtraction operands");
        return new Monomial(m1.getCoefficient() + m2.getCoefficient(), m1.getPower());
    }

    public static Monomial subtract(Monomial m1, Monomial m2) throws OperationException {
        return add(m1, opposite(m2));
    }

    public static Monomial multiply(Monomial m1, Monomial m2) {
        if(MonOp.isZero(m1) || MonOp.isZero(m2)) return null;
        return new Monomial(m1.getCoefficient() * m2.getCoefficient(), m1.getPower() + m2.getPower());
    }

    public static Monomial multiply(Monomial m, double scalar) {
        if(MonOp.isZero(m) || scalar == 0) return null;
        return new Monomial(m.getCoefficient() * scalar, m.getPower());
    }

    public static Monomial divide(Monomial m1, Monomial m2) throws OperationException {
        if(m1.getPower() < m2.getPower()) throw new OperationException("Invalid division operands.");
        if(MonOp.isZero(m1) || MonOp.isZero(m2)) return null;
        return new Monomial(m1.getCoefficient()/m2.getCoefficient(), m1.getPower() - m2.getPower());
    }

    public static Monomial divide(Monomial m, double scalar) throws OperationException {
        if(scalar == 0) throw new OperationException("Monomial division by zero.");
        if(MonOp.isZero(m)) return null;
        return new Monomial(m.getCoefficient() / scalar, m.getPower());
    }

    public static Monomial integrate(Monomial m) {
        if(MonOp.isZero(m)) return null;
        return new Monomial(m.getCoefficient()/(1+m.getPower()), 1 + m.getPower());
    }

    public static Monomial differentiate(Monomial m) {
        if(m.getPower() == 0 || MonOp.isZero(m)) return null;
        return new Monomial(m.getCoefficient() * m.getPower(), m.getPower() - 1);
    }

    public static Monomial opposite(Monomial m) {
        return new Monomial(-m.getCoefficient(), m.getPower());
    }

    public static boolean isZero(Monomial m) {
        return m.getCoefficient() == 0;
    }

    public static boolean isPositive(Monomial m) {
        return m.getCoefficient() >= 0;
    }

    public static Monomial zero() {
        return new Monomial(0, 0);
    }

}
