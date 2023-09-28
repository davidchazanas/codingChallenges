import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaperStrip {
    public static int minPieces(int[] original, int[] desired) {
        
        List<Integer> solution = new ArrayList<Integer>();
        List<List<Integer>> solutionList = new ArrayList<List<Integer>>();

        List<Integer> originalList = new ArrayList<Integer>();
        List<Integer> desiredList = new ArrayList<Integer>();
        for(int i = 0; i < desired.length; i++){
            desiredList.add(desired[i]);
        }

        for(int i = 0; i < original.length; i++){
            originalList.add(original[i]);
        }

        for(int k = 0; k < desiredList.size() -1; k++){
            int currentDesired = desiredList.get(k);
            int indexOfDesiredOnOriginal = originalList.indexOf(currentDesired);

            solution = new ArrayList<Integer>();

            solution.add(originalList.get(indexOfDesiredOnOriginal));
            //while next on original equals next on desired, add to sub and check again

            while(k < desiredList.size() -1 && indexOfDesiredOnOriginal + 1 < originalList.size() && originalList.get(indexOfDesiredOnOriginal + 1) == desiredList.get(k + 1)){
                k++;
                indexOfDesiredOnOriginal++;
                solution.add(originalList.get(indexOfDesiredOnOriginal));
            }
            solutionList.add(solution);
        }
        return solutionList.size();
    }

    public static void main(String[] args) {
        int[] original = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int[] desired = new int[] { 1, 2, 3, 4 , 5, 6 ,7 ,8 ,9 };
        System.out.println(PaperStrip.minPieces(original, desired));
    }
}
