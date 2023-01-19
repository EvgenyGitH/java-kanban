package manager;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private Node<Task> head;
    private Node<Task> tail;
    private int size = 0;
    private Map<Integer, Node> historyMap = new HashMap<>();

    public Node<Task> getHead() {
        return head;
    }

    public void setHead(Node<Task> head) {
        this.head = head;
    }

    public Node<Task> getTail() {
        return tail;
    }

    public void setTail(Node<Task> tail) {
        this.tail = tail;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Node<Task> linkLast(Task task){
        Node<Task> oldTail = getTail(); //tail;
        Node<Task> newNode = new Node<>(oldTail, task, null);
        setTail(newNode); //tail = newNode;
        if (oldTail == null){
            setHead(newNode);//head = newNode;
        } else {
            oldTail.next = newNode;
        }
        historyMap.put(task.getIdTask(),newNode);
        setSize(getSize()+1);
        return newNode;
    }

    @Override
    public void add(Task task){
        if (historyMap.containsKey(task.getIdTask())) {
            remove(task.getIdTask());
            historyMap.remove(task.getIdTask());
        }
        linkLast(task);
    }


    public ArrayList<Task> getTasks(){
        ArrayList<Task> historyTaskData = new ArrayList<>();
        Node<Task> curNote = getHead();//head;
        while (curNote != null){
            historyTaskData.add(curNote.data);
            curNote = curNote.next;
        }
        return historyTaskData;
    }

    @Override
    public  List<Task> getHistory(){
        ArrayList<Task> historyArray = getTasks();

        for (Task taskIdPtint: historyArray){
            System.out.println("+ ID: " + taskIdPtint.getIdTask()+ " taskName: " +  taskIdPtint.getTaskName() + " taskDescription: " + taskIdPtint.getTaskDescription() );
        }
        return historyArray;
    }

    @Override
    public void remove(int id) {
        Node<Task> nodeRemove = historyMap.get(id);
        removeNode(nodeRemove);
    }

    public void removeNode(Node<Task> removeNode) {
        if (removeNode == null) {
            return;
        }
        if (removeNode.next == null) {
            int prevNodeID = removeNode.prev.data.getIdTask();
            removeNode.prev = null;
            removeNode.data = null;
            Node<Task> removeNodePrev = historyMap.get(prevNodeID);
            removeNodePrev.next = null;
            setTail(removeNodePrev);//tail = removeNodePrev;
            setSize(getSize()-1);;
        } else if (removeNode.prev == null) {
            int nextNodeID = removeNode.next.data.getIdTask();
            removeNode.next = null;
            removeNode.data = null;
            Node<Task> removeNodeNext = historyMap.get(nextNodeID);
            removeNodeNext.prev = null;
            setHead(removeNodeNext);//head = removeNodeNext;
            setSize(getSize()-1);;
        } else {
            int prevNodeID = removeNode.prev.data.getIdTask();
            int nextNodeID = removeNode.next.data.getIdTask();
            removeNode.prev = null;
            removeNode.next = null;
            removeNode.data = null;
            Node<Task> removeNodePrev = historyMap.get(prevNodeID);
            Node<Task> removeNodeNext = historyMap.get(nextNodeID);
            removeNodePrev.next = removeNodeNext;
            removeNodeNext.prev = removeNodePrev;
            setSize(getSize()-1);
        }
    }
}












/*

Интерфейс HistoryManager
У нас уже есть интерфейс, осталось добавить метод void remove(int id) для удаления задачи из просмотра.
И реализовать его в классе InMemoryHistoryManager. Добавьте его вызов при удалении задач,
чтобы они также удалялись из истории просмотров.
------------------------------------------------------------------------------------
Где пригодится тип списка просмотренных задач
Для списка просмотренных задач нужен тип task.Task. Метод getHistory() должен возвращать список именно такого типа.
В итоге он будет выглядеть так — List<task.Task> getHistory().

Создайте отдельный интерфейс для управления историей просмотров — manager.HistoryManager. У него будет два метода.
Первый add(task.Task task) должен помечать задачи как просмотренные, а второй getHistory() — возвращать их список.
Объявите класс manager.InMemoryHistoryManager и перенесите в него часть кода для работы с историей
из  класса manager.InMemoryTaskManager.  Новый класс manager.InMemoryHistoryManager должен реализовывать интерфейс manager.HistoryManager.

*/


