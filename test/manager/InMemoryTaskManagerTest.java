package manager;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    public void setTaskManager() {
        taskManager = new InMemoryTaskManager();
    }

}