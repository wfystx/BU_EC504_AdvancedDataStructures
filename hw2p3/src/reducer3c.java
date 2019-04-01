package edu.bu.ec504.hw2;

/**
 * @Author: Fuyao Wang
 * @Description:
 * @Date: Created in 22:14 2019-03-31
 * @Modified By:
 */

import edu.bu.ec504.hw2.Reducer;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class reducer3c extends Reducer {

    public reducer3c(String theKey, ArrayList<Integer> theValue) {
        super(theKey, theValue);
    }

    /**
     * 	Default constructor - required by the mapReduce engine, though it need not do anything.
     */
    public reducer3c() { super(); }


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
        //results.sort(new EmissionTemplate.CompareEmission());

        // ... output in sorted order
        StringBuilder output = new StringBuilder();
        Map<Integer, Integer> h = new HashMap<>();
        int len = 0;
        for (ReducerEmission result : results) {
            len = result.getKey().length();
            if (!h.containsKey(len)) {
                h.put(len, 1);
            } else {
                h.put(len, h.get(len) + 1);
            }
        }
        ArrayList<Integer> sortedKeys =
                new ArrayList<Integer>(h.keySet());
        Collections.sort(sortedKeys);
        for  (Integer key : sortedKeys) {
            if(key != 0) {
                output.append(h.get(key)).append(" words of length ").append(key).append("\n");
            }
        }

        return output.toString();
    }
}
