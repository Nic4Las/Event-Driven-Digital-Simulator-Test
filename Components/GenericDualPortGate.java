package Components;

import java.beans.PropertyChangeEvent;
import java.util.function.BiFunction;

import Interfaces.Gate;

public class GenericDualPortGate implements Gate {

    private String id;
    BiFunction<Boolean, Boolean, Boolean> function;
    private Signal a;
    private Signal b;
    private Signal y;

    public GenericDualPortGate(Signal a, Signal b, Signal y, TYPE type, String id) {
        this(a, b, y, type.fn, id);
    }

    public GenericDualPortGate(Signal a, Signal b, Signal y, BiFunction<Boolean, Boolean, Boolean> function,
            String id) {
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
        evaluate();
        //System.out.println("Updated: " + this.id);
    }

    public void evaluate(){
        this.y.setValue(getValue());
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Boolean getValue() {
        return function.apply(a.getValue(), b.getValue());
    }

    public enum TYPE {
        AND((a,b) -> (a&&b)),
        NAND((a,b) -> !(a&&b)),
        OR((a,b) -> (a||b)),
        NOR((a,b) -> !(a||b)),
        XOR((a,b) -> (a^b)),
        XNOR((a,b) -> !(a^b));

        public final BiFunction<Boolean,Boolean,Boolean> fn;

        TYPE(BiFunction<Boolean,Boolean,Boolean> fn) {
            this.fn = fn;
        }
    }
}
