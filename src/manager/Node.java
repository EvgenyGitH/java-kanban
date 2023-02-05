package manager;

/*

public class CustomeLinkedList <T>{
Node<T> head;
Node<T> tail;
int size=0;
}

 */
public class Node<T> {
    public T data;
    public Node<T> next;
    public Node<T> prev;

    public Node(Node<T> prev, T data, Node<T> next) {
        this.data = data;
        this.next = next;
        this.prev = prev;
    }
}
