// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

	private BSTree left, right;     // Children.
	private BSTree parent;          // Parent pointer.
		
	public BSTree(){  
		super();
		// This acts as a sentinel root node
		// How to identify a sentinel node: A node with parent == null is SENTINEL NODE
		// The actual tree starts from one of the child of the sentinel node!.
		// CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
	}    

	public BSTree(int address, int size, int key){
		super(address, size, key); 
	}

	public BSTree Insert(int address, int size, int key) 
	{
		BSTree x = this.getRoot();
		BSTree newnode = new BSTree(address,size,key);
		if (x==null) {
			this.right=newnode;
			newnode.parent=this;
			return newnode;
		}
		BSTree p = x.parent;
		while(x!=null) {
			p=x;
			if (x.key>key) {
				x=x.left;
			}
			else if (x.key<key) {
				x=x.right;
			}
			else if (x.address>address) {
				x=x.left;
			}
			else {
				x=x.right;
			}
		}
		
		if (p.key>key) {
			p.left = newnode;
		}
		else if (p.key<key) {
			p.right = newnode;
		}
		else if (p.address>address) {
			p.left = newnode;
		}
		else {
			p.right = newnode;
		}
		newnode.parent = p;
		return newnode;
	}

	public boolean Delete(Dictionary e)
	{
		BSTree x = this.getRoot();
		if (x==null) {
			return false;
		}
		BSTree p;
		while(x.key!=e.key) {
			if (x.key>e.key) {
				x=x.left;
			}
			else {
				x=x.right;
			}
		}
		while(x.address!=e.address) {
			if (x.address>e.address) {
				x=x.left;
			}
			else {
				x=x.right;
			}
		}
		if (x==null || x.size!=e.size) {
			return false;
		}

		if (x.left!=null && x.right!=null) {
			p=x.getNext();
			x.address=p.address;
			x.size=p.size;
			x.key=p.key;
			x=p;
			p=x.parent;
			p.left=x.right;
			if (x.right!=null) {
				x.right.parent=p;
			}
			return true;
		}
		else {
			p=x.parent;
			if (p.left==x) {
				if (x.left!=null) {
					p.left=x.left;
					x.left.parent=p;
				}
				else if (x.right!=null) {
					p.left=x.right;
					x.right.parent=p;
				}
				else {
					p.left=null;
				}
			}
			else {
				if (x.left!=null) {
					p.right=x.left;
					x.left.parent=p;
				}
				else if (x.right!=null) {
					p.right=x.right;
					x.right.parent=p;
				}
				else {
					p.right=null;
				}
			}
		}
		return true;
	}
		
	public BSTree Find(int key, boolean exact)
	{ 
		BSTree x = this.getRoot();
		if (x==null) {
			return null;
		}
		BSTree p = x.parent;
		while(x.key!=key) {
			p=x;
			if (x.key>key) {
				x=x.left;
			}
			else {
				x=x.right;
			}
			if (x==null) {
				break;
			}
		}
		if (x==null) {
			if (exact==true) {
				return null;
			}
			if (p.key<key) {
				p=p.getNext();
			}
			return p;
		}
		while (x.left!=null && x.left.key==key) {
			x=x.left;
		}
		return x;
	}

	public BSTree getFirst()
	{ 
		BSTree x = this.getRoot();
		if (x==null) {
			return null;
		}
			
		while (x.left!=null) {
			x=x.left;
		}
		return x;
	}

	public BSTree getNext()
	{ 
		BSTree x = this;
		if (x.right!=null) {
			x=x.right;
			while (x.left!=null) {
				x=x.left;
			}
			return x;
		}
		BSTree p=x.parent;
		while (p!=null && p.left!=x) {
			x=p;
			p=x.parent;
		}
		return p;
	}

	public boolean sanity()
	{ 
		BSTree x = this;
		BSTree j = this;
		if (x.parent==null) {
			if (x.address!=-1 || x.size!=-1 || x.key!=-1 || x.left!=null) {
				return false;
			}
			if (x.right==null) {
				return true;
			}
		}
		while (j!=null && j.parent!=null) {
			if (x.parent.left!=x && x.parent.right!=x) {
				return false;
			}
			x=x.parent;
			j=j.parent.parent;
			if (x==j) {
				return false;
			}
		}
		while (x.parent!=null) {
			if (x.parent.left!=x && x.parent.right!=x) {
				return false;
			}
			x=x.parent;
		}
		if (x.address!=-1 || x.size!=-1 || x.key!=-1 || x.left!=null) {
			return false;
		}
		x=x.right;
		return checkBST(x);
	}

	private BSTree getRoot() {
		BSTree x = this;
		while (x.parent!=null) {
			x=x.parent;
		}
		return x.right;
	}

	private static boolean checkBST(BSTree x) {
		return checkconnection(x) && checksearchproperty(x);
	}

	private static boolean checkconnection(BSTree x) {
		if (x==null) {
			return true;
		}
		if (x.left!=null) {
			if (x.left.parent!=x) {
				return false;
			}
		}
		if (x.right!=null) {
			if (x.right.parent!=x) {
				return false;
			}
		}
		return checkconnection(x.left) && checkconnection(x.right);
	}

	private static boolean checksearchproperty(BSTree x) {
		if (x==null) {
			return true;
		}
		x=x.getFirst();
		BSTree y;
		while (x!=null) {
			y=x.getNext();
			if (y==null) {
				return true;
			}
			if (x.key>y.key) {
				return false;
			}
			if (x.key==y.key && x.address>y.address) {
				return false;
			}
			x=x.getNext();
		}
		return true;

	}

}


 


