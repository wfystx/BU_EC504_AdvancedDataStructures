package edu.bu.ec504.hw2;

/**
 * @Author: Fuyao Wang
 * @Description:
 * @Date: Created in 22:13 2019-03-31
 * @Modified By:
 */


public class mapper3c extends Mapper {

    /**
     * A helpful constructor for instantiating a mapper based on a key-value pair.
     */
    public mapper3c(Integer theKey, String theValue) { super(theKey, theValue); }

    /**
     * Default constructor - required by the mapReduce engine, though it need not do anything.
     */
    public mapper3c() { super(); }

    /**
     * 	This does the actual work of your mapper.
     */
    @Override
    public MapperEmissionList call() {
        MapperEmissionList EmissionList = new MapperEmissionList();
        String[] splited = value.split("\\s+");
        for (int ii=0; ii<splited.length; ii++) {
            splited[ii] = splited[ii].replaceAll("[^\\w]", "");
            EmissionList.add(
                    new MapperEmission(splited[ii] , 1) // emit the pair (ii-th character,1)
            );
        }
        return EmissionList;
    }
}
