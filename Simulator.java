import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Components.AND;
import Components.DFF;
import Components.NAND;
import Components.NOR;
import Components.NOT;
import Components.OR;
import Components.Signal;
import Components.XOR;
import EventScheduler.EventScheduler;
import Interfaces.Gate;

public class Simulator {
    public static void main(String[] args) {

        EventScheduler scheduler = new EventScheduler();

        
        HashMap<Integer, Signal> signalMap = new HashMap<Integer, Signal>();
        HashMap<String, Integer> signalNamesMap = new HashMap<String, Integer>();
        HashMap<String, Gate> gateMap = new HashMap<String, Gate>();

        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(".\\jsons\\alu.json"));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            JsonObject netnames = jsonObject.get("modules").getAsJsonObject().get("alu").getAsJsonObject()
                    .get("netnames").getAsJsonObject();
            JsonObject cells = jsonObject.get("modules").getAsJsonObject().get("alu").getAsJsonObject()
                    .get("cells").getAsJsonObject();

            for (String netName : netnames.keySet()) {

                JsonArray bits = netnames.get(netName).getAsJsonObject().get("bits").getAsJsonArray();

                if(bits.size()==1){
                    signalMap.put(bits.get(0).getAsJsonPrimitive().getAsInt(), new Signal(netName, scheduler));
                    signalNamesMap.put(netName, bits.get(0).getAsJsonPrimitive().getAsInt());
                } else{
                    for (JsonElement busSignal : bits) {

                        if (busSignal.getAsJsonPrimitive().isNumber()) {
                            Signal tempSignal = new Signal(netName + "_" + busSignal, scheduler);
                            tempSignal.forceUpdate();
                            signalMap.put(busSignal.getAsJsonPrimitive().getAsInt(), tempSignal);
                            signalNamesMap.put(netName + "_" + busSignal, busSignal.getAsJsonPrimitive().getAsInt());
                        }
    
                    }
                }

                
                

            }

