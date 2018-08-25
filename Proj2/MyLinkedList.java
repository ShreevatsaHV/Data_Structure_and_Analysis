package MyLinkedList;

import java.util.Iterator;

/**
 * LinkedList class implements a doubly-linked list.
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType>
{
	/**
	 * Construct an empty LinkedList.
	 */
	public MyLinkedList( )
	{
		doClear( );
	}

	private void clear( )
	{
		doClear( );
		System.out.println("cleared");
	}

	/**
	 * Change the size of this collection to zero.
	 */
	public void doClear( )
	{
		beginMarker = new Node<>( null, null, null );
		endMarker = new Node<>( null, beginMarker, null );
		beginMarker.next = endMarker;

		theSize = 0;
		modCount++;
	}

	/**
	 * Returns the number of items in this collection.
	 * @return the number of items in this collection.
	 */
	public int size( )
	{
		return theSize;
	}

	public boolean isEmpty( )
	{
		return size( ) == 0;
	}

	/**
	 * Adds an item to this collection, at the end.
	 * @param x any object.
	 * @return true.
	 */
	public boolean add( AnyType x )
	{
		add( size( ), x );   
		return true;         
	}

	/**
	 * Adds an item to this collection, at specified position.
	 * Items at or after that position are slid one position higher.
	 * @param x any object.
	 * @param idx position to add at.
	 * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
	 */
	public void add( int idx, AnyType x )
	{
		addBefore( getNode( idx, 0, size( ) ), x );
	}

	/**
	 * Adds an item to this collection, at specified position p.
	 * Items at or after that position are slid one position higher.
	 * @param p Node to add before.
	 * @param x any object.
	 * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
	 */    
	private void addBefore( Node<AnyType> p, AnyType x )
	{
		Node<AnyType> newNode = new Node<>( x, p.prev, p );
		newNode.prev.next = newNode;
		p.prev = newNode;         
		theSize++;
		modCount++;
	}   


	/**
	 * Returns the item at position idx.
	 * @param idx the index to search in.
	 * @throws IndexOutOfBoundsException if index is out of range.
	 */
	public AnyType get( int idx )
	{
		return getNode( idx ).data;
	}

	/**
	 * Changes the item at position idx.
	 * @param idx the index to change.
	 * @param newVal the new value.
	 * @return the old value.
	 * @throws IndexOutOfBoundsException if index is out of range.
	 */
	public AnyType set( int idx, AnyType newVal )
	{
		Node<AnyType> p = getNode( idx );
		AnyType oldVal = p.data;

		p.data = newVal;   
		return oldVal;
	}

	/**
	 * Gets the Node at position idx, which must range from 0 to size( ) - 1.
	 * @param idx index to search at.
	 * @return internal node corresponding to idx.
	 * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
	 */
	private Node<AnyType> getNode( int idx )
	{
		return getNode( idx, 0, size( ) - 1 );
	}

	/**
	 * Gets the Node at position idx, which must range from lower to upper.
	 * @param idx index to search at.
	 * @param lower lowest valid index.
	 * @param upper highest valid index.
	 * @return internal node corresponding to idx.
	 * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
	 */    
	private Node<AnyType> getNode( int idx, int lower, int upper )
	{
		Node<AnyType> p;

		if( idx < lower || idx > upper )
			throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );

		if( idx < size( ) / 2 )
		{
			p = beginMarker.next;
			for( int i = 0; i < idx; i++ )
				p = p.next;            
		}
		else
		{
			p = endMarker;
			for( int i = size( ); i > idx; i-- )
				p = p.prev;
		} 

		return p;
	}

	/**
	 * Removes an item from this collection.
	 * @param idx the index of the object.
	 * @return the item was removed from the collection.
	 */
	public AnyType remove( int idx )
	{
		return remove( getNode( idx ) );
	}

	/**
	 * Removes the object contained in Node p.
	 * @param p the Node containing the object.
	 * @return the item was removed from the collection.
	 */
	private AnyType remove( Node<AnyType> p )
	{
		p.next.prev = p.prev;
		p.prev.next = p.next;
		theSize--;
		modCount++;

		return p.data;
	}

	/**
	 * Returns a String representation of this collection.
	 */
	public String toString( )
	{
		StringBuilder sb = new StringBuilder( "[ " );

		for( AnyType x : this )
			sb.append( x + " " );
		sb.append( "]" );

		return new String( sb );
	}

	/**
	 * Obtains an Iterator object used to traverse the collection.
	 * @return an iterator positioned prior to the first element.
	 */
	public java.util.Iterator<AnyType> iterator( )
	{
		return new LinkedListIterator( );
	}

	/**
	 * This is the implementation of the LinkedListIterator.
	 * It maintains a notion of a current position and of
	 * course the implicit reference to the MyLinkedList.
	 */
	private class LinkedListIterator implements java.util.Iterator<AnyType>
	{
		private Node<AnyType> current = beginMarker.next;
		private int expectedModCount = modCount;
		private boolean okToRemove = false;

		public boolean hasNext( )
		{
			return current != endMarker;
		}

		public AnyType next( )
		{
			if( modCount != expectedModCount )
				throw new java.util.ConcurrentModificationException( );
			if( !hasNext( ) )
				throw new java.util.NoSuchElementException( ); 

			AnyType nextItem = current.data;
			current = current.next;
			okToRemove = true;
			return nextItem;
		}

		public void remove( )
		{
			if( modCount != expectedModCount )
				throw new java.util.ConcurrentModificationException( );
			if( !okToRemove )
				throw new IllegalStateException( );

			MyLinkedList.this.remove( current.prev );
			expectedModCount++;
			okToRemove = false;       
		}
	}

	/**
	 * This is the doubly-linked list node.
	 */
	private static class Node<AnyType>
	{
		public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
		{
			data = d; prev = p; next = n;
		}
// see from here
		
		public AnyType data;
		public Node<AnyType>   prev;
		public Node<AnyType>   next;
	}
	//Part1-a.swap
	public void swap(int idx1,int idx2)
	{
		Node temp;
		if(idx1<theSize && idx2<theSize)
		{	
			
			Node<AnyType> nAtidx1 = getNode(idx1);
			Node<AnyType> nAtidx2 = getNode(idx2);
			
			if(nAtidx1.next!=nAtidx2)
			{
				nAtidx1.prev.next = nAtidx2;
				nAtidx1.next.prev = nAtidx2;
				nAtidx2.prev.next = nAtidx1;
				nAtidx2.next.prev = nAtidx1;
						
				temp = nAtidx1.next;
				nAtidx1.next = nAtidx2.next;
				nAtidx2.next = temp;
				temp = nAtidx1.prev;
				nAtidx1.prev = nAtidx2.prev;
				nAtidx2.prev = temp;
			}
			else
			{
				nAtidx1.next.prev=nAtidx1.prev;
				nAtidx1.next=nAtidx2.next;
				nAtidx2.next=nAtidx1;
				nAtidx1.prev.next=nAtidx2;
				nAtidx2.next.prev = nAtidx1;
				nAtidx1.prev=nAtidx2;
			}
		}
		else
		{
			System.out.println("Invalid index");
		}	
	}
	
	//Part1-b.shift
	public void shift(int position)
	{
		AnyType temp=null;
		if(position==0)
		{
			;
		}
		else if(position<0)
		{
			for (int i = 0; i < theSize; i++) 
			{
				if(i==theSize-1)
				{
					Node<AnyType> FirstNode=getNode(i);
					Node<AnyType> NextNode=getNode(0);
					if(temp==null)
					{
						NextNode.data=FirstNode.data;	
					}
					else
					{
						NextNode.data=temp;
					}
					temp=NextNode.data;
				}
				else
				{
					Node<AnyType> FirstNode=getNode(i);
					Node<AnyType> NextNode=getNode(i+1);
					if(temp==null)
					{
						temp=NextNode.data;
						NextNode.data=FirstNode.data;	
					}
					else
					{
						AnyType temp2=NextNode.data;
						NextNode.data=temp;
						temp=temp2;
					}
				}
			}	
			shift(position+1);
		}
		else
		{
			shift(position-theSize);
		}
	}
	
	//Part1-c.erase
	public void erase(int index,int numberOfElements)
	{
		if(index>=theSize || index+numberOfElements>theSize)
		{
			System.out.println("Invalid input");
		}
		else
		{
			for(int i=index;i<index+numberOfElements;i++)
			{
				remove(index);	
			}
		}
	}
	
	//Part1-d.insertList 
	public void insertList(MyLinkedList<AnyType> newlist,int index)
	{
		if(index<theSize)
		{
			while(newlist.size()!=0)
			{
				add(index,newlist.remove(newlist.size()-1));
			}
		}
		else
		{
			System.out.println("Invalid Index");
		}
	}
	private int theSize;
	private int modCount = 0;
	private Node<AnyType> beginMarker;
	private Node<AnyType> endMarker;
}

