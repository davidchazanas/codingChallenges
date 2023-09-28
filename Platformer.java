import java.util.ArrayList;
import java.util.List;

public class Platformer {
    List<Integer> tiles = new ArrayList<Integer>();
    public int position;
    public int indexOfPosition;

    public Platformer(int n, int position) {
        tiles = new ArrayList<Integer>();
        for(int i = 0; i < n-1; i++){
            tiles.add(i);
        }
        this.position = position;
        this.indexOfPosition = tiles.indexOf(position);
    }

    public void jumpLeft() {
        

        int maxTile = tiles.size() -1;
        System.out.println(String.format("maxTile %d", tiles.get(maxTile)));
        //tratar a volta
        int indexOfNewPosition = indexOfPosition - 2;
        if(indexOfNewPosition < 0) {
            indexOfNewPosition += (maxTile + 1);
        }

        int newPosition = tiles.get(indexOfNewPosition);
        tiles.remove(indexOfPosition);
        
        //new position is before, removing previous tile doesnt affect position
        if(!(indexOfNewPosition < indexOfPosition)) {
            indexOfNewPosition --;
        } 


        //tratar a volta
        position = newPosition;
        indexOfPosition = indexOfNewPosition;
    }

    public void jumpRight() {
        
        int maxTile = tiles.size() - 1;
        int indexOfNewPosition = indexOfPosition + 2;
        if(indexOfNewPosition > maxTile) {
            indexOfNewPosition -= (maxTile + 1);
        }
        
        int newPosition = tiles.get(indexOfNewPosition);
        tiles.remove(indexOfPosition);
        
        //new position is before, removing previous tile doesnt affect position
        if(!(indexOfNewPosition < indexOfPosition)) {
            indexOfNewPosition --;
        } 


        //tratar a volta
        position = newPosition;
        indexOfPosition = indexOfNewPosition;
    }

    public int position() {
        return this.position;
    }

    public static void main(String[] args) {
        Platformer platformer = new Platformer(10, 3);
        System.out.println(platformer.position()); // should print 3

        platformer.jumpLeft();
        System.out.println(platformer.position()); // should print 1
        platformer.jumpLeft();
        System.out.println(platformer.position());
        platformer.jumpRight();
        System.out.println(platformer.position()); // should print 4
        platformer.jumpRight();
        System.out.println(platformer.position()); // should print 4
        platformer.jumpRight();
        System.out.println(platformer.position()); // should print 4
    }
}
