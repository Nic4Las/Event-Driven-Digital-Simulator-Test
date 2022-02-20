package Interfaces;

public interface Observable {
    void addListener(Gate listener);
    void removeListener(Gate listener);
}