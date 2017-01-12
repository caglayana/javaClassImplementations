/*
 * To change this license firster, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkedlist;

import java.util.ArrayList;

public class LinkedList extends LNode implements HW2Interface  {

    public LNode first;
   
    
    public LinkedList() {
        first = new LNode();        // First node'unu oluşturup listeyi construct ediyoruz
        first = null;
    }
    
    @Override
    public void Insert(int newElement, int pos) throws Exception{
        if (pos < 0)
            throw LinkedListException();
        LNode Dummy;
        Dummy = first;
        if (pos == 0) // Add to the first location
        {
            LNode NewNode = new LNode();
            NewNode.Element = newElement;
            NewNode.link = first;
            first = NewNode;
        }
        else
        {
            for (int i = 0; i < pos-1; i++)
            {
                  Dummy = Dummy.link;
                  if (pos > 0 && Dummy == null)
                      throw LinkedListException();
            }
            LNode NewNode = new LNode();
            NewNode.Element = newElement;
            NewNode.link = Dummy.link;
            Dummy.link = NewNode;
        }
    }
    @Override
    public int Delete(int pos) throws Exception {
        int DeletedNodeElement;
        if (pos < 1){
            throw LinkedListException();
        }
        LNode Dummy; 
        if (pos == 1 && first != null){    // The First Element
            DeletedNodeElement = first.Element;
            first = first.link;  
        }
        Dummy = first;
        for (int i = 0; i < pos-2; i++)
        {
              Dummy = Dummy.link;
              if (pos > 0 && Dummy == null)
                  throw LinkedListException();
        }
        DeletedNodeElement = Dummy.link.Element;
        Dummy.link = Dummy.link.link;
        return DeletedNodeElement;
    }
  
 

    @Override
    public void Output(){
        LNode Dummy;
        Dummy = first;
        System.out.print("The Elements in the list are : ");
        while (Dummy != null){
            System.out.print(Dummy.Element + " " );
            Dummy = Dummy.link;
        }
        System.out.println("");
        
    }
    /* 
        ReverseLink metodu, linked list'i aynalar.
    */
    @Override
    public void ReverseLink() {
        LNode prev = null; 
        LNode curr = first;
        while(curr != null) {
            LNode next = curr.link;     // Her seferinde yeni node yaratmanın sebebi eskiyi tutmadan listeye koymak. 
            curr.link = prev;           // Bulunduğumuz node'un ilerisi olarak gerisi gösterilir.
            prev = curr;                // Bulunduğumuz node'un gerisi olarak bulunduğumuz node gösterilir.
            curr = next;                // Bulunduğumuz node olarak da bir sonraki node gösterilerek üçlü aynalama yapılmış olur.
        }
        first = prev;
    }
    /*
        SquashL metodu, listedeki bir elemandan kaçar tane sıralı olarak bulunduğunu bulur.
        Burada liste değiştirilmek yerine, geçici bir liste yaratılarak önce ona ekleme yapılıp,
        daha sonra ise oluşturulan geçici listenin ilk node'u, var olan listenin ilk node'u
        olarak gösterilir. Böylelikle ekstra kod yazmak yerine, OOP'nin biz sağladığı olanakları
        kullanarak, yazmış ve almış olduğumuz ReverseLink ve Insert metodlarını direkt kullanarak
        bunu sağladık.
    */
    @Override
    public void SquashL() {
        LNode temp;
        LinkedList tempList = new LinkedList();     // Geçici bir liste oluşturduk
        
        temp = this.first;
        int count = 1;
        while(temp.link != null) {
            count = 1;                                                      // Count değişkeni 1 olarak başlar.
            int tempElem = temp.Element;
            while(temp != null && temp.Element == temp.link.Element){       // Bulunulan node bir sonrakine eşit olduğu ve bir sonraki
                count++;                                                    // node null olmadığı sürece count değişkenini arttırıyoruz.
                tempElem = temp.Element;                                    // Buradaki sıkıntı, listenin son elemanının tekli olması zorunluluğu.
                temp = temp.link;                                           // Şartlar bozulana kadar listenin içinde ilerliyoruz.
            }
            try{
            tempList.Insert(tempElem, 0);         // Hangi elemanı sayıyorsak, onu listeye koyuyoruz. 
            tempList.Insert(count, 0);            // Sayılan elemanın miktarını listeye koyuyoruz.
            }                                     // Listedeki sıranın tersi yönünde koyuyoruz. Çünkü elimizdeki
            catch(Exception e){                   // Insert metodunda yapabileceğimiz en kolay şey başa eleman eklemek.
                LinkedListException();            // Elemanları ters yönde başa ekleyip, listeyi aynalarsak, istediğimiz
            }                                     // sonuca ekstra kod yazmadan ulaşmış oluruz.
            temp = temp.link;   // Listenin içinde ilerliyoruz.
        }
        try {
            tempList.Insert(temp.Element, 0);     // En son elemanı yukarıdaki döngüde kontrol edemiyoruz.
            tempList.Insert(count, 0);            // Kontrol şartlarımızda bir sonrakinin null olmama durumu var,
            }                                     // bu yüzden o elemanı okuma şansı olmadan döngüden çıkıyor.
        catch(Exception e){                       // Bundan dolayı son elemanı ve miktarını en sonda ekliyoruz
                LinkedListException();
            }
        tempList.ReverseLink();                   // Geçici listeyi ters çeviriyoruz.
        this.first = tempList.first;              // Var olan listemizi, geçici listemize eşitliyoruz.
        }
    /*
       OplashL metodu, listedeki elemanları ikili olarak işler; sıradaki ilk node yerleştirilecek elemanı,
       ikinci node ise yerleştirilme miktarını belirtir. Burada da SquashL metodundaki gibi geçici bir
       liste oluşturup var olan metodları kullandık.
    */
    @Override
    public void OplashL() {
        int tempElem;
        int tempCount;
        LinkedList tempList = new LinkedList();
        
        LNode temp;
        temp = this.first;
        
        
        while(temp != null && temp.link != null) {      // Döngünün çift kontrol yapmasının sebebi ilerleme miktarıdır. Döngünün sonunda açıklandı
            tempElem = temp.Element;                    // Sıradaki ilk node yerleştirilecek elemanı gösteriyor.
            tempCount = temp.link.Element;              // Sıradaki ikinci node yerleştirilme miktarını gösteriyor.
            for(int i = 0; i < tempCount; i++) {        // Yerleştirilme miktarı kadar döngü döndürüyor ve 
                try {
                    tempList.Insert(tempElem, 0);       // yerleştirilecek elemanı ekliyoruz.
                } catch (Exception e) {
                    LinkedListException();
                }
            }
            temp = temp.link.link;                      // Kod ikili çalıştığı için, listede iki adım ilerliyoruz.
        }
        tempList.ReverseLink();
        this.first = tempList.first;
    }

    @Override
    public Exception LinkedListException() {
        Exception e = new Exception();
        e.getMessage();
        e.toString();
        return null;
    }
    /*
        toString metodu, Linked List'i String'e önce eleman eleman ArrayList'e atayıp,
        daha sonra var olan ArrayList toString metoduyla String'e dönüştürür. Yaşasın OOP =)
    */
    @Override
    public String toString() {
        String strList;
        ArrayList intList = new ArrayList();
        LNode temp;
        int tempInt;
        temp = this.first;
        
        while(temp != null) {
            tempInt = temp.Element;
            intList.add(tempInt);
            temp = temp.link;
        }
        strList = intList.toString();
        System.out.println("The list is: " + strList);
        return strList;
    }
    public static void main(String[] args) throws Exception {
        LinkedList myList = new LinkedList();
        
        try {
            myList.Insert(3, 0);
            myList.Insert(4, 0);
            myList.Insert(1, 0);
            myList.Insert(1, 0);
            myList.Insert(1, 0);
            myList.Insert(1, 0);
            myList.Insert(1, 0);
            myList.Insert(1, 0);
            myList.Insert(1, 0);
            myList.Insert(1, 0);
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        myList.SquashL();
        myList.Output();
        myList.OplashL();
        myList.Output();
        myList.toString();
        
    } 
}
    