package Components;

import java.beans.PropertyChangeEvent;
import java.util.function.BiFunction;

import Interfaces.Gate;

public class GenericDualPortGate implements Gate{

    private String id;
    BiFunction<Boolean,Boolean,Boolean> function;
    private Signal a;
    private Signal b;
    private Signal y;

    public GenericDualPortGate(Signal a, Signal b, Signal y, BiFunction<Boolean,Boolean,Boolean> function, String id){
        this.a = a;
        this.b = b;
        this.y = y;
        this.function = function;
        this.id = id;

        this.a.addListener(this);
        this.b.addListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("id: " + this.id + " updated");
        this.y.setValue(function.apply(a.getValue(), b.getValue()));
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean getValue() {
        return function.apply(a.getValue(), b.getValue());
    }
    
}
