package com.hiabby.flyleetcode.treestructure.bstree;

import java.util.Objects;

/**
 * @desc 二叉搜索树，根节点左边比根节点小，右边比根节点大
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
        BSTNode<T> parent;

        public BSTNode(T key, BSTNode<T> leftChildren, BSTNode<T> rightChildren, BSTNode<T> parent) {
            this.key = key;
            this.leftChildren = leftChildren;
            this.rightChildren = rightChildren;
            this.parent = parent;
        }
    }

    /**
     * 前序遍历：先根节点后左最后右
     */
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

    /**
     * 中序遍历：先左后根节点最后右
     */
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

    /**
     * 后序遍历：先左后右最后根节点
     */
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

    /**
     * 非递归查找目标值的节点
     */
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

    /**
     * 非递归查找目标值的节点
     */
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

    /**
     * 找最大值，也就是右节点中最后面的节点
     */
    private BSTNode<T> findMax(BSTNode<T> node){
        if (Objects.isNull(node)){
            while (Objects.nonNull(node.rightChildren)){
                node = node.rightChildren;
            }
        }
        return node;
    }

    public BSTNode<T> findMax(){
        return findMax(mRoot);
    }

    /**
     * 找最小值，也就是左节点中最后面的节点
     */
    private BSTNode<T> findMin(BSTNode<T> node){
        if (Objects.isNull(node)){
            while (Objects.nonNull(node.leftChildren)){
                node = node.leftChildren;
            }
        }
        return node;
    }

    public BSTNode<T> findMin(){
        return findMin(mRoot);
    }

    /**
     * 前驱和后继针对的是遍历顺序来进行判断的，指的是遍历完成后与该节点相邻的前面或后面的节点，一般说的是中序遍历
     * 一个节点的前驱节点是其左子树中的最大值，若无左子树，其前驱节点在从根节点到key的路径上，比key小的最大值。
     */
    private BSTNode<T> findMiddleFrontNode(BSTNode<T> node){
        // 如果左子树不为空，那么就是搜索左子树中最大的值
        // 因为中序遍历是先左后右，所以最后找到的肯定是右节点，也就是最大的那个
        if (Objects.nonNull(node)){
            return findMax(node.leftChildren);
        }

        // 如果左子树为空，那么就分为两种情况考虑，一种是该节点为左节点，另一种是右节点
        // 左节点：找到该节点最低的上面有左节点的父节点
        //         这是因为中序遍历先左后根再右的特点，它自身的父节点就不会是它的前驱节点，就要往上追溯有左节点的父节点，
        //         之所以要有左节点，因为没有左节点会一直遍历下去，直到找到有左节点的父节点
        // 右节点：它的父节点就是它的前驱节点，这是因为中序遍历先左后根再右的特点
        //         同样是因为中序遍历先左后根再右的特点，根节点就是它的前驱节点。
        BSTNode<T> parent = node.parent;
        while (Objects.nonNull(parent) && node == parent.leftChildren){
            node = parent;
            parent = node.parent;
        }
        return node;
    }

    public BSTNode<T> findMiddleFrontNode(){
        return findMiddleFrontNode(mRoot);
    }

    /**
     * 一个节点的后继节点是右子树的最小值，若无右子树，其后继节点在从根节点到key的路径上，比key大的最小值。
     * 这个后继节点跟前驱节点相反就好了，右子树不为空取右子树最小的；
     * 如果右子树为空，那么还是分两种情况：为左节点那么父节点就是后继节点，
     * 为右节点则需要找到上一个有左节点的最低父节点,之所以还是左节点，因为先左后中再右，
     * 左边的节点遍历完了，就需要遍历中节点，而有左节点的中节点才是后继节点
     */
    private BSTNode<T> findMiddleBackNode(BSTNode<T> node){
        if (Objects.nonNull(node)){
            return findMin(node.rightChildren);
        }

        BSTNode<T> parent = node.parent;
        while (Objects.nonNull(parent) && node == parent.leftChildren){
            node = parent;
            parent = node.parent;
        }
        return parent;
    }

    public BSTNode<T> findMiddleBackNode(){
        return findMiddleBackNode(mRoot);
    }
}
