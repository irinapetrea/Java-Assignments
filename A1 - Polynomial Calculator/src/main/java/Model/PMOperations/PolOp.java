package Model.PMOperations;

import Model.PMOperations.Exceptions.OperationException;
import Model.Monomial;
import Model.Polynomial;

import java.util.ListIterator;

public class PolOp {
    public static Polynomial add(Polynomial p1, Polynomial p2) {
        Polynomial result = new Polynomial();
        ListIterator<Monomial> it1 = p1.getPolynomial().listIterator();
        ListIterator<Monomial> it2 = p2.getPolynomial().listIterator();
        Monomial m1, m2, res;
        while (it1.hasNext() && it2.hasNext()) {
            m1 = it1.next();
            m2 = it2.next();
            if (m1.getPower() > m2.getPower()) {
                result.addMonomial(m1);
                it2.previous();
            } else if (m2.getPower() > m1.getPower()) {
                result.addMonomial(m2);
                it1.previous();
            } else {
                try {
                    res = MonOp.add(m1, m2);
                    if (!MonOp.isZero(res)) result.addMonomial(res);
                } catch (OperationException ignored) {
                }
            }
        }
        while (it1.hasNext()) {
            result.addMonomial(it1.next());
        }
        while (it2.hasNext()) {
            result.addMonomial(it2.next());
        }
        if(result.getPolynomial().isEmpty()) return PolOp.zero();
        return result;
    }

    public static Polynomial subtract(Polynomial p1, Polynomial p2) {
        return add(p1, opposite(p2));
    }

    public static Polynomial multiply(Polynomial p1, Polynomial p2) { //TODO: return pol.zero if any of the operands are pol.zero ? equals operation
        Polynomial result = new Polynomial();
        for (Monomial item1 : p1.getPolynomial()) {
            for (Monomial item2 : p2.getPolynomial()) {
                result.addMonomial(MonOp.multiply(item1, item2));
            }
        }
        return result;
    }

    public static Polynomial multiply(Polynomial p, double scalar) {
        ListIterator<Monomial> it = p.getPolynomial().listIterator();
        Polynomial result = new Polynomial();
        while (it.hasNext()) {
            result.addMonomial(MonOp.multiply(it.next(), scalar));
        }
        return result;
    }

    public static Polynomial multiply(Polynomial p, Monomial m) {
        ListIterator<Monomial> it = p.getPolynomial().listIterator();
        Polynomial result = new Polynomial();
        while (it.hasNext()) {
            result.addMonomial(MonOp.multiply(it.next(), m));
        }
        return result;
    }

    public static Polynomial[] divide(Polynomial p1, Polynomial p2) throws OperationException {
        if (p1 == null || p2 == null || p1.getDegree() < p2.getDegree()) {
            throw new OperationException("Invalid division operands.");
        }
        Polynomial quotient = new Polynomial();
        Polynomial dividend = copy(p1);
        Polynomial divisor = copy(p2);
        int deg1 = dividend.getDegree();
        int deg2 = divisor.getDegree();
        Monomial aux;
        while(deg1 >= deg2) {
            divisor = multiply(divisor, new Monomial(deg1 - deg2));
            aux = new Monomial((dividend.getPolynomial().get(0).getCoefficient()/divisor.getPolynomial().get(0).getCoefficient()),deg1 - deg2);
            quotient.addMonomial(aux);
            divisor = multiply(divisor, aux.getCoefficient());
            dividend = subtract(dividend, divisor);
            if(isZero(dividend)) break;
            divisor = copy(p2);
            deg1 = dividend.getDegree();
        }
        Polynomial remainder = dividend;
        return new Polynomial[] {quotient, remainder};
    }

    public static Polynomial divide(Polynomial p, double scalar) throws OperationException {
        ListIterator<Monomial> it = p.getPolynomial().listIterator();
        Polynomial result = new Polynomial();
        while (it.hasNext()) {
            result.addMonomial(MonOp.divide(it.next(), scalar));
        }
        return result;
    }

    public static Polynomial integrate(Polynomial p) {
        ListIterator<Monomial> it = p.getPolynomial().listIterator();
        Polynomial result = new Polynomial();
        while (it.hasNext()) {
            result.addMonomial(MonOp.integrate(it.next()));
        }
        return result;
    }

    public static Polynomial differentiate(Polynomial p) {
        ListIterator<Monomial> it = p.getPolynomial().listIterator();
        Polynomial result = new Polynomial();
        while (it.hasNext()) {
            result.addMonomial(MonOp.differentiate(it.next()));
        }
        return result;
    }

    public static Polynomial opposite(Polynomial p) {
        ListIterator<Monomial> it = p.getPolynomial().listIterator();
        Polynomial result = new Polynomial();
        while (it.hasNext()) {
            result.addMonomial(MonOp.opposite(it.next()));
        }
        return result;
    }

    public static Polynomial copy(Polynomial p) {
        ListIterator<Monomial> it = p.getPolynomial().listIterator();
        Polynomial result = new Polynomial();
        while (it.hasNext()) {
            result.addMonomial(it.next());
        }
        return result;
    }

    public static boolean isZero(Polynomial p) {
        if (p.getPolynomial().isEmpty()) return true;
        return MonOp.isZero(p.getPolynomial().get(0));
    }

    public static Polynomial zero() { //TODO: see if the zero polynomial is any better than an empty one
        Polynomial result = new Polynomial();
        result.addMonomial(MonOp.zero());
        return result;
    }
}
