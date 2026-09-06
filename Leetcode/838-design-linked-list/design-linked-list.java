class MyLinkedList {

    int val;
    MyLinkedList next;

    public static MyLinkedList head;
    public static MyLinkedList tail;
    public static int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public MyLinkedList(int val) {
        this.val = val;
        this.next = null;
    }

    public int get(int index) {

        if (index < 0 || index >= size) {
            return -1;
        }

        MyLinkedList temp = head;

        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }

        return temp.val;
    }

    public void addAtHead(int val) {

        MyLinkedList Node = new MyLinkedList(val);

        if (head == null) {
            head = tail = Node;
        } else {
            Node.next = head;
            head = Node;
        }

        size++;
    }

    public void addAtTail(int val) {

        MyLinkedList Node = new MyLinkedList(val);

        if (head == null) {
            head = tail = Node;
        } else {
            tail.next = Node;
            tail = Node;
        }

        size++;
    }

    public void addAtIndex(int index, int val) {

        if (index < 0 || index > size) {
            return;
        }

        if (index == 0) {
            addAtHead(val);
            return;
        }

        if (index == size) {
            addAtTail(val);
            return;
        }

        MyLinkedList Node = new MyLinkedList(val);
        MyLinkedList temp = head;

        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }

        Node.next = temp.next;
        temp.next = Node;

        size++;
    }

    public void deleteAtIndex(int index) {

        if (index < 0 || index >= size) {
            return;
        }

        if (index == 0) {

            head = head.next;
            size--;

            if (size == 0) {
                tail = null;
            }

            return;
        }

        MyLinkedList temp = head;

        for (int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }

        if (temp.next == tail) {
            tail = temp;
        }

        temp.next = temp.next.next;

        size--;
    }
}