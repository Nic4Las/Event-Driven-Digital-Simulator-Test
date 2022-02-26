package Components;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import Interfaces.Gate;

public class DFF implements Gate {

    private Boolean state = false;
    private String id;
    private Signal D;
    private Signal C;
    private Signal Q;

    public DFF(Signal D, Signal C, Signal Q, String id) {
        this.id = id;
        this.D = D;
        this.C = C;
        this.Q = Q;

        C.addListener(this);
        // D.addListener(this); //Do not listen, D-FlipFlop doesn't check if clock or input, because it only needs to listen to clock
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if ((Boolean) evt.getOldValue() == false && (Boolean) evt.getNewValue() == true) {
            evaluate();
        }
    }

    public void evaluate(){
        Boolean tmpValue = getValue();
        if (this.state != tmpValue) {
            this.Q.setValue(tmpValue);
            this.state = tmpValue;
            //System.out.println("Updated: " + this.id);
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    public Boolean getValue() {
        return this.D.getValue();
    }

    @Override
    public ArrayList<Signal> getInputSignals() {
        ArrayList<Signal> list = new ArrayList<Signal>();
        list.add(D);
        list.add(C);
        return list;
    }

    @Override
    public ArrayList<Signal> getOutputSignals() {
        ArrayList<Signal> list = new ArrayList<Signal>();
        list.add(Q);
        return list;
    }

}
