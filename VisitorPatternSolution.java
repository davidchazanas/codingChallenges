import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

enum Color {
    RED, GREEN
}

abstract class Tree {

    private int value;
    private Color color;
    private int depth;

    public Tree(int value, Color color, int depth) {
        this.value = value;
        this.color = color;
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int getDepth() {
        return depth;
    }

    public abstract void accept(TreeVis visitor);
}

class TreeNode extends Tree {

    private ArrayList<Tree> children = new ArrayList<>();

    public TreeNode(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitNode(this);

        for (Tree child : children) {
            child.accept(visitor);
        }
    }

    public void addChild(Tree child) {
        children.add(child);
    }
}

class TreeLeaf extends Tree {

    public TreeLeaf(int value, Color color, int depth) {
        super(value, color, depth);
    }

    public void accept(TreeVis visitor) {
        visitor.visitLeaf(this);
    }
}

abstract class TreeVis
{
    public abstract int getResult();
    public abstract void visitNode(TreeNode node);
    public abstract void visitLeaf(TreeLeaf leaf);

}

class SumInLeavesVisitor extends TreeVis {    
    private int result = 0;
    public int getResult() {
       return result;
    }      
      
    public void visitNode(TreeNode node) {
        //
    }

    public void visitLeaf(TreeLeaf leaf) {
      	result += leaf.getValue();
    }
}

class ProductOfRedNodesVisitor extends TreeVis {
    private long result = 1;
    private final int power = 1000000007;
    public int getResult() {
        return (int) result;
    }

    public void visitNode(TreeNode node) {
      	if(node.getColor() == Color.RED){
              result = (result * node.getValue()) % power;
          }
    }

    public void visitLeaf(TreeLeaf leaf) {
      	    if(leaf.getColor() == Color.RED){
              result = (result * leaf.getValue()) % power ;
          }
    }
}

class FancyVisitor extends TreeVis {
    private int sumNodes = 0;
    private int sumLeafs = 0;
    
    public int getResult() {
      	//implement this
        return Math.abs(sumNodes - sumLeafs);
    }

    public void visitNode(TreeNode node) {
    	if(node.getDepth() %2 == 0){
            sumNodes += node.getValue();
        } 
    }

    public void visitLeaf(TreeLeaf leaf) {
    	if(leaf.getColor() == Color.GREEN){
            sumLeafs += leaf.getValue();
        }
    }
}
/* */
public class Solution {
     private static int [] values;
    private static Color [] colors;
    private static HashMap<Integer, List<Integer>> edgesMap;
    
    public static Tree solve() {
        //read the tree from STDIN and return its root as a return value of this function
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        values = new int[n];
        colors = new Color[n];
        edgesMap = new HashMap<Integer, List<Integer>>(n);
        for(int i = 0; i < n; i++){
            values[i] = sc.nextInt();    
        }
        
        for(int i = 0; i < n; i++){
            colors[i] = (sc.nextInt() == 0? Color.RED : Color.GREEN);    
        }
        
         for(int i = 0; i < n-1; i++){            
            int p = sc.nextInt();
            int c = sc.nextInt();
            List<Integer> l = edgesMap.get(p);
            if(l == null){
               
                l = new ArrayList<Integer>();
                edgesMap.put(p, l);
            } 
            l.add(c); 
            
            //Bidirectional
            List<Integer> l2 = edgesMap.get(c);
            if(l2 == null){
               
                l2 = new ArrayList<Integer>();
                edgesMap.put(c, l2);
            } 
            l2.add(p); 
        }
        sc.close();
        
       TreeNode r = new TreeNode(values[0], colors[0], 0);
       
       if(n > 1){
           for(int childPos : edgesMap.get(1)){
               edgesMap.get(childPos).remove(Integer.valueOf(1));
               fillTree(r, childPos, 0); 
            }
        }        
        
        return r;
    }
    
    public static void fillTree(TreeNode parentNode, int childPosition, int parentDepth){
        if(edgesMap.get(childPosition) == null || edgesMap.get(childPosition).isEmpty()){
            TreeLeaf childLeaf = new TreeLeaf(values[childPosition -1], colors[childPosition -1], parentDepth + 1);
            parentNode.addChild(childLeaf);
        }  
        else {
            TreeNode childNode = new TreeNode(values[childPosition -1], colors[childPosition -1], parentDepth + 1);
            //for all children of the child

            for(int grandChildPos : edgesMap.get(childPosition)){
               edgesMap.get(grandChildPos).remove(Integer.valueOf(childPosition));
               fillTree(childNode, grandChildPos, parentDepth + 1);
            }
            
            parentNode.addChild(childNode);
        }
    }


    public static void main(String[] args) {
      	Tree root = solve();
		SumInLeavesVisitor vis1 = new SumInLeavesVisitor();
      	ProductOfRedNodesVisitor vis2 = new ProductOfRedNodesVisitor();
      	FancyVisitor vis3 = new FancyVisitor();

      	root.accept(vis1);
      	root.accept(vis2);
      	root.accept(vis3);

      	int res1 = vis1.getResult();
      	int res2 = vis2.getResult();
      	int res3 = vis3.getResult();

      	System.out.println(res1);
     	System.out.println(res2);
    	System.out.println(res3);
	}
}
