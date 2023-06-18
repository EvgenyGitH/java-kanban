package manager;

import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

/*    @Override
    public void setTaskManager() {
        taskManager = new InMemoryTaskManager();
    }*/

    @BeforeEach
    public void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }
}