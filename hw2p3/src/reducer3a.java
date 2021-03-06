package edu.bu.ec504.hw2;

/**
 * @Author: Fuyao Wang
 * @Description:
 * @Date: Created in 21:13 2019-03-31
 * @Modified By:
 */
import edu.bu.ec504.hw2.Reducer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class reducer3a extends Reducer {

    public reducer3a(String theKey, ArrayList<Integer> theValue) {
        super(theKey, theValue);
    }

    /**
     * 	Default constructor - required by the mapReduce engine, though it need not do anything.
     */
    public reducer3a() { super(); }


    /**
     * 	This does the actual work of your reducer.
     */
    @Override
    public ReducerEmissionList call() {
        int sum = 0;
        for (Integer ii : value) {
            sum += ii;
        }
        ReducerEmissionList EL= new ReducerEmissionList();
        EL.add(new ReducerEmission(key, sum));
        return EL;
    }

    /**
     * Coalesces reduction output.
     * @return A string representing the coalesced reduction output.
     */

    @Override
    public String print() {
        /* THIS IS WHERE YOUR REDUCE EMISSIONS ARE SUMMARIZED. */
        ArrayList<ReducerEmission> results = new ArrayList<>();
        for (Future<ReducerEmissionList> future: Reducer.getFutures()) {
            try {
                List<ReducerEmission> emittedList = future.get();
                results.addAll(emittedList);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // ... sort by value
        results.sort(new EmissionTemplate.CompareEmission());

        // ... output in sorted order
        StringBuilder output = new StringBuilder();
        int count = 0;
        for (ReducerEmission result : results)
            if(result.getValue()==1){
                count++;
                output.append(result.getKey()) // print out each emission from the reducer, in turn
                        .append(": ")
                        .append(result.getValue())
                        .append("\n");
            }
        output.insert(0, "\n");
        output.insert(0, count+" unique words: ");

        return output.toString();
    }
}
