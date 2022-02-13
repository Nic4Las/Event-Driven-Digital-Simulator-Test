package Components;

import java.beans.PropertyChangeEvent;

import Interfaces.Gate;

public class NOT implements Gate{
    
    private String id;
    private Signal a;
    private Signal y;

    public NOT(Signal a, Signal y, String id){
        this.a = a;
        this.y = y;
        this.id = id;

        this.a.addListener(this);

        // this.y.setValueSilent(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        evaluate();
        // System.out.println("Updated: " + this.id);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean getValue() {
        return !a.getValue();
    }

    @Override
    public void evaluate() {
        this.y.setValue(getValue());
    }
    
}
