package EventScheduler;

import java.beans.PropertyChangeEvent;
import java.util.LinkedList;
import java.util.Queue;

import Components.Signal;
import Interfaces.Gate;

public class EventScheduler {
    private Queue<PropertyChangeEvent> queue = new LinkedList<PropertyChangeEvent>();

    public EventScheduler() {

    }

    public void addEvent(PropertyChangeEvent event) {
        this.queue.add(event);
    }

    public void runStep(){
        while(!queue.isEmpty()){
            PropertyChangeEvent event = this.queue.poll();
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();
            Object source = event.getSource();
            if(source instanceof Signal){
                if (((Signal)source).getDirty()) {
                    ((Signal)source).markClean();
                    for (Gate gate : ((Signal)source).getListeners()) {
                        gate.propertyChange(event);
                    }
 
                 }

                if (oldValue == null || newValue == null || !oldValue.equals(newValue)) {
                   for (Gate gate : ((Signal)source).getListeners()) {
                       gate.propertyChange(event);
                   }

                }
            }
        }
    }

}
