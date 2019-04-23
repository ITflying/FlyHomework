package com.hiabby.flyinterview.april;

import java.util.Objects;

/**
 * @desc: 字符数组反转和双向链表插入
 * @author: ITflying
 * @create: 2019-04-22 21:21
 **/
public class Twelve {
    public static void main(String[] args) {
        testFactory(2);
    }

    public static void testFactory(int choice) {
        switch (choice) {
            case 1:
                System.out.println("-----------------------------------");
                System.out.println("\t\t 测试字符串反转 \t");
                System.out.println("-----------------------------------");
                char[] s = new char[]{'1', '2', '3', '4', '5', '6'};
                System.out.println(s);
                reverseStr(s);
                System.out.println(s);

                reverseStrByRecursion(s, 0);
                System.out.println(s);
                break;
            case 2:
                System.out.println("-----------------------------------");
                System.out.println("\t\t 测试双向链表 \t");
                System.out.println("-----------------------------------");
                DualLinkedList du  = new DualLinkedList<Integer>();
                du.addAfterTail(3);
                du.addAfterTail(4);
                du.addAfterTail(5);
                du.addBeforeHead(2);
                du.addBeforeHead(1);
                du.addBeforeHead(0);
                System.out.println("第二个数据为："+du.getNodeByPos(1).getVal());
                System.out.println("第五个数据为："+du.getNodeByPos(4).getVal());
                du.display();

                du.deleteByPos(2);
                System.out.println("删除第三个数据后："+du.toString());
                break;
            case 3:
                System.out.println("-----------------------------------");
                System.out.println("\t\t 测试双向整数链表的插入 \t");
                System.out.println("-----------------------------------");
                DualLinkedList duNum  = new DualLinkedList<Integer>();
                duNum.addAfterTail(1);
                duNum.addAfterTail(2);
                duNum.addAfterTail(4);
                duNum.addAfterTail(5);
                duNum.addAfterTail(6);
                System.out.println("之前的数据："+duNum.toString());

                duNum.insertNum(1);
                duNum.insertNum(3);
                duNum.insertNum(7);
                duNum.insertNum(0);
                System.out.println("插入后的数据："+duNum.toString());
                break;
            default:
                System.out.println("您选择了错误的测试项目，请重新选择");
                break;
        }
    }

    /**
     * 1. 字符数组反转，只是用原来的字符数组
     */
    public static void reverseStr(char[] var) {
        int i = 0, j = var.length - 1;
        if (var.length > 1) {
            while (i != j && i <= j) {
                char temp = var[i];
                var[i] = var[j];
                var[j] = temp;
                i++;
                j--;
            }
        }
    }

    static int reversePos = 0;

    public static void reverseStrByRecursion(char[] var, int pos) {
        if (pos == var.length) {
            return;
        }
        reverseStrByRecursion(var, pos + 1);
        if (reversePos < pos) {
            char temp = var[pos];
            var[pos] = var[reversePos];
            var[reversePos++] = temp;
        }
    }
}

/**
 * 2. 双向链表插入，定义结点和链表类，插入
 */
class DualLinkedList<T extends Comparable<T>> {
    class Node<T extends Comparable<T>> {
        private T val;
        private Node left;
        private Node right;

        public Node(T val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public T getVal() {
            return val;
        }
    }

    private Node header;
    // 如果是循环链表，那么尾节点就指向头结点
    private Node tail;
    private int size = 0;

    public DualLinkedList() {
        header = null;
        tail = null;
    }

    public void addAfterTail(T element) {
        if (Objects.isNull(header)) {
            header = new Node(element, null, null);
            tail = header;
        } else {
            Node newNode = new Node(element, tail, null);
            tail.right = newNode;
            tail = newNode;
        }
        size++;
    }

    public void addBeforeHead(T element) {
        if (Objects.isNull(header)) {
            header = new Node(element, null, null);
            tail = header;
        } else {
            Node newNode = new Node(element, null, header);
            header.left = newNode;
            header = newNode;
        }
        size++;
    }

    public Node getNodeByPos(int pos) {
        if (pos < 0 || pos > size - 1) {
            System.out.println("索引位置不符合要求");
        }
        Node findNode = null;
        if (pos < size / 2) {
            findNode = header;
            for (int i = 1; i <= pos; i++) {
                findNode = findNode.right;
            }
        } else {
            findNode = tail;
            for (int i = size - 2; i >= pos; i--) {
                findNode = findNode.left;
            }
        }
        return findNode;
    }

    public void insertByPos(T element, int pos) {
        if (pos < 0 || pos > size) {
            System.out.println("插入的位置不符合要求");
        }
        if (pos == 0) {
            addBeforeHead(element);
        } else if (pos == size) {
            addAfterTail(element);
        } else {
            Node leftNode = getNodeByPos(pos - 1);
            Node rightNode = leftNode.right;
            Node insert = new Node(element, leftNode, rightNode);
            leftNode.right = insert;
            if (!Objects.isNull(rightNode)) {
                rightNode.left = insert;
            }
        }
    }

    public void deleteByPos(int pos){
        if (pos < 0 || pos > size) {
            System.out.println("插入的位置不符合要求");
        }
        Node current = getNodeByPos(pos);
        Node leftNode = current.left;
        Node rightNode = current.right;
        if (!Objects.isNull(leftNode)){
            leftNode.right = current.right;
        }
        if (!Objects.isNull(rightNode)){
            rightNode.left = current.left;
        }
        size--;
    }

    public void display(){
        System.out.println("链表中的数据：");
        Node current = header;
        while (!Objects.isNull(current)){
            System.out.println(current.val);
            current = current.right;
        }
    }

    @Override
    public String toString(){
        if (size <= 0){
            return "[]";
        }
        StringBuilder builder = new StringBuilder("[");
        for (Node current = header; current != null; current = current.right){
            builder.append(current.val + ",");
        }
        String res = builder.toString();
        return res.substring(0, res.length() - 1) + "]";
    }


    /**
     * 在有序的整数双向链表当中插入整数，如果整数存在返回已存在，否则按顺序插入
     */
    public void insertNum(T num) {
        if (size == 0 || header.getVal().compareTo(num) > 0){
            addBeforeHead(num);
        }
        else if (tail.getVal().compareTo(num) < 0){
            addAfterTail(num);
        } else {
            int pos = 0;
            Node current = header;
            while(!Objects.isNull(current)){
                Node next = current.right;
                int compareRes = num.compareTo((T) current.getVal());
                if (compareRes == 0){
                    System.out.println(num + " 已存在");
                }
                else if (compareRes > 0 && (next == null || num.compareTo((T) next.getVal()) < 0)){
                   insertByPos(num, pos+1);
                   return;
                }
                current = current.right;
                pos++;
            }
        }
    }
}