            for (String cellName : cells.keySet()) {
                JsonObject connections = cells.get(cellName).getAsJsonObject().get("connections").getAsJsonObject();
                
                switch(cells.get(cellName).getAsJsonObject().get("type").getAsString()){
                    case "AND":{
                        Signal a = signalMap.get(connections.get("A").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal b = signalMap.get(connections.get("B").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal y = signalMap.get(connections.get("Y").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());

                        gateMap.put(cellName, new AND(a, b, y, cellName));
                        break;
                    }
                        

                    case "NAND":{
                        Signal a = signalMap.get(connections.get("A").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal b = signalMap.get(connections.get("B").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal y = signalMap.get(connections.get("Y").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());

                        gateMap.put(cellName, new NAND(a, b, y, cellName));
                        break;
                    }
                        

                    case "OR":{
                        Signal a = signalMap.get(connections.get("A").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal b = signalMap.get(connections.get("B").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal y = signalMap.get(connections.get("Y").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        
                        gateMap.put(cellName, new OR(a, b, y, cellName));
                        break;
                    }
                        

                    case "NOR":{
                        Signal a = signalMap.get(connections.get("A").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal b = signalMap.get(connections.get("B").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal y = signalMap.get(connections.get("Y").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                    
                        gateMap.put(cellName, new NOR(a, b, y, cellName));
                        break;
                    }
                        

                    case "NOT":{
                        Signal a = signalMap.get(connections.get("A").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal y = signalMap.get(connections.get("Y").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        
                        gateMap.put(cellName, new NOT(a, y, cellName));
                        break;
                    }

                        
                    case "XOR":{
                        Signal a = signalMap.get(connections.get("A").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal b = signalMap.get(connections.get("B").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal y = signalMap.get(connections.get("Y").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        
                        gateMap.put(cellName, new XOR(a, b, y, cellName));
                        break;
                    }
                        

                    case "XNOR":{
                        Signal a = signalMap.get(connections.get("A").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal b = signalMap.get(connections.get("B").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal y = signalMap.get(connections.get("Y").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        
                        gateMap.put(cellName, new XOR(a, b, y, cellName));
                        break;
                    }
                        

                    case "DFF": {
                        Signal d = signalMap.get(connections.get("D").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal c = signalMap.get(connections.get("C").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal q = signalMap.get(connections.get("Q").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());

                        gateMap.put(cellName, new DFF(d, c, q, cellName));
                        break;
                    }
                        
                }

            }

            System.out.println("Nr Wires: " + signalMap.size());
            System.out.println("Nr Gates: " + gateMap.size());
            System.out.println();

            ArrayList<Integer> AIndexes = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9));
            ArrayList<Integer> BIndexes = new ArrayList<Integer>(Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17));
            ArrayList<Integer> ALU_SelIndexes = new ArrayList<Integer>(Arrays.asList(18, 19, 20, 21));
            ArrayList<Integer> ALU_OutIndexes = new ArrayList<Integer>(Arrays.asList(22, 23, 24, 25, 26, 27, 28, 29));

            ArrayList<Signal> A = generateBusByIndexes(AIndexes, signalMap);
            ArrayList<Signal> B = generateBusByIndexes(BIndexes, signalMap);
            ArrayList<Signal> ALU_Sel = generateBusByIndexes(ALU_SelIndexes, signalMap);
            ArrayList<Signal> ALU_Out = generateBusByIndexes(ALU_OutIndexes, signalMap);
            Signal CarryOut = signalMap.get(30);


            scheduler.runStep();
            setBusInt(0, A);
            scheduler.runStep();
            setBusInt(0, B);
            scheduler.runStep();
            setBusInt(0b0000, ALU_Sel);
            scheduler.runStep();

            scheduler.runStep();
            printBusBin(ALU_Out);
            scheduler.runStep();
            setBusInt(5, A);
            scheduler.runStep();
            setBusInt(1, B);
            scheduler.runStep();
            printBusBin(A);
            printBusBin(B);
            scheduler.runStep();
            printBusBin(ALU_Out);
            scheduler.runStep();
            printBusBin(ALU_Out);
            scheduler.runStep();
            printBusBin(ALU_Out);
            scheduler.runStep();
            printBusBin(ALU_Out);
            scheduler.runStep();
            printBusBin(ALU_Out);
            scheduler.runStep();
            printBusBin(ALU_Out);
            scheduler.runStep();
            printBusBin(ALU_Out);


            // setBusInt(0, A);
            // setBusInt(0, B);
            // setBusInt(0b0000, ALU_Sel);

            // printBusBin(A);
            // printBusBin(B);
            // printBusBin(ALU_Out);
            // printBusBin(ALU_Sel);

            // scheduler.runStep();

            // printBusBin(ALU_Out);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getCause());
        }
        
        
    }

    public static ArrayList<Signal> generateBusByIndexes(ArrayList<Integer> indexes, HashMap<Integer, Signal> signals){
        ArrayList<Signal> bus = new ArrayList<Signal>();
        for (Integer index:indexes) {
            bus.add(signals.get(index));
        }
        return bus;
    }

    public static void printBusBin(ArrayList<Signal> bus){
        String text = "";
        for (Signal signal : bus) {
            text += signal.getValue()?"1":"0";
        }
        System.out.println(text);
    }

    public static void printBusInt(ArrayList<Signal> bus){
        String text = "";
        for (Signal signal : bus) {
            text += signal.getValue()?"1":"0";
        }
        System.out.println(Integer.parseInt(text, 2));
    }

    public static void printBusHex(ArrayList<Signal> bus){
        String text = "";
        for (Signal signal : bus) {
            text += signal.getValue()?"1":"0";
        }
        System.out.println(String.format("%0"+bus.size()+"x", Integer.parseInt(text, 2)));
    }

    public static void setBusInt(Integer value, ArrayList<Signal> bus){
        Collections.reverse(bus);
        for (int i = 0; i < bus.size(); i++) {
            bus.get(i).setValue((value & (1 << i)) != 0);
        }
        Collections.reverse(bus);
    }

    
} 
