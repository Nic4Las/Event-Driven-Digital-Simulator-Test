import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Components.DFF;
import Components.GenericDualPortGate;
import Components.GenericDualPortGate.TYPE;
import Components.NOT;
import Components.Signal;
import EventScheduler.EventScheduler;
import Interfaces.Gate;

public class Simulator3 {
    public static void main(String[] args) {

        EventScheduler scheduler = new EventScheduler();

        HashMap<Integer, Signal> signalMap = new HashMap<Integer, Signal>();
        HashMap<String, Integer> signalNamesMap = new HashMap<String, Integer>();
        HashMap<String, Gate> gateMap = new HashMap<String, Gate>();

        try {
            JsonElement jsonElement = JsonParser.parseReader(new FileReader(".\\jsons\\parallel_to_serial.json"));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            JsonObject netnames = jsonObject.get("modules").getAsJsonObject().get("parallel_to_serial").getAsJsonObject()
                    .get("netnames").getAsJsonObject();
            JsonObject cells = jsonObject.get("modules").getAsJsonObject().get("parallel_to_serial").getAsJsonObject()
                    .get("cells").getAsJsonObject();

            for (String netName : netnames.keySet()) {

                JsonArray bits = netnames.get(netName).getAsJsonObject().get("bits").getAsJsonArray();

                if (bits.size() == 1) {
                    signalMap.put(bits.get(0).getAsJsonPrimitive().getAsInt(), new Signal(netName, scheduler));
                    signalNamesMap.put(netName, bits.get(0).getAsJsonPrimitive().getAsInt());
                } else {
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
                switch (cells.get(cellName).getAsJsonObject().get("type").getAsString()) {
                    case "AND":
                    case "NAND":
                    case "OR":
                    case "NOR":
                    case "XOR":
                    case "XNOR": {
                        Signal a = signalMap
                                .get(connections.get("A").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal b = signalMap
                                .get(connections.get("B").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal y = signalMap
                                .get(connections.get("Y").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());

                        TYPE tmp = TYPE.valueOf(cells.get(cellName).getAsJsonObject().get("type").getAsString());
                        gateMap.put(cellName, new GenericDualPortGate(a, b, y, tmp, cellName));
                        break;
                    }
                    case "NOT":
                        Signal a = signalMap
                                .get(connections.get("A").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal y = signalMap
                                .get(connections.get("Y").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());

                        gateMap.put(cellName, new NOT(a, y, cellName));
                        break;

                    case "DFF":
                        Signal d = signalMap
                                .get(connections.get("D").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal c = signalMap
                                .get(connections.get("C").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());
                        Signal q = signalMap
                                .get(connections.get("Q").getAsJsonArray().get(0).getAsJsonPrimitive().getAsInt());

                        gateMap.put(cellName, new DFF(d, c, q, cellName));
                        break;

                }

            }

            System.out.println("Nr Wires: " + signalMap.size());
            System.out.println("Nr Gates: " + gateMap.size());
            System.out.println();

            ArrayList<Integer> data_inIndexes = new ArrayList<Integer>(Arrays.asList(4, 5, 6, 7, 8, 9, 10, 11));
            Collections.reverse(data_inIndexes);

            ArrayList<Signal> data_in = generateBusByIndexes(data_inIndexes, signalMap);
            Signal clk = signalMap.get(2);
            Signal reset = signalMap.get(3);
            Signal empty_tick = signalMap.get(12);
            Signal data_out = signalMap.get(13);

            System.out.print("data_in: ");
            printBusBin(data_in);
            System.out.print("clk: ");
            System.out.println(clk.getValue()? "1" : "0");
            System.out.print("reset: ");
            System.out.println(reset.getValue()? "1" : "0");
            System.out.print("empty_tick: ");
            System.out.println(empty_tick.getValue()? "1" : "0");
            System.out.print("data_out: ");
            System.out.println(data_out.getValue()? "1" : "0");
            System.out.println();


            setBusInt(43, data_in);
            reset.setValue(true);
            scheduler.runStep();
            System.out.print("data_in: ");
            printBusBin(data_in);
            System.out.print("clk: ");
            System.out.println(clk.getValue()? "1" : "0");
            System.out.print("reset: ");
            System.out.println(reset.getValue()? "1" : "0");
            System.out.print("empty_tick: ");
            System.out.println(empty_tick.getValue()? "1" : "0");
            System.out.print("data_out: ");
            System.out.println(data_out.getValue()? "1" : "0");
            System.out.println();


            clk.toggleValue();
            scheduler.runStep();
            System.out.print("data_in: ");
            printBusBin(data_in);
            System.out.print("clk: ");
            System.out.println(clk.getValue()? "1" : "0");
            System.out.print("reset: ");
            System.out.println(reset.getValue()? "1" : "0");
            System.out.print("empty_tick: ");
            System.out.println(empty_tick.getValue()? "1" : "0");
            System.out.print("data_out: ");
            System.out.println(data_out.getValue()? "1" : "0");
            System.out.println();


            clk.toggleValue();
            scheduler.runStep();
            System.out.print("data_in: ");
            printBusBin(data_in);
            System.out.print("clk: ");
            System.out.println(clk.getValue()? "1" : "0");
            System.out.print("reset: ");
            System.out.println(reset.getValue()? "1" : "0");
            System.out.print("empty_tick: ");
            System.out.println(empty_tick.getValue()? "1" : "0");
            System.out.print("data_out: ");
            System.out.println(data_out.getValue()? "1" : "0");
            System.out.println();


            clk.toggleValue();
            reset.setValue(false);
            scheduler.runStep();
            System.out.print("data_in: ");
            printBusBin(data_in);
            System.out.print("clk: ");
            System.out.println(clk.getValue()? "1" : "0");
            System.out.print("reset: ");
            System.out.println(reset.getValue()? "1" : "0");
            System.out.print("empty_tick: ");
            System.out.println(empty_tick.getValue()? "1" : "0");
            System.out.print("data_out: ");
            System.out.println(data_out.getValue()? "1" : "0");
            System.out.println();


            for(int i = 0; i<=20; i++){
                clk.toggleValue();
                scheduler.runStep();
                System.out.println("step: " + i);
                System.out.print("data_in: ");
                printBusBin(data_in);
                System.out.print("clk: ");
                System.out.println(clk.getValue()? "1" : "0");
                System.out.print("reset: ");
                System.out.println(reset.getValue()? "1" : "0");
                System.out.print("empty_tick: ");
                System.out.println(empty_tick.getValue()? "1" : "0");
                System.out.print("data_out: ");
                System.out.println(data_out.getValue()? "1" : "0");
                System.out.println();
            }

            



        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getCause());
        }

    }

    public static ArrayList<Signal> generateBusByIndexes(ArrayList<Integer> indexes, HashMap<Integer, Signal> signals) {
        ArrayList<Signal> bus = new ArrayList<Signal>();
        for (Integer index : indexes) {
            bus.add(signals.get(index));
        }
        return bus;
    }

    public static void printBusBin(ArrayList<Signal> bus) {
        String text = "";
        for (Signal signal : bus) {
            text += signal.getValue() ? "1" : "0";
        }
        System.out.println(text);
    }

    public static void printBusInt(ArrayList<Signal> bus) {
        String text = "";
        for (Signal signal : bus) {
            text += signal.getValue() ? "1" : "0";
        }
        System.out.println(Integer.parseInt(text, 2));
    }

    public static void printBusHex(ArrayList<Signal> bus) {
        String text = "";
        for (Signal signal : bus) {
            text += signal.getValue() ? "1" : "0";
        }
        System.out.println(String.format("%0" + bus.size() + "x", Integer.parseInt(text, 2)));
    }

    public static void setBusInt(Integer value, ArrayList<Signal> bus) {
        Collections.reverse(bus);
        for (int i = 0; i < bus.size(); i++) {
            bus.get(i).setValue((value & (1 << i)) != 0);
        }
        Collections.reverse(bus);
    }

}
