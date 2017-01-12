package linkedlist;

public interface HW2Interface {
    public void Insert(int newElement, int pos) throws Exception;
    public int Delete(int pos) throws Exception;
    public void ReverseLink();
    public void SquashL();
    public void OplashL();
    public void Output();
    @Override
    public String toString();   
    public Exception LinkedListException();
}
