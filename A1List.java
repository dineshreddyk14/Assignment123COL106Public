// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

	private A1List  next; // Next Node
	private A1List prev;  // Previous Node 

	public A1List(int address, int size, int key) { 
		super(address, size, key);
	}

	public A1List(){
		super(-1,-1,-1);
		// This acts as a head Sentinel

		A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
		
		this.next = tailSentinel;
		tailSentinel.prev = this;
	}

	public A1List Insert(int address, int size, int key)
	{
		A1List newnode = new A1List(address,size,key);
		if (this.next==null) {
			return null; //can't insert after tail sentinal(it won't come to this if abstraction is maintained)
		}
		newnode.next = this.next;
		newnode.prev = this;
		this.next.prev = newnode;
		this.next = newnode;
		return newnode;
	}

	public boolean Delete(Dictionary d) 
	{
		A1List x = this;
		while (x!=null) {
			if (x.key==d.key) {
				if (x.address==d.address && x.size==d.size) {
					if (x==this) {
						if (x.prev==null) {
							return false;
						}
						x = this.prev;
						this.key = x.key;
						this.address = x.address;
						this.size = x.size;
						if (x.prev!=null) {
							x.prev.next = x.next;
						}
						x.next.prev = x.prev;						
						return true;
					}
					if (x.prev==null || x.next==null) {
						return false;
					}
					x.next.prev = x.prev;
					x.prev.next = x.next;
					return true;
				}
			}
			x = x.prev;
		}
		x = this.next;
		while (x!=null) {
			if (x.key==d.key) {
				if (x.address==d.address && x.size==d.size) {
					if (x.next == null) {
						return false;
					}
					x.next.prev = x.prev;
					x.prev.next = x.next;
					return true;
				}
			}
			x = x.next;
		}
		return false;
	}

	public A1List Find(int k, boolean exact)
	{
		A1List x = this;
		x=x.getFirst();
		if (exact==true) {
			while (x!=null) {
				if (x.key==k && x.next!=null) {
					return x;
				}
				x = x.next;
			}
			return null;
		}
		while (x!=null) {
			if (x.key>=k && x.next!=null) {
				return x;
			}
			x = x.next;
		}
		return null;
	}

	public A1List getFirst()
	{
		A1List x = this;
		while (x.prev!=null) {
			x = x.prev;
		}
		if (x.next.next == null) {
			return null;
		}
		return x.next;
	}
	
	public A1List getNext() 
	{
		A1List x=this;
		if (x.next==null){
			return null; //won't come to this 
		}
		if (x.next.next == null) {
			return null;
		}
		return x.next;
	}

	public boolean sanity()
	{
		A1List x = this;
		A1List j = this;
		while (j!=null && j.prev!=null) {
			if (x.prev.next!=x) {
				return false; // two way connection check
			}
			x=x.prev;
			j=j.prev.prev;
			if (x==j) {
				return false; // loop check
			}
		}
		while (x.prev!=null) {
			if (x.prev.next!=x) {
				return false;
			}
			x=x.prev;
		}
		if (x.key!=-1 || x.size!=-1 || x.address!=-1) {
			return false; // head sentinal check
		}
		x = this;
		j = this;
		while (j!=null && j.next!=null) {
			if (x.next.prev!=x) {
				return false; // two way connection check
			}
			x=x.next;
			j=j.next.next;
			if (x==j) {
				return false; // loop check
			}
		}
		while (x.next!=null) {
			if (x.next.prev!=x) {
				return false;
			}
			x=x.next;
		}
		if (x.key!=-1 || x.size!=-1 || x.address!=-1) {
			return false; // tail sentinal check
		}
		return true;
	}

}


