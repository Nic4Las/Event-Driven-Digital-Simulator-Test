package Components;

import java.beans.PropertyChangeEvent;

import Interfaces.Gate;

public class DFF implements Gate {

    private boolean state = false;
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

        if ((boolean) evt.getOldValue() == false && (boolean) evt.getNewValue() == true) {
            evaluate();
        }
    }

    public void evaluate(){
        boolean tmpValue = getValue();
        if (this.state != tmpValue) {
            this.Q.setValue(tmpValue);
            this.state = tmpValue;
            // System.out.println("Updated: " + this.id);
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean getValue() {
        return this.D.getValue();
    }

}
