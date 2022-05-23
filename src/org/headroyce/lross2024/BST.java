package org.headroyce.lross2024;

import java.util.ArrayList;
import java.util.List;

/**
 * the Binary Search Tree
 * @param <T> the type of data stored in nodes
 */
public class BST<T extends Comparable<T> > {

    private BSTNode<T> root;

    /**
     * constructs a BST with a root with null data (will change later)
     */
    public BST() {
        this.root = new BSTNode<>(null);
    }

    /**
     * adds and sorts a new node into the tree (or replaces data in root)
     *
     * @param data the data to add to the new node
     */
    public void add(T data) {
        if (root.data == null) {
            root.data = data;
        } else {
            BSTNode<T> newNode = new BSTNode<>(data);
            BSTNode<T> curr = this.root;
            boolean done = false;
            while (!done) {
                //less than or equal -> left child
                if (data.compareTo(curr.data) < 0) {
                    if (curr.left == null) {
                        curr.left = newNode;
                        done = true;
                    } else {
                        curr = curr.left;
                    }
                    //greater than -> right child
                } else if (data.compareTo(curr.data) > 0) {
                    if (curr.right == null) {
                        curr.right = newNode;
                        done = true;
                    } else {
                        curr = curr.right;
                    }
                }
            }
        }
    }

    /**
     * removes a node from the tree and rearranges tree
     * @param data data of the node to remove
     */
    public T remove(T data) {
        BSTNode<T> parent = null;
        BSTNode<T> curr = this.root;
        //TODO: fix, root is set to null when removed
        while(curr != null && curr.data != null){
            if (curr.data.compareTo(data) < 0) {
                //data is less than node we're looking for, go to right subtree
                parent = curr;
                curr = curr.right;
            } else if (curr.data.compareTo(data) > 0) {
                parent = curr;
                curr = curr.left;
            } else {
                //we found it, now remove it
                if (parent == null) {
                    //its the root
                    BSTNode<T> tempParent = root;
                    BSTNode<T> temp = root.left;
                    if (temp == null) {
                        T rtn = root.data;
                        root.data = null;
                        return rtn;
                    } else {
                        while (temp.right != null) {
                            tempParent = temp;
                            temp = temp.right;
                        }
                        T tempData = root.data;
                        root.data = temp.data;
                        temp.data = tempData;

                        return removeNode(tempParent, temp);
                    }
                } else {
                    if (curr.left != null && curr.right != null) {
                        //2 children
                        BSTNode<T> temp = curr;
                        BSTNode<T> leftGC = curr.left;
                        while (leftGC.right != null) {
                            temp = leftGC;
                            leftGC = leftGC.right;
                        }
                        //swap data
                        T tempData = curr.data;
                        curr.data = leftGC.data;
                        leftGC.data = tempData;

                        //wow very cool and swag
                        return removeNode(temp, leftGC);
                    } else {
                        return removeNode(parent, curr);
                    }
                }
            }
        }
        return null;
    }

    /**
     * handles removing a node with 0-1 children
     * @param child the child node/node to remove
     * @param parent parent of node to remove
     * @return data of node removed
     */
    private T removeNode(BSTNode<T> parent, BSTNode<T> child) {
        boolean isLeft = false;
        //handles 0-1 children. if point here is null in both cases, we know there are 0 children
        BSTNode<T> pointHere = child.left;
        if (pointHere == null){
            pointHere = child.right;
        }
        if (parent.left == child) {
                isLeft = true;
        }

        if (isLeft){
            parent.left = pointHere;
        } else {
            parent.right = pointHere;
        }
        return child.data;
    }
    /**
     * Traverses through the BST in-Order (least to greatest values)
     * @return list containing sorted data (not the nodes)
     */
    public List<T> inOrder() {
        List<T> list = new ArrayList<>();
        inOrderHelper(root, list);
        return list;
    }

    /**
     * In-Order Traversal handled with recursion instead of iteration,
     * therefore a helper function is necessary
     * @param node the node being evaluated
     * @param list the list to append evaluated items onto
     */
    private void inOrderHelper(BSTNode<T> node, List<T> list){
        if (node != null){
            if (node.left != null){
                inOrderHelper(node.left, list);
            }
            list.add(node.data);
            if (node.right != null){
                inOrderHelper(node.right, list);
            }
        }
    }

    /**
     * find a node and returns the data
     * @param data the data of the node to search for
     * @return the data of the node when found
     */
    public T findNode(T data){
        if (this.root.data != null){
            BSTNode<T> node = findNodeHelper(data, this.root);
            if (node != null){
                return node.data;
            }
        }
        return null;
    }

    /**
     * recursive helper function to find node
     * @param data data of node to find
     * @param node node being currently examined
     * @return null if the node is not found, otherwise return the node
     */
    private BSTNode<T> findNodeHelper(T data, BSTNode<T> node){
        if (node != null){
            if (node.left != null){
                BSTNode<T> left = findNodeHelper(data, node.left);
                if (left != null){
                    return left;
                }
            }
            if (node.right != null) {
                BSTNode<T> right = findNodeHelper(data, node.right);
                if (right != null) {
                    return right;
                }
            }
            if (node.data.compareTo(data) == 0){
                return node;
            }
        }
        return null;
    }

    /**
     * the nodes contained in the pointer-based binary search tree
     * @param <E> the type of the data stored
     */
    public class BSTNode<E extends Comparable<E> > {
        public BSTNode<E> left;
        public BSTNode<E> right;
        public T data;

        /**
         * constructor for BST nodes that sets pointers to null
         * @param data data to store
         */
        public BSTNode(T data){
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
}
