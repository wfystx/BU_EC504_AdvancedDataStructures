/**
 * @Author: Fuyao Wang
 * @Description: part of code referenced from GreeksforGreeks.com: Max Heap in Java
 * https://www.greeksforgreeks.org/max-heap-in-java
 * @Date: Created in 16:32 2019-03-30
 * @Modified By:
 */

package fuyao;

import java.util.Arrays;

public class myDataStructure {
    private int[] heap;
    private int size, max;
/**
 * @Description Constructor//16:48 2019-03-30 by freddie
 * @Param [number of elements in the heap]
 * @return
 **/
    public myDataStructure(int max) {
        this.max = max;
        this.size = 0;
        heap = new int[this.max + 1];
        heap[0] = Integer.MAX_VALUE;
    }
    
    
/**
 * @Description If a node is stored in a index k, then its left child is stored at index 2k+1 and its right
 * child at index 2k+2//16:39 2019-03-30 by freddie
 **/

    /**
     * @Description get the Parent index of input node//16:40 2019-03-30 by freddie
     * @Param [index of the node]
     * @return int
     **/
    private int getParent(int index) {return index/2;}
    
    /**
     * @Description get the left child index of input node//16:42 2019-03-30 by freddie
     * @Param [index]
     * @return int
     **/
    private int getLeftChild(int index) { return (2*index); }
    
    /**
     * @Description get the right index of input node//16:42 2019-03-30 by freddie
     * @Param [index]
     * @return int
     **/
    private int getRightChild(int index) { return (2*index)+1; }
    
    /**
     * @Description swap two values//16:43 2019-03-30
     * @Param [i1, i2]
     * @return void
     **/
    private void swap(int i1, int i2) { int temp; temp = heap[i1]; heap[i1] = heap[i2]; heap[i2] = temp; }

    /**
     * @Description Max heapify the given subtree resursively by assuming the left and right subtrees are already
     * heapified, we only need to fix the root//16:44 2019-03-30 by freddie
     * @Param [index]
     * @return void
     **/
    private void heapify(int index) {
        if (isLeaf(index)) return;
        if (heap[index] < heap[getLeftChild(index)] ||
                heap[index] < heap[getRightChild(index)]) {
            if (heap[getLeftChild(index)] > heap[getRightChild(index)]) {
                swap(index, getLeftChild(index));
                heapify(getLeftChild(index));
            }
            else {
                swap(index, getRightChild(index));
                heapify(getRightChild(index));
            }
        }
    }

    /**
     * @Description If new key is smaller than its parent, then we don't need to do anything
     * Otherwise, we need to traverse up to fix the violated heap property//16:53 2019-03-30
     * @Param [element]
     * @return int []
     **/
    public int insert(int element) {
        heap[++size] = element;
        int current = size;
        while (heap[current] > heap[getParent(current)]) {
            swap(current, getParent(current));
            current = getParent(current);
        }
        return current;
    }

    /**
     * @Description Extract the root which is the max of the heap and heapify the heap again//17:08 2019-03-30 by freddie
     * @Param []
     * @return int max
     **/
    public int extractMax() {
        int max = heap[1];
        heap[1] = heap[size--];
        heapify(1);
        return max;
    }

    /**
     * @Description Extract the second max element by extracting the max first and insert the max to the heap after
     * extracting the second again //17:13 2019-03-30 by freddie
     * @Param []
     * @return int secondmax
     **/
    public int extractSecondMax() {
        int max = extractMax();
        int secondmax = extractMax();
        insert(max);
        return secondmax;
    }

    /**
     * @Description Decrease key of the specific index and re-heapify the subtree because in max-heap the parent of
     * that index is bigger than it so we just need to heapify the subtree instead of the whole heap
     * //17:15 2019-03-30 by freddie
     * @Param [ID, lowerKey]
     * @return void
     **/
    public void decreasekey(int ID, Integer lowerKey){
        heap[ID] = lowerKey;
        heapify(ID);
    }

    /**
     * @Description Because a max-heap is a complete binary tree so we can check if a node is leaf by
     * multiple it by 2//17:02 2019-03-30 by freddie
     * @Param [index]
     * @return boolean True if is leaf. False if isn't leaf
     **/
    public boolean isLeaf(int index){
        if(index >= (size/2) && index <= size) return true;
        else return false;
    }

    /**
     * @Description Override toString()//17:22 2019-03-30 by freddie
     * @Param []
     * @return java.lang.String
     **/
    @Override
    public String toString() {
        String out = "size= " + size + "\n";
        for (int i = 1; i <= size ; i++) out += (heap[i]+"\n");
        return out;
    }
}