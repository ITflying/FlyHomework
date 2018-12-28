package com.hiabby.flyleetcode.treestructure.bstree;

import java.util.Objects;

/**
 * @desc 二叉搜索树，根节点左边比根节点小，右边比根节点大
 * @date 2018/11/15
 **/
public class BSTree<T extends Comparable<T>> {

    /**
     * 根结点
     */
    private BSTNode<T> mRoot;

    public BSTNode<T> getmRoot() {
        return mRoot;
    }

    public class BSTNode<T extends Comparable<T>> {
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
    private void preOrder(BSTNode<T> node) {
        if (Objects.nonNull(node)) {
            System.out.print(String.format("%s\t", node.key));
            preOrder(node.leftChildren);
            preOrder(node.rightChildren);
        }
    }

    public void preOrder() {
        System.out.print("前序遍历：\n");
        preOrder(mRoot);
    }

    /**
     * 中序遍历：先左后根节点最后右
     */
    private void middleOrder(BSTNode<T> node) {
        if (Objects.nonNull(node)) {
            middleOrder(node.leftChildren);
            System.out.print(String.format("%s\t", node.key));
            middleOrder(node.rightChildren);
        }
    }

    public void middleOrder() {
        System.out.print("\n中序遍历：\n");
        middleOrder(mRoot);
    }

    /**
     * 后序遍历：先左后右最后根节点
     */
    private void backOrder(BSTNode<T> node) {
        if (Objects.nonNull(node)) {
            backOrder(node.leftChildren);
            backOrder(node.rightChildren);
            System.out.print(String.format("%s\t", node.key));
        }
    }

    public void backOrder() {
        System.out.print("\n后序遍历：\n");
        backOrder(mRoot);
    }

    /**
     * 递归查找目标值的节点
     */
    private BSTNode<T> recursiveSearch(BSTNode<T> node, T key) {
        if (Objects.isNull(node)) {
            return node;
        }
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            recursiveSearch(node.leftChildren, key);
        } else if (compare > 0) {
            recursiveSearch(node.rightChildren, key);
        }
        return node;
    }

    public BSTNode<T> recursiveSearch(T key) {
        return recursiveSearch(mRoot, key);
    }

    /**
     * 非递归查找目标值的节点
     */
    private BSTNode<T> commonSearch(BSTNode<T> node, T key) {
        while (Objects.nonNull(node)) {
            int compare = key.compareTo(node.key);
            if (compare < 0) {
                node = node.leftChildren;
            } else if (compare > 0) {
                node = node.rightChildren;
            } else {
                return node;
            }
        }
        return node;
    }

    public BSTNode<T> commonSearch(T key) {
        return commonSearch(mRoot, key);
    }

    /**
     * 找最大值，也就是右节点中最后面的节点
     */
    private BSTNode<T> findMax(BSTNode<T> node) {
        while (Objects.nonNull(node.rightChildren)) {
            node = node.rightChildren;
        }
        return node;
    }

    public BSTNode<T> findMax() {
        return findMax(mRoot);
    }

    /**
     * 找最小值，也就是左节点中最后面的节点
     */
    private BSTNode<T> findMin(BSTNode<T> node) {
        while (Objects.nonNull(node.leftChildren)) {
            node = node.leftChildren;
        }
        return node;
    }

    public BSTNode<T> findMin() {
        return findMin(mRoot);
    }

    /**
     * 前驱和后继针对的是遍历顺序来进行判断的，指的是遍历完成后与该节点相邻的前面或后面的节点，一般说的是中序遍历
     * 一个节点如果有左子树，那么其前驱节点是其左子树中的最大值，因为先左后中再右的顺序，本身相当于中节点
     * 若无左子树，其前驱节点在从根节点到key的路径上，比key小的最大值。
     */
    private BSTNode<T> findMiddleFrontNode(BSTNode<T> node) {
        // 如果左子树不为空，那么就是搜索左子树中最大的值
        // 因为中序遍历是先左后右，所以最后找到的肯定是右节点，也就是最大的那个
        if (Objects.nonNull(node)) {
            return findMax(node.leftChildren);
        }

        // 如果左子树为空，那么就分为两种情况考虑，一种是该节点为左节点，另一种是右节点
        // 左节点：找到该节点最低的上面有右节点的父节点
        //         这是因为中序遍历先左后根再右的特点，它自身的父节点就不会是它的前驱节点，就要往上追溯有右节点的父节点，
        //         同时自身不能为左节点，要为右节点。
        //         之所以要有右节点，因为没有右节点会一直遍历下去，直到找到有右节点的父节点，
        // 右节点：它的父节点就是它的前驱节点，这是因为中序遍历先左后根再右的特点
        //         同样是因为中序遍历先左后根再右的特点，根节点就是它的前驱节点。
        BSTNode<T> parent = node.parent;
        while (Objects.nonNull(parent) && node == parent.leftChildren) {
            node = parent;
            parent = node.parent;
        }
        return parent;
    }

