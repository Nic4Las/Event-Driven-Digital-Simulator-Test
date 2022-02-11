package Components;

import java.beans.PropertyChangeEvent;

import Interfaces.Gate;

public class AND implements Gate{
    
    private String id;
    private Signal a;
    private Signal b;
    private Signal y;

    public AND(Signal a, Signal b, Signal y, String id){
        this.a = a;
        this.b = b;
        this.y = y;
        this.id = id;

        this.a.addListener(this);
        this.b.addListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // System.out.println("id: " + this.id + " updated");
        evaluate();
        System.out.println("Updated: " + this.id);
    }

    public void evaluate() {
        // System.out.println("id: " + this.id + " updated");
        this.y.setValue(getValue());
        System.out.println("Updated: " + this.id);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean getValue() {
        return a.getValue() && b.getValue();
    }
    
}
