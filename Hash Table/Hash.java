package hw4;

public class Hash {
    public int key;
    public int value;
    public ArrayList<HashPosition> positionList;
    
    public Hash(int key, int value, int posX, int posY) {
        this.key = key;
        this.value = value;
        HashPosition pos = new HashPosition(posX, posY);
        positionList = new ArrayList();
       // positionList.set(0, pos);
        positionList.add(pos);
    }
    
    public Hash() {
        
    }
    
    public int keyOf() {
        return key;
    }
    
    public int valueOf() {
        return value;
    }
}
