import java.util.ArrayList;
import java.util.Collections;

import Components.AND;
import Components.Signal;
import Interfaces.Gate;

public class Simulator{
    public static void main(String[] args) {

        // Signal D = new Signal("D");
        // Signal CLK = new Signal("CLK");
        // Signal ND = new Signal("ND");

        // Signal one = new Signal("one");
        // Signal two = new Signal("two");

        // Signal Q = new Signal("Q");
        // Signal NQ = new Signal("NQ");

        // Gate NAND1 = new NAND(D, CLK, one, "NAND1");
        // Gate NAND2 = new NAND(CLK, ND, two, "NAND2");
        // Gate NAND3 = new NAND(one, NQ, Q, "NAND3");
        // Gate NAND4 = new NAND(two, Q, NQ, "NAND4");

        // Gate NOT = new NOT(D, ND, "NOT1");

        // CLK.setValue(true);
        // CLK.setValue(false);
        // System.out.println("Q: " + Q.getValue() + ", NQ: " + NQ.getValue());
        // System.out.println();


        // D.setValue(true);
        // CLK.setValue(true);
        // CLK.setValue(false);
        // System.out.println("Q: " + Q.getValue() + ", NQ: " + NQ.getValue());
        // System.out.println();


        // D.setValue(false);
        // System.out.println("Q: " + Q.getValue() + ", NQ: " + NQ.getValue());
        // System.out.println();

        // CLK.setValue(true);
        // System.out.println("Q: " + Q.getValue() + ", NQ: " + NQ.getValue());
        // System.out.println();

        Signal input = new Signal("input");
        ArrayList<Signal> signalList = new ArrayList<Signal>();
        ArrayList<Gate> gatelList = new ArrayList<Gate>();

        signalList.add(new Signal("x_0"));
        gatelList.add(new AND(input, input, signalList.get(0), "AND_0"));

        for(int i = 1; i<700; i++){
            signalList.add(new Signal("x_"+i));
            gatelList.add(new AND(signalList.get(i-1), input, signalList.get(i), "AND_"+i));
        }

        Collections.shuffle(signalList);
        Collections.shuffle(gatelList);

        input.setValue(false);
        System.out.println("Input: " + input.getValue() + ", Last: " + signalList.get(signalList.size()-1).getValue());

        long startTime = System.nanoTime();
        input.setValue(true);
        System.out.println(System.nanoTime()-startTime);
        System.out.println("Input: " + input.getValue() + ", Last: " + signalList.get(signalList.size()-1).getValue());
        
        input.setValue(false);
        System.out.println("Input: " + input.getValue() + ", Last: " + signalList.get(signalList.size()-1).getValue());
        
    }
}