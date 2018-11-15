package com.hiabby.flyleetcode.treestructure.bstree;

import java.util.Comparator;
import java.util.Objects;

/**
 * @desc
 * @date 2018/11/15
 **/
public class BSTree<T extends Comparable<T>>{

    /**
     * 根结点
     */
    private BSTNode<T> mRoot;

    public class BSTNode<T extends Comparable<T>>{
        T key;
        BSTNode<T> leftChildren;
        BSTNode<T> rightChildren;
        BSTNode<T> parentChildren;

        public BSTNode(T key, BSTNode<T> leftChildren, BSTNode<T> rightChildren, BSTNode<T> parentChildren) {
            this.key = key;
            this.leftChildren = leftChildren;
            this.rightChildren = rightChildren;
            this.parentChildren = parentChildren;
        }
    }

    private void preOrder(BSTNode<T> node){
        if (Objects.nonNull(node)){
            String.format("%s ", node.key);
            preOrder(node.leftChildren);
            preOrder(node.rightChildren);
        }
    }

    public void preOrder(){
        preOrder(mRoot);
    }

    private void middleOrder(BSTNode<T> node){
        if (Objects.nonNull(node)){
            middleOrder(node.leftChildren);
            String.format("%s ", node.key);
            middleOrder(node.rightChildren);
        }
    }

    public void middleOrder(){
        middleOrder(mRoot);
    }

    private void backOrder(BSTNode<T> node){
        if (Objects.nonNull(node)){
            backOrder(node.leftChildren);
            backOrder(node.rightChildren);
            String.format("%s ", node.key);
        }
    }

    public void backOrder(){
        backOrder(mRoot);
    }

    private BSTNode<T> recursiveSearch(BSTNode<T> node, T key){
        if (Objects.isNull(node)){
            return node;
        }
        int compare = key.compareTo(node.key);
        if (compare < 0){
            recursiveSearch(node.leftChildren, key);
        } else if (compare > 0){
            recursiveSearch(node.rightChildren, key);
        }
        return node;
    }

    public BSTNode<T> recursiveSearch(T key){
        return recursiveSearch(mRoot, key);
    }


    private BSTNode<T> commonSearch(BSTNode<T> node, T key){
        if (Objects.nonNull(node)){
            while(Objects.nonNull(node)){
                int compare = key.compareTo(node.key);
                if (compare < 0){
                    commonSearch(node.leftChildren, key);
                } else if (compare > 0){
                    commonSearch(node.rightChildren, key);
                } else{
                    return node;
                }
            }
        }
        return node;
    }

    public BSTNode<T> commonSearch(T key){
        return commonSearch(mRoot, key);
    }


    private BSTNode<T> findMax(BSTNode<T> node){
        if (Objects.isNull(node)){
            while (Objects.nonNull(node.rightChildren)){
                node = node.rightChildren;
            }
        }
        return node;
    }

    private BSTNode<T> findMax(){
        return findMax(mRoot);
    }

    private BSTNode<T> findMin(BSTNode<T> node){
        if (Objects.isNull(node)){
            while (Objects.nonNull(node.leftChildren)){
                node = node.leftChildren;
            }
        }
        return node;
    }

    private BSTNode<T> findMin(){
        return findMin(mRoot);
    }
}
