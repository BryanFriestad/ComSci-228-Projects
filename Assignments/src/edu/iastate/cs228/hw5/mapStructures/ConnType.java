package edu.iastate.cs228.hw5.mapStructures;

public enum ConnType {
    
    hwy4(110),
    hwy2(130),
    dirt(143),
    river(185),
    fence(325),
    canyon(950),
    wall(450), 
    path(0), 
    kDefault(195);
    private int weight = 0;
    
    private ConnType(int w){
        weight = w;
    }
    public int getWeight() {
        return weight;
    }
    public boolean isBarrier() {
        return (this == wall || this == fence || this == canyon);
    }  
}
