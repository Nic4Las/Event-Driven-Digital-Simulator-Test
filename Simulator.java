import java.io.FileReader;
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
import Interfaces.Gate;

public class Simulator {
    public static void main(String[] args) {
        HashMap<Integer, Signal> signalMap = new HashMap<Integer, Signal>();
        HashMap<String, Integer> signalNamesMap = new HashMap<String, Integer>();
        HashMap<String, Gate> gateMap = new HashMap<String, Gate>();

        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(".\\jsons\\counter2.json"));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            JsonObject netnames = jsonObject.get("modules").getAsJsonObject().get("up_counter").getAsJsonObject()
                    .get("netnames").getAsJsonObject();
            JsonObject cells = jsonObject.get("modules").getAsJsonObject().get("up_counter").getAsJsonObject()
                    .get("cells").getAsJsonObject();

            for (String netName : netnames.keySet()) {
                // System.out.println("adding Signal:  " + netName);

                JsonArray bits = netnames.get(netName).getAsJsonObject().get("bits").getAsJsonArray();

                if(bits.size()==1){
                    signalMap.put(bits.get(0).getAsJsonPrimitive().getAsInt(), new Signal(netName));
                    signalNamesMap.put(netName, bits.get(0).getAsJsonPrimitive().getAsInt());
                } else{
                    for (JsonElement busSignal : bits) {

                        if (busSignal.getAsJsonPrimitive().isNumber()) {
                            // System.out.println("bits:           " + netName + "_" + busSignal);
                            signalMap.put(busSignal.getAsJsonPrimitive().getAsInt(), new Signal(netName + "_" + busSignal));
                            signalNamesMap.put(netName + "_" + busSignal, busSignal.getAsJsonPrimitive().getAsInt());
                        }
    
                    }
                }

                
                // System.out.println();

            }

            for (String cellName : cells.keySet()) {
                // System.out.println("adding Gate:        " + cellName);
                // System.out.println("    Gate Type:      " + cells.get(cellName).getAsJsonObject().get("type"));

                JsonObject connections = cells.get(cellName).getAsJsonObject().get("connections").getAsJsonObject();

                // System.out.println(connections.get("A").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                // System.out.println(connections.get("B").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                // System.out.println(connections.get("Y").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());

                // System.out.println();
                // break;



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

            for(Gate gate : gateMap.values()){
                if(!(gate instanceof DFF)){
                    gate.evaluate();
                }
            }

            System.out.println("Nr Wires: " + signalMap.size());
            System.out.println("Nr Gates: " + gateMap.size());
            System.out.println();

           Signal clk = signalMap.get(2);
           Signal reset = signalMap.get(3);
           Signal output_0 = signalMap.get(4);
           Signal output_1 = signalMap.get(5);
           Signal output_2 = signalMap.get(6);
           Signal output_3 = signalMap.get(7);

           clk.setValue(false);
           reset.setValue(false);
           output_0.setValue(false);
           output_1.setValue(false);
           output_2.setValue(false);
           output_3.setValue(false);

           System.out.print(output_3.getValue()?1:0);
           System.out.print(output_2.getValue()?1:0);
           System.out.print(output_1.getValue()?1:0);
           System.out.print(output_0.getValue()?1:0);
           System.out.println();

           clk.toggleValue();
           clk.toggleValue();
           System.out.print(output_3.getValue()?1:0);
           System.out.print(output_2.getValue()?1:0);
           System.out.print(output_1.getValue()?1:0);
           System.out.print(output_0.getValue()?1:0);
           System.out.println();





            // Signal clk = signalMap.get(2);
            // Signal rst = signalMap.get(3);
            // Signal en = signalMap.get(4);
            // Signal count_0 = signalMap.get(5);
            // Signal count_1 = signalMap.get(6);
            // Signal count_2 = signalMap.get(7);
            // Signal count_3 = signalMap.get(8);

            // en.setValue(false);
            // rst.setValue(false);
            // en.setValue(false);
            // count_0.setValue(false);
            // count_1.setValue(false);
            // count_2.setValue(false);
            // count_3.setValue(false);


            // en.setValue(true);
            // clk.setValue(true);
            // clk.setValue(false);
            // clk.setValue(true);
            // clk.setValue(false);

            // System.out.print(count_3.getValue()?1:0);
            // System.out.print(count_2.getValue()?1:0);
            // System.out.print(count_1.getValue()?1:0);
            // System.out.print(count_0.getValue()?1:0);
            // System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}