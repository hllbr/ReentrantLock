
import java.util.logging.Level;
import java.util.logging.Logger;

/*
synchronized kod bloklarına alternatif olarak kullanılabilen reentrant bloklar ile işlemlerimi yapmaya çalışıyorum
*/
public class Main {
    public static void main(String[] args) {
        ReentrantLockOrnek rlo = new ReentrantLockOrnek();
        
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
             rlo.thread1Fonksiyonu();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
               rlo.thread2Fonksiyonu();
            }
        });
        
        t1.start();
        t2.start();
        
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        rlo.threadOver();
    }
    
    
}