//Part1-e.main code
class TestLinkedList
{
	public static void main( String [] args )
	{
		MyLinkedList<Integer> lst = new MyLinkedList<>( );

		for( int i = 0; i < 10; i++ )
			lst.add( i );

		System.out.println("Before swap");
		java.util.Iterator<Integer> itr = lst.iterator( );
		while( itr.hasNext( ) )
		{
			System.out.print( itr.next( ));
		}
		System.out.println();
		lst.swap(2,4);
		System.out.println("After swapping values in 2nd and 4th index positions");
		itr = lst.iterator( );
		while( itr.hasNext( ) )
		{
			System.out.print( itr.next( ));
		}
		System.out.println();  
		System.out.println("Before shifting");
		itr = lst.iterator( );
		while( itr.hasNext( ) )
		{
			System.out.print( itr.next( ));
		}
		System.out.println();
		System.out.println("Shifting -1 time");
		lst.shift(-1);
		itr = lst.iterator( );
		while( itr.hasNext( ) )
		{
			System.out.print( itr.next( ));
		}
		System.out.println();
		System.out.println("Shifting +2 times");
		lst.shift(2);
		itr = lst.iterator( );
		while( itr.hasNext( ) )
		{
			System.out.print( itr.next( ));
		}
		System.out.println();
		System.out.println("Erasing element from 3rd index position to 4th index position");
		lst.erase(3,2);
		itr = lst.iterator( );
		while( itr.hasNext( ) )
		{
			System.out.print( itr.next( ));
		}
		System.out.println();
		System.out.println("Element at index 3 and index 4 are erased");
		
		System.out.println("Contents of List 1");
		itr = lst.iterator( );
		while( itr.hasNext( ) )
		{
			System.out.print( itr.next( ));
		}
		System.out.println();
		MyLinkedList<Integer> newlist = new MyLinkedList<>( );

		for( int i = 0; i < 10; i++ )
			newlist.add( i );
		System.out.println("Contents of NewList");
		itr = newlist.iterator( );
		while( itr.hasNext( ) )
		{
			System.out.print( itr.next( ));
		}
		System.out.println();
		System.out.println("Inserting NewList elements from index 5");
		lst.insertList(newlist, 5);
		itr = lst.iterator( );
		while( itr.hasNext( ) )
		{
			System.out.print( itr.next( ));
		}
		System.out.println();
		itr = newlist.iterator( );
		while( itr.hasNext( ) )
		{
			System.out.print( itr.next( ));
		}
	}
}