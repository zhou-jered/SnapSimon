package me.local.problem;

public class ReverseList {
    static class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    void reverse(Node head) {
        Node preNode = head.next;
        Node current = preNode.next;
        preNode.next = null;
        if (current == null) {
            return;
        }
        Node sufNode = current.next;
        if (sufNode == null) {
            current.next = preNode;
            preNode.next = null;
            head.next = current;
            return;
        }


        while (sufNode != null) {
            current.next = preNode;
            preNode = current;
            current = sufNode;
            sufNode = sufNode.next;
        }
        current.next = preNode;
        head.next = current;
    }

    void printList(Node head) {
        Node current = head.next;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }


    public static void main(String[] args) {
        Node head = new Node(-1);
        Node current = head;
        int idx = 1;
        for (int i = 0; i < 10; i++) {
            current.next = new Node(idx++);
            current = current.next;
        }
        ReverseList rl = new ReverseList();
        rl.printList(head);
        rl.reverse(head);
        rl.printList(head);

    }
}
