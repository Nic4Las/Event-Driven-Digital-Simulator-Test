package Components;

import java.beans.PropertyChangeEvent;

import Interfaces.Gate;

public class XOR implements Gate{
    
    private String id;
    private Signal a;
    private Signal b;
    private Signal y;

    public XOR(Signal a, Signal b, Signal y, String id){
        this.a = a;
        this.b = b;
        this.y = y;
        this.id = id;

        this.a.addListener(this);
        this.b.addListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        evaluate();
        System.out.println("Updated: " + this.id);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean getValue() {
        return a.getValue() ^ b.getValue();
    }

    @Override
    public void evaluate() {
        this.y.setValue(getValue());
        
    }
    
}
