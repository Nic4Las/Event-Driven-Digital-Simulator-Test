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
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("id: " + this.id + " updated");
        this.y.setValue(!a.getValue());
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean getValue() {
        return !a.getValue();
    }
    
}
