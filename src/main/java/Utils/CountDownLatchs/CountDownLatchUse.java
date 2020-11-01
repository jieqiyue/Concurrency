package Utils.CountDownLatchs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Classname CountDownLatchUse
 * @Description TODO
 * @Date 2020/10/15 16:51
 * @Created by Jieqiyue
 */
public class CountDownLatchUse {

    private  static Random random = new Random(System.currentTimeMillis());

    static class Event{
        int id = 0;
        public Event(int id){
            this.id = id;
        }
    }

    public static void main(String[] args) {
        Event[] events = {new Event(1),new Event(2)};
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (Event event: events){
            List<Table> tables = capture(event);

            for (Table table : tables){
                TaskBatch taskBatch = new TaskBatch(2);
                TrustSourceColumns trustSourceColumns = new TrustSourceColumns(table,taskBatch);
                TrustTargetCount trustTargetCount = new TrustTargetCount(table,taskBatch);

                executorService.submit(trustSourceColumns);
                executorService.submit(trustTargetCount);
            }
        }
    }

    interface Watcher{
        //void startWatch();
        void done(Table table);
    }

    static class TaskBatch implements Watcher{

        private CountDownLatch countDownLatch;

        public TaskBatch(int size){
            this.countDownLatch = new CountDownLatch(size);
        }
        @Override
        public void done(Table table) {
            countDownLatch.countDown();
            if (countDownLatch.getCount() == 0){
                System.out.println("The table " + table.tableName + " finished work,[" + table + "]" );
            }
        }
    }


    static class  Table{
        String tableName;
        long sourceRecordCount = 10;
        long targetCount;
        String sourceColumnSchema = "sourceColumnSchema";
        String targetColumnSchema = "";

        public Table(String tableName, long sourceRecordCount) {
            this.tableName = tableName;
            this.sourceRecordCount = sourceRecordCount;
        }

        @Override
        public String toString() {
            return "Table{" +
                    "tableName='" + tableName + '\'' +
                    ", sourceRecordCount=" + sourceRecordCount +
                    ", targetCount=" + targetCount +
                    ", sourceColumnSchema='" + sourceColumnSchema + '\'' +
                    ", targetColumnSchema='" + targetColumnSchema + '\'' +
                    '}';
        }
    }

    private static List<Table> capture(Event event){
        List<Table> list = new ArrayList<>();
        for (int i = 0;i < 10 ;i++){
            list.add(new Table("Table-" + event.id + "-" + i,i * 1000));
        }
        return list;
    }

    static class TrustSourceColumns implements Runnable{
        private final Table table;
        private TaskBatch taskBatch;

        public TrustSourceColumns(Table table, TaskBatch taskBatch) {
            this.table = table;
            this.taskBatch = taskBatch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            table.targetColumnSchema = table.sourceColumnSchema;
            taskBatch.done(table);
            System.out.println("The table" + table.tableName + " targetColumn capture down and update to db..");
        }
    }

    static class TrustTargetCount implements Runnable{
        private final Table table;
        private TaskBatch taskBatch;
        public TrustTargetCount(Table table,TaskBatch taskBatch) {
            this.table = table;
            this.taskBatch = taskBatch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            table.targetCount = table.sourceRecordCount;
            taskBatch.done(table);
            System.out.println("The table" + table.tableName + " TargetCount capture down and update to db..");
        }
    }

}
