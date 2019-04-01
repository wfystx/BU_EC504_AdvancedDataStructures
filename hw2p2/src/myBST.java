/**
 * @Author: Fuyao Wang
 * @Description: My implement of BST using rotation to get the target BST.
 * @Date: Created in 10:32 2019-03-27
 * @Modified By:
 */

package edu.bu.ec504.hw2;
import java.util.ArrayList;

public class myBST<keyType extends Comparable<keyType>> extends BST<keyType>{
    public ArrayList<BstRotation> rotation_path = new ArrayList<>();
    public ArrayList<BstRotation> rotation = new ArrayList<>();
    public myBST(Iterable<keyType> keys){
        super(keys);
    }

    /**
     * @Description Override function of parent class //11:21 2019-03-31 by freddie
     * @Param [target Tree]
     * @return java.util.ArrayList<edu.bu.ec504.hw2.BstRotation>
     **/
    @Override
    public ArrayList<BstRotation> rotateTo(BST<keyType> otherTree) {
        BST<keyType> root = this;
        getNew(root, otherTree);
        return this.rotation;
    }

    /**
     * @Description Firstly check if the root's key is empty, then compare the left and right node of the tree with
     * the target tree to see if there is a possible rotate path recursively. // 2019-03-31 by freddie
     * @Param [root, target]
     * @return edu.bu.ec504.hw2.BST<keyType>
     **/
    private BST<keyType> getNew(BST<keyType> root, BST<keyType> otherTree){
        if (root.key == null) return root;
        else {
            rotation_path = findPath(root,otherTree);
            root = rotate(root,this.rotation_path);
            this.rotation.addAll(rotation_path);
            this.rotation_path.clear();
            if (root.rightChild != null) return getNew(root.rightChild,otherTree.rightChild);
            else if (root.leftChild != null) return getNew(root.leftChild,otherTree.leftChild);
            else return root;
        }
    }

    /**
     * @Description //If there is, create a new rotation of zig or zag depending on it's tht left or right.
     * 2019-03-31 by freddie
     * @Param [before, target]
     * @return java.util.ArrayList<edu.bu.ec504.hw2.BstRotation>
     **/
    private ArrayList<BstRotation> findPath(BST<keyType> before, BST<keyType> target) {

        if(before.key.compareTo(target.key)==0) return this.rotation_path;
        else if(before.key.compareTo(target.key)>0){
            if(before.leftChild == null) return null;
            else {
                this.rotation_path.add(new BstRotation(before.nodeId, BstRotation.RotationType.ZIG));
                return findPath(before.leftChild, target);
            }
        }
        else if(before.key.compareTo(target.key) < 0) {
            if (before.rightChild == null) return null;
            else {
                this.rotation_path.add(new BstRotation(before.nodeId, BstRotation.RotationType.ZAG));
                return findPath(before.rightChild, target);
            }
        }
        return null;
    }

    /**
     * @Description Get the direction of the rotation and pass values to zigzag function //11:18 2019-03-31 by freddie
     * @Param [root, rotation]
     * @return edu.bu.ec504.hw2.BST<keyType>
     **/
    private BST<keyType> rotate(BST<keyType> root, ArrayList<BstRotation> rotation ){
        for(BstRotation x: rotation){
            String direction =x.toString().substring(0,3);
            if (direction.equals("ZIG")) root = zigzag(root,1);
            else root = zigzag(root,0);
        }
        return root;
    }

    /**
     * @Description Rotate the node by direction //11:19 2019-03-31 by freddie
     * @Param [node, direction]
     * @return edu.bu.ec504.hw2.BST<keyType>
     **/
    private BST<keyType> zigzag(BST<keyType> node, int direction){
        BST<keyType> nodeA, nodeB, para1, para2, para3;
        if(direction==1){
            nodeA = node.leftChild;
            nodeB = node;
            para1 = node.leftChild.getLeftChild(); //leftChild subtree of nodeA
            para2 = node.leftChild.getRightChild();  //rightChild subtree of nodeA
            para3 = node.getRightChild();  //rightChild subtree of nodeB
            nodeA.rightChild = nodeB;
            nodeA.leftChild = para1;
            nodeB.leftChild = para2;
            nodeB.rightChild = para3;
            return nodeA;
        }else{
            nodeB = node.rightChild;
            nodeA = node;
            para1 = node.getLeftChild(); //leftChild subtree of nodeA;
            para2 = node.rightChild.getLeftChild(); //leftChild subtree of nodeB;
            para3 = node.rightChild.getRightChild(); //leftChild subtree of nodeB;
            nodeB.rightChild = para3;
            nodeB.leftChild = nodeA;
            nodeA.leftChild = para1;
            nodeA.rightChild = para2;
            return nodeB;
        }
    }
}