    public BSTNode<T> findMiddleFrontNode() {
        return findMiddleFrontNode(mRoot);
    }

    /**
     * 一个节点的后继节点是右子树的最小值，若无右子树，其后继节点在从根节点到key的路径上，比key大的最小值。
     * 这个后继节点跟前驱节点相反就好了，右子树不为空取右子树最小的；
     * 如果右子树为空，那么还是分两种情况：
     * （1）为左节点那么父节点就是后继节点；
     * （2）为右节点则需要找到上一个有左节点的最低父节点，之所以是左节点，是因为先左后中再右，
     * 右节点的下一个就是上一层的中节点，之所以是要有左节点，则是因为要找到真正的上一层
     */
    private BSTNode<T> findMiddleBackNode(BSTNode<T> node) {
        if (Objects.nonNull(node)) {
            return findMin(node.rightChildren);
        }

        BSTNode<T> parent = node.parent;
        while (Objects.nonNull(parent) && node == parent.rightChildren) {
            node = parent;
            parent = node.parent;
        }
        return parent;
    }

    public BSTNode<T> findMiddleBackNode() {
        return findMiddleBackNode(mRoot);
    }


    /**
     * 插入的逻辑是根据二叉搜索树左节点比中节点小，右节点比左节点大的特点
     * 步骤：先找到插入位置，再处理插入逻辑
     */
    private void insert(BSTree<T> bst, BSTNode<T> insertNode) {
        int compareRes;
        BSTNode<T> insertNodeParent = null;
        BSTNode<T> checkNode = bst.mRoot;

        // 1. 首先查询到插入位置，循环往下找到位置
        while (checkNode != null) {
            insertNodeParent = checkNode;

            compareRes = insertNode.key.compareTo(checkNode.key);
            if (compareRes < 0) {
                checkNode = checkNode.leftChildren;
            } else if (compareRes > 0) {
                checkNode = checkNode.rightChildren;
            } else {
                // 不允许相同节点
                return;
            }
        }

        // 2. 然后在最终点选择左边或者右边插入
        insertNode.parent = insertNodeParent;
        if (insertNodeParent == null) {
            bst.mRoot = insertNode;
        } else {
            compareRes = insertNode.key.compareTo(insertNodeParent.key);
            if (compareRes < 0) {
                insertNodeParent.leftChildren = insertNode;
            } else {
                insertNodeParent.rightChildren = insertNode;
            }
        }
    }

    /**
     * 使用的时候要使用前序遍历的顺序插入
     *
     * @param key
     */
    public void insert(T key) {
        BSTNode<T> insertNode = new BSTNode<>(key, null, null, null);

        insert(this, insertNode);
    }

    /**
     * 删除节点主要是将断开的节点连接上，可以使用后继节点来完成查找
     * 1、没有左右子节点，可以直接删除
     * 2、存在左节点或者右节点，删除后需要对子节点移动
     * 3、同时存在左右子节点，不能简单的删除，但是可以通过和后继节点交换后转换为前两种情况
     * 特殊：删除根部节点
     */
    private BSTNode<T> remove(BSTree<T> tree, BSTNode<T> removeNode) {
        BSTNode<T> childNode;
        BSTNode<T> deleteNode;

        // 1. 首先处理找到要删除的节点
        if (Objects.isNull(removeNode.leftChildren) || Objects.isNull(removeNode.rightChildren)) {
            // 如果只存在左或右子节点，或不存在子节点，那么直接删除原节点
            deleteNode = removeNode;
        } else {
            // 如果同时存在左右节点，那么就需要找到后缀节点来替代原节点，然后删除的节点就变成了后继节点
            // 就又变成了上面两种情况（左右节点不同时存在）
            deleteNode = findMiddleBackNode(removeNode);
        }

        // 2. 处理左右子节点，子节点只会有一个存在或不存在的情况，所以直接赋值判断存在与否，存在就建立被删节点父节点和子节点的关系
        if (Objects.nonNull(deleteNode.leftChildren)) {
            childNode = deleteNode.leftChildren;
        } else {
            childNode = deleteNode.rightChildren;
        }

        if (Objects.nonNull(childNode)) {
            // 建立子节点和父节点的关系
            childNode.parent = deleteNode.parent;
        }

        // 3. 最后处理删除节点，建立被删父节点和子节点的关系
        if (Objects.isNull(deleteNode.parent)) {
            // 如果被删除节点的父节点为空，说明删除的是根节点，那么把树的根节点赋值为子节点就好了。
            tree.mRoot = childNode;
        } else if (deleteNode == deleteNode.parent.leftChildren) {
            deleteNode.parent.leftChildren = childNode;
        } else {
            deleteNode.parent.rightChildren = childNode;
        }

        if (deleteNode != removeNode) {
            // 被删的节点就变成了后继节点，将后继节点的值赋给删除的
            removeNode.key = deleteNode.key;
        }

        return deleteNode;
    }

