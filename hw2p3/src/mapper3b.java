package edu.bu.ec504.hw2;

import java.util.HashSet;

/**
 * @Author: Fuyao Wang
 * @Description:
 * @Date: Created in 21:29 2019-03-31
 * @Modified By:
 */
public class mapper3b extends Mapper {

    /**
     * A helpful constructor for instantiating a mapper based on a key-value pair.
     */
    public mapper3b(Integer theKey, String theValue) { super(theKey, theValue); }

    /**
     * Default constructor - required by the mapReduce engine, though it need not do anything.
     */
    public mapper3b() { super(); }

    /**
     * 	This does the actual work of your mapper.
     */
    @Override
    public MapperEmissionList call() {
        MapperEmissionList EmissionList = new MapperEmissionList();
        if(key%2==0){
            String[] splited = value.split("\\s+");
            for (int ii=0; ii<splited.length; ii++) {
                splited[ii] = splited[ii].replaceAll("[^\\w]", "");
                EmissionList.add(
                        new MapperEmission(splited[ii] , Integer.MIN_VALUE) // emit the pair (ii-th character,1)
                );
            }
        } else{
            String[] splited = value.split("\\s+");
            for (int ii=0; ii<splited.length; ii++) {
                splited[ii] = splited[ii].replaceAll("[^\\w]", "");
                EmissionList.add(
                        new MapperEmission(splited[ii] , 1) // emit the pair (ii-th character,1)
                );
            }
        }

        return EmissionList;
    }
}
