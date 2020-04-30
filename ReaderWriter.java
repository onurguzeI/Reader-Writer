//package sync;

//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

    public class ReaderWriter{
      
// User change number of readers and writers below 
// Initial start is with 3 readers and 3 writers 

      public static final int readers = 3;
      public static final int writers = 3;
   
       public static void main(String args[]){
         RWLock database = new Database();
      
         Thread[] readerArray = new Thread[readers];
         Thread[] writerArray = new Thread[writers];
      
         for (int i = 0; i < readers; i++) {
            readerArray[i] = new Thread(new Reader(i, database));
            readerArray[i].start();
         }
      
         for (int i = 0; i < writers; i++) {
            writerArray[i] = new Thread(new Writer(i, database));
            writerArray[i].start();
         }
      }
   }



    interface RWLock{
       public abstract void ReadLock(int readerNum);
       public abstract void WriteLock(int writerNum);
       public abstract void ReadUnLock(int readerNum);
       public abstract void WriteUnLock(int writerNum);
   }



    class Database implements RWLock{
      private int readerCount;  
      private Semaphore mutex;  
      private Semaphore db;     
       public Database() {

         readerCount = 0;
         mutex = new Semaphore(1);
         db = new Semaphore(1);
      }
   
       public void ReadLock(int readerNum) {
         try{
          
            mutex.acquire();
         }
             catch (InterruptedException e) {}
      
         ++readerCount;
      
      
         if (readerCount == 1){
            try{
               db.acquire();
            }
                catch (InterruptedException e) {}
         }
      
         System.out.println("Reader " + readerNum + " has finished reading. Reader count: " + readerCount);
         
         mutex.release();
      }
   
       public void ReadUnLock(int readerNum) {
         try{
         
            mutex.acquire();
         }
             catch (InterruptedException e) {}
      
         --readerCount;
      
         if (readerCount == 0){
            db.release();
         }
      
         System.out.println("Reader " + readerNum + " has finished reading. Reader count: " + readerCount);
    
         mutex.release();
      }
   
       public void WriteLock(int writerNum) {
         try{
            db.acquire();
         }
             catch (InterruptedException e) {}
         System.out.println("Writer " + writerNum + " is writing.");
      }
   
       public void WriteUnLock(int writerNum) {
         System.out.println("Writer " + writerNum + " has finished writing.");
         db.release();
      }
   
   
   }




    class Writer implements Runnable
   {
      private RWLock database;
      private int writerNum;
   
       public Writer(int w, RWLock d) {
         writerNum = w;
         database = d;
      }
   
       public void run() {
         while (true){
            Sleep.nap();
         
            System.out.println("Writer " + writerNum + " wants to write.");
            database.WriteLock(writerNum);
         
            Sleep.nap();
         
            database.WriteUnLock(writerNum);
         }
      }
   
   
   }

 
 class Reader implements Runnable
   {
   
      private RWLock database;
      private int readerNum;
   
       public Reader(int readerNum, RWLock database) {
         this.readerNum = readerNum;
         this.database = database;
      }
   
       public void run() {
         while (true) {
            Sleep.nap();
         
            System.out.println("Reader " + readerNum + " wants to read.");
            database.ReadLock(readerNum);
         
      
            Sleep.nap();
         
            database.ReadUnLock(readerNum);
         }
      }
   ;
   }


    class Sleep
   {
   
       public static void nap() {
         nap(NAP_TIME);
      }
   
   
       public static void nap(int duration) {
         int sleeptime = (int) (NAP_TIME * Math.random() );
         try { Thread.sleep(sleeptime*1000); }
             catch (InterruptedException e) {}
      }
   
      private static final int NAP_TIME = 5;
   }