    public void remove(T key) {
        BSTNode<T> removeNode, deleteNode;

        removeNode = commonSearch(mRoot, key);
        if (Objects.nonNull(removeNode)) {
            deleteNode = remove(this, removeNode);
            if (Objects.nonNull(deleteNode)) {
                // gc回收
                deleteNode = null;
            }
        }
    }


    /**
     * 销毁二叉树，直接递归从下往上赋值为null就好了
     *
     * @param node
     */
    public void destory(BSTNode<T> node) {
        if (Objects.isNull(node)) {
            return;
        }

        destory(node.leftChildren);
        destory(node.leftChildren);
        node = null;
    }

    public void destory() {
        if (Objects.nonNull(mRoot)) {
            destory(mRoot);
            mRoot = null;
        }
    }

    /**
     * 打印二叉搜索树，直接递归往下遍历打印就好了
     *
     * @param node 节点实体
     * @param key  父节点的值
     * @param type 该节点的类型，0表示为根节点，1代表右孩子，-1代表左孩子
     */
    public void print(BSTNode<T> node, T key, int type) {
        if (Objects.nonNull(node)) {
            if (type == 0) {
                System.out.println(String.format("%2d 是根节点", node.key));
            } else {
                System.out.println(String.format("%2d 是值为 %2d 的%s节点", node.key, key, type == 1 ? "right" : "left"));
            }

            print(node.leftChildren, node.key, -1);
            print(node.rightChildren, node.key, 1);
        }
    }

    public void print() {
        if (Objects.nonNull(mRoot)) {
            print(mRoot, mRoot.key, 0);
        }
    }

    /**
     * 获取节点数量
     *
     * @param node
     * @param num
     * @return
     */
    public int getNodeNum(BSTNode<T> node, int num) {
        if (Objects.isNull(node)) {
            return num;
        }
        num++;
        num = getNodeNum(node.leftChildren, num);
        num = getNodeNum(node.rightChildren, num);
        return num;
    }

    public int getNodeNum() {
        if (Objects.nonNull(mRoot)) {
            return getNodeNum(mRoot, 0);
        }
        return 0;
    }


    /**
     * 获取满二叉树指定层数的节点数量
     *
     * @return
     */
    public int getFullTreeTargetLevelNodeNum(int level) {
        if (level <= 0) {
            return 0;
        }
        return (int) Math.pow(2, (level - 1));
    }

    /**
     * 获取树的高度
     *
     * @param node
     * @return
     */
    public int getTreeHeight(BSTNode<T> node) {
        if (Objects.isNull(node)) {
            return 0;
        }

        // 这个相当于到最底层，然后再往上一一加一
        int left = getTreeHeight(node.leftChildren);
        int right = getTreeHeight(node.rightChildren);
        return left > right ? (left + 1) : (right + 1);
    }

    public int getTreeHeight() {
        if (Objects.nonNull(mRoot)) {
            return getTreeHeight(mRoot);
        }
        return 0;
    }

    /**
     * 打印树形结构
     *
     * @param node
     * @param height
     * @param maxLevelNum
     */
    public void display(BSTNode<T> node, int height, int maxLevelNum) {
        for (int level = 0; level < height; level++) {
            StringBuilder stringBuilder = new StringBuilder();
            int nodeNum = getFullTreeTargetLevelNodeNum(level);
            Integer[] levelNode = new Integer[nodeNum];

            int middle = maxLevelNum / nodeNum;
            for (int i = 0; i < middle; i++) {
                stringBuilder.append("\t");
            }
        }
    }

    public void display() {
        if (Objects.nonNull(mRoot)) {
            int height = getTreeHeight();
            int maxLevelNum = getFullTreeTargetLevelNodeNum(height);
            display(mRoot, height, maxLevelNum + 1);
        }
    }

}
