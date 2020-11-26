// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).

    public int Allocate(int blockSize) {
        if (blockSize<=0) {
            return -1;
        }
        Dictionary d = freeBlk.Find(blockSize,false);
        if (d==null) {
            return -1;
        }
        System.out.print(d.address);
        if (d.size>blockSize) {
            freeBlk.Insert(d.address+blockSize,d.size-blockSize,d.size-blockSize);
        }
        System.out.print(freeBlk.Delete(d));
        allocBlk.Insert(d.address,blockSize,d.address);
        return d.address;
    } 
    
    public int Free(int startAddr) {
        Dictionary d = allocBlk.Find(startAddr,true);
        if (d==null) {
            return -1;
        }
        allocBlk.Delete(d);
        freeBlk.Insert(d.address,d.size,d.size);
        return 0;
    }
}