package Model;

import Model.PMOperations.Exceptions.OperationException;
import Model.PMOperations.PolOp;

public class Model {
    private Polynomial currentTotal;
    private Polynomial aux;

    public Model() {
        currentTotal = PolOp.zero();
    }

    public void addMonomial(Monomial m){
        currentTotal.addMonomial(m);
    }

    public void reset() {
        currentTotal = PolOp.zero();
    }

    public Polynomial getCurrentTotal() {
        if(currentTotal == null) return PolOp.zero();
        return currentTotal;
    }

    public Polynomial getAux() {
        if(aux == null) return PolOp.zero();
        return aux;
    }

    public void add(Polynomial p) {
        currentTotal = PolOp.add(currentTotal, p);
    }

    public void subtract(Polynomial p){
        currentTotal = PolOp.subtract(currentTotal, p);
    }

    public void multiply(Polynomial p) {
        currentTotal = PolOp.multiply(currentTotal, p);
    }

    public void multiply(double scalar) {
        currentTotal = PolOp.multiply(currentTotal, scalar);
    }

    public void divide(Polynomial p) throws OperationException {
        Polynomial[] result =  PolOp.divide(currentTotal, p);
        currentTotal = result[0];
        aux = result[1];
    }

    public void divide(double scalar) throws OperationException {
        currentTotal = PolOp.divide(currentTotal, scalar);
    }

    public void integrate() {
        currentTotal = PolOp.integrate(currentTotal);
    }

    public void differentiate() {
        currentTotal = PolOp.differentiate(currentTotal);
    }

    public void opposite() {
        currentTotal = PolOp.opposite(currentTotal);
    }
}
