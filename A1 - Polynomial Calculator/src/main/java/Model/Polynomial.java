package Model;

import Model.PMOperations.MonOp;

import java.util.LinkedList;
import java.util.ListIterator;

public class Polynomial {
    private final LinkedList<Monomial> polynomial;
    private int degree;

    public Polynomial() {
        this.polynomial = new LinkedList<>();
        this.degree = 0;
    }

    public void addMonomial(Monomial m) {
        if (m == null) return;
        ListIterator<Monomial> it = polynomial.listIterator();
        while (it.hasNext()) {
            Monomial current = it.next();
            if (current.getPower() == m.getPower()) {
                current.setCoefficient(current.getCoefficient() + m.getCoefficient()); //does this work?
                this.degree = polynomial.get(0).getPower();
                return;
            }
            if (current.getPower() < m.getPower()) {
                it.previous();
                it.add(m);
                this.degree = polynomial.get(0).getPower();
                return;
            }
        }
        polynomial.add(m);
        this.degree = polynomial.get(0).getPower();
    }

    public LinkedList<Monomial> getPolynomial() {
        return polynomial;
    }

    public int getDegree() {
        return degree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polynomial that = (Polynomial) o;
        if (this.degree != that.degree) return false;
        ListIterator<Monomial> it1 = this.polynomial.listIterator();
        ListIterator<Monomial> it2 = that.polynomial.listIterator();
        while (it1.hasNext() && it2.hasNext()) {
            if (!it1.next().equals(it2.next())) return false;
        }
        return !it1.hasNext() && !it2.hasNext();
    }

    @Override
    public String toString() {
        if (polynomial.isEmpty()) return null;
        ListIterator<Monomial> it = polynomial.listIterator();
        StringBuilder finalString = new StringBuilder(it.next().toString());
        Monomial m;
        while (it.hasNext()) {
            m = it.next();
            if (MonOp.isPositive(m)) {
                finalString.append(" + ").append(m.toString());
            } else finalString.append(" - ").append(MonOp.opposite(m).toString());
        }
        return finalString.toString();
    }
}
