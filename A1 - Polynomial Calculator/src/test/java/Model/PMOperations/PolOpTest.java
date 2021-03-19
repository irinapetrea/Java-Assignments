package Model.PMOperations;

import Controller.StringProcessor;
import Model.Monomial;
import Model.PMOperations.Exceptions.OperationException;
import Model.Polynomial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolOpTest {
    Polynomial p1 = new Polynomial();
    Polynomial p2 = new Polynomial();
    @BeforeEach
    void setUp() {
        p1.addMonomial(new Monomial(1, 3));
        p1.addMonomial(new Monomial(-12, 1));
        p1.addMonomial(new Monomial(-42, 0));

        p2.addMonomial(new Monomial(1, 1));
        p2.addMonomial(new Monomial(-3, 0));
    }

    @Test
    void add() {
        Polynomial result = StringProcessor.toPolynomial("x^3-11x-45");
        assertEquals(PolOp.add(p1, p2), result);
    }

    @Test
    void subtract() {
        Polynomial result = StringProcessor.toPolynomial("x^3-13x-39");
        assertEquals(PolOp.subtract(p1, p2), result);
    }

    @Test
    void multiply() {
        Polynomial result = StringProcessor.toPolynomial("x^4-3x^3-12x^2-6x+126");
        assertEquals(PolOp.multiply(p1, p2), result);
    }

    @Test
    void testMultiply() {
        Polynomial result = StringProcessor.toPolynomial("2x^4-24x^2-84x");
        assertEquals(PolOp.multiply(p1, new Monomial(2, 1)), result);
    }

    @Test
    void testMultiply1() {
        Polynomial result = StringProcessor.toPolynomial("2x^3-24x^1-84");
        assertEquals(PolOp.multiply(p1, 2), result);
    }

    @Test
    void divide() {
        Polynomial quotient = StringProcessor.toPolynomial("x^2+3x-3");
        Polynomial remainder = StringProcessor.toPolynomial("-51");
        try {
            Polynomial[] result = PolOp.divide(p1, p2);
            assertEquals(result[0], quotient);
            assertEquals(result[1], remainder);
        } catch (OperationException ignored) {}
    }

    @Test
    void integrate() {
        Polynomial p = new Polynomial();
        p.addMonomial(new Monomial(3,3));
        Polynomial polynomial = PolOp.add(p1, p);
        p = StringProcessor.toPolynomial("x^4-6x^2-42x");
        assertEquals(PolOp.integrate(polynomial), p);
    }

    @Test
    void differentiate() {
        Polynomial result = StringProcessor.toPolynomial("3x^2-12");
        assertEquals(PolOp.differentiate(p1), result);
    }

    @Test
    void opposite() {
        Polynomial result = StringProcessor.toPolynomial("-x^3+12x+42");
        assertEquals(PolOp.opposite(p1), result);
    }

    @Test
    void copy() {
        Polynomial result = StringProcessor.toPolynomial("x^3-12x-42");
        assertEquals(PolOp.copy(p1), result);
    }
}