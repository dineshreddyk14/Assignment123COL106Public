// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public void Defragment() {
        Dictionary x = freeBlk.getFirst();
        DLList listblk = new DLList();
        while (x!=null) {
            listblk.insert(x);
            x=x.getNext();
        }
        listblk.Defrag(freeBlk);
        listblk=null;
    }
}


class DLList {
    Dictionary element;
    private DLList prev;
    private DLList next;

    DLList(Dictionary e, DLList p, DLList n) {
        this.element=e;
        this.prev=p;
        this.next=n;
    }
    DLList(Dictionary e) {
        this(e,null,null);
    }
    DLList() {
        this(null);
        this.next= new DLList(null);
        this.next.prev=this;
    }

    DLList insert(Dictionary e) {
        DLList x = this.next;
        DLList newnode = new DLList(e);
        while (x.element!=null) {
            if (x.element.address<e.address) {
                x=x.next;
            }
            else {
                newnode.next=x;
                newnode.prev=x.prev;
                x.prev=newnode;
                newnode.prev.next=newnode;
                return newnode;
            }
        }
        newnode.next=x;
        newnode.prev=x.prev;
        x.prev=newnode;
        newnode.prev.next=newnode;
        return newnode;
    }

    void Defrag(Dictionary block) {
        DLList current = this.next;
        Dictionary e = current.element;
        if (e==null) {
            return;
        }
        Dictionary ref;
        while (current.next.element!=null) {
            ref = current.next.element;
            if ((e.address+e.size)==ref.address) {
                block.Delete(e);
                block.Delete(ref);
                e=block.Insert(e.address,ref.size+e.size,ref.size+e.size);
                current.next.prev = current.prev;
                current.prev.next = current.next;
                current=current.next;
                current.element=e;
            }
            else {
                current=current.next;
                e=current.element;
            }
        }
    }
}

    
    
