package Components;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import Components.Port.PORT_TYPE;
import Interfaces.Gate;

public class SUB_CIRCUIT implements Gate {

    private String id;

    private ArrayList<Gate> gates;
    private ArrayList<Signal> signals;

    private ArrayList<Port> inputPorts;
    private ArrayList<Port> outputPorts;


    public SUB_CIRCUIT(ArrayList<Gate> gates, ArrayList<Signal> signals, String id){

        this.gates = gates;
        this.signals = signals;

        for (Gate gate : gates) {
            if(gate instanceof Port){
                if(((Port)gate).getType() == PORT_TYPE.INPUT){
                    inputPorts.add((Port)gate);
                };
                if(((Port)gate).getType() == PORT_TYPE.OUTPUT){
                    outputPorts.add((Port)gate);
                };
            }
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void evaluate() {}

    @Override
    public ArrayList<Signal> getInputSignals() {
        ArrayList<Signal> res = new ArrayList<Signal>();
        for (Port port : inputPorts) {
            res.add(port.getOutputSignals().get(0));       
        }
        return res;
    }

    @Override
    public ArrayList<Signal> getOutputSignals() {
        ArrayList<Signal> res = new ArrayList<Signal>();
        for (Port port : outputPorts) {
            res.add(port.getInputSignals().get(0));       
        }
        return res;
    }
    
}
