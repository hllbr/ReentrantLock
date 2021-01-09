
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

//concurrent = eş zamanlılık kavramı bulunan classlar bu yapı içersinde toplanmış durumda 
public class ReentrantLockOrnek {
    
    private int say = 0;//10000 thread1 ve 10000 thread2 arttıracak toplamda 20000 tane sayma olacak.
    private Lock lock = new ReentrantLock();
    private Condition con = lock.newCondition();//Condition abstract olduğu için bu şekilde oluşturuyoruz.lock üzerinde notify ve wait kullankmak için condition yapımızı locka bağladık
    //reentrantlock javada lock interfacei implemente eden bir class
    
    /*
    biz bu yapı içindede(reentrantlock) wait ve notify kullanabiliriz burada condition classtan bir condition oluşturarak kullanabiliriz.
    */
    public void arttır(){
    //synchronized ile yazmadğımız için her threadimiz buraya rahatlıkla girebilecek
    for(int i = 0;i<10000;i++){
        say++;
    }
    }//Thread1 ve thread2 fonksiyonlarımız bu arttır metodunu aynı anda kullanacaklar.sonuç starndart olmadığı için biz buraya(THREAD1 VE THREAD2 İÇİERİSNE) synchronized kod bloku yazarak çözebilirz fakat biz burada reentrantLock kullanarak sorunu çözmek istiyoruz.
    /*
    lock anahtarı bir thread kaptığında diğeri giremesin isteiğim yapı bu bunu lock olarak tanımladığım yapı ile gerçekleştirmeye çalışıyor olacağım.
    */
    public void thread1Fonksiyonu(){
        lock.lock();
        System.out.println("T1 çalışıyor");
        System.out.println("T1 uyandırılmayı bekliyor");
          try{
        con.await();//biri uyandırana kaar burada bekliyoruz
              System.out.println("T1 uyandırıldı işlemine devam ediyor");
        arttır();
         }catch (InterruptedException ex) {
            Logger.getLogger(ReentrantLockOrnek.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            lock.unlock();
        }//anahtarı teslim ediyor ve başka threadlerin girmesine müsade veriyor.
    }
    public void thread2Fonksiyonu(){
        try {
            Thread.sleep(1000);
        }catch (InterruptedException ex) {
            Logger.getLogger(ReentrantLockOrnek.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scanner scn = new Scanner(System.in);
        
        lock.lock();
        System.out.println("T2 çalışıyor.");
        System.out.println("Devam etmek için bir tuşa basınız");
        scn.nextLine();
        con.signal();
        System.out.println("T1 uyandırıldı.Ben Gidiyore");
        try{
        arttır();
        }finally{
            lock.unlock();
        }
   
        
        /*
        eğer lock ile gidriğimiz işlem bir exception fırlatma potansiyeline sahipse anahtar değişmi asla gerçekleşmeyebilir.
        bu yüzden yapıyı güvenli hale getirmek için try-finally yapısı içinde kullanmamız güvenlik sorununu aşmamız için yeterli olacaktır.
        */
    }
      
    public void threadOver(){
        System.out.println("say değişkenimizin değeri : "+say);
    }
}
