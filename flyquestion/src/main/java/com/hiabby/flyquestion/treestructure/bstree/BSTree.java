package com.hiabby.flyquestion.treestructure.bstree;


import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * @desc 二叉搜索树（Binary Search Tree），根结点左边比根结点小，右边比根结点大
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

        public BSTNode(T key, BSTNode<T> parent) {
            this.key = key;
            this.leftChildren = null;
            this.rightChildren = null;
            this.parent = parent;
        }

        public T getKey() {
            return key;
        }
    }

    /**
     * 前序遍历：先根结点后左最后右
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
     * 中序遍历：先左后根结点最后右
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
     * 后序遍历：先左后右最后根结点
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
     * 递归查找目标值的结点
     */
    private BSTNode<T> recursiveSearch(BSTNode<T> node, T key) {
        if (Objects.isNull(node)) {
            return node;
        }
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            return recursiveSearch(node.leftChildren, key);
        } else if (compare > 0) {
            return recursiveSearch(node.rightChildren, key);
        } else {
            return node;
        }
    }

    public BSTNode<T> recursiveSearch(T key) {
        return recursiveSearch(mRoot, key);
    }

    /**
     * 非递归查找目标值的结点
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
     * 找最大值，也就是右结点中最后面的结点
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
     * 找最小值，也就是左结点中最后面的结点
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
     * 前驱和后继针对的是遍历顺序来进行判断的，指的是遍历完成后与该结点相邻的前面或后面的结点，一般说的是中序遍历
     * 一个结点如果有左子树，那么其前驱结点是其左子树中的最大值，因为先左后中再右的顺序，本身相当于中结点
     * 若无左子树，其前驱结点在从根结点到key的路径上，比key小的最大值。
     */
    private BSTNode<T> findMiddleFrontNode(BSTNode<T> node) {
        // 如果左子树不为空，那么就是搜索左子树中最大的值
        // 因为中序遍历是先左后右，所以最后找到的肯定是右结点，也就是最大的那个
        if (Objects.nonNull(node.leftChildren)) {
            return findMax(node.leftChildren);
        }

        // 如果左子树为空，那么就分为两种情况考虑，一种是该结点为左结点，另一种是右结点
        // 左结点：找到该结点最低的上面有右结点的父结点
        //         这是因为中序遍历先左后根再右的特点，它自身的父结点就不会是它的前驱结点，就要往上追溯有右结点的父结点，
        //         同时自身不能为左结点，要为右结点。
        //         之所以要有右结点，因为没有右结点会一直遍历下去，直到找到有右结点的父结点，
        // 右结点：它的父结点就是它的前驱结点，这是因为中序遍历先左后根再右的特点
        //         同样是因为中序遍历先左后根再右的特点，根结点就是它的前驱结点。
        BSTNode<T> parent = node.parent;
        while (Objects.nonNull(parent) && node == parent.leftChildren) {
            node = parent;
            parent = node.parent;
        }
        return parent;
    }

    public BSTNode<T> findMiddleFrontNode(T key) {
        BSTNode<T> node = recursiveSearch(key);
        if (Objects.isNull(node)) {
            return null;
        }
        return findMiddleFrontNode(node);
    }

    /**
     * 一个结点的后继结点是右子树的最小值，若无右子树，其后继结点在从根结点到key的路径上，比key大的最小值。
     * 这个后继结点跟前驱结点相反就好了，右子树不为空取右子树最小的；
     * 如果右子树为空，那么还是分两种情况：
     * （1）为左结点那么父结点就是后继结点；
     * （2）为右结点则需要找到上一个有左结点的最低父结点，之所以是左结点，是因为先左后中再右，
     * 右结点的下一个就是上一层的中结点，之所以是要有左结点，则是因为要找到真正的上一层
     */
    private BSTNode<T> findMiddleBackNode(BSTNode<T> node) {
        if (Objects.nonNull(node.rightChildren)) {
            return findMin(node.rightChildren);
        }

        BSTNode<T> parent = node.parent;
        while (Objects.nonNull(parent) && node == parent.rightChildren) {
            node = parent;
            parent = node.parent;
        }
        return parent;
    }

    public BSTNode<T> findMiddleBackNode(T key) {
        BSTNode<T> node = recursiveSearch(key);
        if (Objects.isNull(node)) {
            return null;
        }
        return findMiddleBackNode(node);
    }


    /**
     * 插入的逻辑是根据二叉搜索树左结点比中结点小，右结点比左结点大的特点
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
                // 不允许相同结点
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
     * 删除结点主要是将断开的结点连接上，可以使用后继结点来完成查找
     * 1、没有左右子结点，可以直接删除
     * 2、存在左结点或者右结点，删除后需要对子结点移动
     * 3、同时存在左右子结点，不能简单的删除，但是可以通过和后继结点交换后转换为前两种情况
     * 特殊：删除根部结点
     */
    private BSTNode<T> remove(BSTree<T> tree, BSTNode<T> removeNode) {
        BSTNode<T> childNode;
        BSTNode<T> deleteNode;

        // 1. 首先处理找到要删除的结点
        if (Objects.isNull(removeNode.leftChildren) || Objects.isNull(removeNode.rightChildren)) {
            // 如果只存在左或右子结点，或不存在子结点，那么直接删除原结点
            deleteNode = removeNode;
        } else {
            // 如果同时存在左右结点，那么就需要找到后缀结点来替代原结点，然后删除的结点就变成了后继结点
            // 就又变成了上面两种情况（左右结点不同时存在）
            deleteNode = findMiddleBackNode(removeNode);
        }

        // 2. 处理左右子结点，子结点只会有一个存在或不存在的情况，所以直接赋值判断存在与否，存在就建立被删结点父结点和子结点的关系
        if (Objects.nonNull(deleteNode.leftChildren)) {
            childNode = deleteNode.leftChildren;
        } else {
            childNode = deleteNode.rightChildren;
        }

        if (Objects.nonNull(childNode)) {
            // 建立子结点和父结点的关系
            childNode.parent = deleteNode.parent;
        }

        // 3. 最后处理删除结点，建立被删父结点和子结点的关系
        if (Objects.isNull(deleteNode.parent)) {
            // 如果被删除结点的父结点为空，说明删除的是根结点，那么把树的根结点赋值为子结点就好了。
            tree.mRoot = childNode;
        } else if (deleteNode == deleteNode.parent.leftChildren) {
            deleteNode.parent.leftChildren = childNode;
        } else {
            deleteNode.parent.rightChildren = childNode;
        }

        if (deleteNode != removeNode) {
            // 被删的结点就变成了后继结点，将后继结点的值赋给删除的
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
     * @param node 结点实体
     * @param key  父结点的值
     * @param type 该结点的类型，0表示为根结点，1代表右孩子，-1代表左孩子
     */
    public void print(BSTNode<T> node, T key, int type) {
        if (Objects.nonNull(node)) {
            if (type == 0) {
                System.out.println(String.format("%2d 是根结点", node.key));
            } else {
                System.out.println(String.format("%2d 是值为 %2d 的%s结点", node.key, key, type == 1 ? "right" : "left"));
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
     * 获取结点数量
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
     * 获取满二叉树指定层数的结点数量
     *
     * @param level 层数，从1开始
     * @return
     */
    public int getFullTreeTargetLevelNodeNum(int level) {
        if (level <= 0) {
            return 0;
        }
        return (int) Math.pow(2, (level - 1));
    }

    /**
     * 获取满二叉树到指定层数所有结点数量
     *
     * @return
     */
    public int getFullTreeTargetLevelAllNodeNum(int level) {
        if (level <= 0) {
            return 0;
        }
        return (int) Math.pow(2, level) - 1;
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
     * @param mRoot 树的跟结点
     */
    public void display(BSTNode<T> mRoot) {
        // 1. 获取树高
        int height = getTreeHeight();

        // 2. 获取满二叉树所有结点数量
        int allLevelNum = getFullTreeTargetLevelAllNodeNum(height);

        // 3. 遍历展示每一层的结点
        for (int level = 1; level <= height; level++) {
            displayLevel(mRoot, level, height, allLevelNum);
        }
    }

    public void displayLevel(BSTNode<T> mRoot, int level, int height, int allLevelNum) {
        StringBuilder levelShow = new StringBuilder();

        // 1. 获取指定层数的结点数量
        int nodeNum = getFullTreeTargetLevelNodeNum(level);

        // 2. 确定间隔空格
        int intervalPos = allLevelNum / (nodeNum + 1);
        StringBuilder intervalStr = new StringBuilder();
        StringBuilder intervalStrLine = new StringBuilder();
        for (int i = 0; i < intervalPos; i++) {
            intervalStr.append(" ");
            intervalStrLine.append("ˉ");
        }

        // 3. 获取指定层数的数据
        List<String> levelData = getTargetLevelData(mRoot, level);

        int realPos = 0;
        while (levelData.size() > 0 && realPos < levelData.size()) {
            if (realPos > 0 && realPos < levelData.size()) {
                levelShow.append(intervalStrLine);
            } else {
                levelShow.append(intervalStr);
            }
            levelShow.append(levelData.get(realPos));
            realPos++;
        }

        // 4. 打印树形结构
        System.out.println(levelShow.toString());
    }

    public List<String> getTargetLevelData(BSTNode<T> mRoot, int level) {
        // 1. 初始化数据
        List<String> levelData = Lists.newArrayList();
        BSTNode<T> temp;
        BSTNode<T> last = mRoot;
        BSTNode<T> nextLast = mRoot;
        int nowLevel = 1;

        // 2. 使用队列来完成
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.offer(mRoot);
        while (!queue.isEmpty() && nowLevel <= level) {
            temp = queue.poll();
            if (nowLevel == level) {
                levelData.add(String.valueOf(temp.key));
            }

            if (Objects.nonNull(temp.leftChildren)) {
                queue.offer(temp.leftChildren);
            } else {
                BSTNode defaultNode = getDefaultNode(temp);
                queue.offer(defaultNode);
            }

            if (Objects.nonNull(temp.rightChildren)) {
                queue.offer(temp.rightChildren);
                nextLast = temp.rightChildren;
            } else {
                BSTNode defaultNode = getDefaultNode(temp);
                queue.offer(defaultNode);
                nextLast = defaultNode;
            }

            if (temp.equals(last)) {
                nowLevel++;
                last = nextLast;
            }
        }

        return levelData;
    }

    private BSTNode getDefaultNode(BSTNode parent) {
        BSTNode defaultNode = new BSTNode("口", parent);
        BSTNode leftDefaultNode = new BSTNode("口", parent);
        BSTNode rightDefaultNode = new BSTNode("口", parent);
        defaultNode.leftChildren = leftDefaultNode;
        defaultNode.rightChildren = rightDefaultNode;
        return defaultNode;
    }

    public void display() {
        if (Objects.nonNull(mRoot)) {
            display(mRoot);
        }
    }
}
