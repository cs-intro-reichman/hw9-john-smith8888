/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}
	
	/**
	 * Gets the first node of the list
	 * @return The first node of the list.
	 */		
	public Node getFirst() {
		return this.first;
	}

	/**
	 * Gets the last node of the list
	 * @return The last node of the list.
	 */		
	public Node getLast() {
		return this.last;
	}
	
	/**
	 * Gets the current size of the list
	 * @return The size of the list.
	 */		
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index >= size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		if (index == 0) {
			return first;
		}
		if (index == size-1) {
			return last;
		}

		Node curr = first;
		for(int i=0 ; i<index;i++){
			curr = curr.next;
		}
		return curr;
	}
	
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		if (index == 0) {
			addFirst(block);
			return;
		}
		if (index == size) {
			addLast(block);
			return;
		}

		Node insert = new Node(block);
		Node prev = null;
		Node current = this.first;
		for (int i = 0; i < index; i++) {
		prev = current;
		current = current.next;
		}
		prev.next = insert;
		insert.next = current;
		size++;
		return;

	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		Node tmp = new Node(block);
		if (size==0) {
			first = tmp;
			last = tmp;
		}
		else{
			last.next = tmp;
			last = tmp;
		}
		size++;
		return;
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		Node newNode = new Node(block);
		newNode.next = first;
		first = newNode;
		if (size==0) {
			last = newNode;
		}
		size++;
		return;
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		return getNode(index).block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		if (size == 0) {
			return -1;
		}
		Node curr = first;

		if (curr.block==block) {
			return 0;
		}

		for(int i =1; i<size;i++){
			curr = curr.next;
			if (curr.block==block) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		if (first==node) {
			first = first.next;
			if (first==null) {
				last = null;
			}
			size--;
			return;
		}
		Node curr = first;
		for (int i=0;i<size;i++) {
			if (curr.next==node) {
				if (node==last) {
					last = curr;
					last.next = null;
					size--;
					return;
				}
				curr.next=curr.next.next;
				size--;
				return;
			}
			curr = curr.next;
		}
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		if (index == 0) {
			if (size==1) {
				first = null;
				last = null;
				size--;
			}
			else{
			first = first.next;
			size--;
			}
			return;
		}

		ListIterator it = iterator();
		Node prev = null;

		for(int i =0 ; i <= index ; i++) {
			Node currentNode = it.current;
			it.next();
			if (i==index) {
				if (currentNode == last) {
					last = prev;
				}
					prev.next = currentNode.next;
					
				size--;
				return;
			}
			prev = currentNode;
		}
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		// if (this.indexOf(block)==-1) {
		// 	throw new IllegalArgumentException(
		// 			"block is not in this list");
		// }
		if (block == null || this.indexOf(block)==-1) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		ListIterator it = iterator();
		Node prev = null;
		while (it.hasNext()) {
			Node curentNode = it.current;
			if (it.next().equals(block)) {
				if (prev==null) {
					first = curentNode.next;
					if (curentNode==last) {
						last = null;
					}
				} else{
					prev.next = curentNode.next;
					if (curentNode == last) {
						last = prev;
					}
				}
				size--;
				return;
			}
			prev = curentNode;
		}
	}	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		ListIterator it = iterator();
		StringBuilder sb = new StringBuilder();
		
		while (it.hasNext()) {
			sb.append(it.next()+" ");
		}
		return sb.toString();
	}

	public LinkedList sortList(){
		if (size==0) {
			return new LinkedList();
		}
		else if (size==1) {
			return this;
		}
		LinkedList sorted = new LinkedList();

		Node cur = this.first;

		while (cur!=null) {
			sorted.addInOrder(cur.block);
			cur = cur.next;
		}


		return sorted;
	}
	public void addInOrder(MemoryBlock data){
		if (this.first == null || this.first.block.baseAddress > data.baseAddress) {
			this.addFirst(data);
			return;
			}
			Node node = new Node(data);
			Node prev = null;
			Node current = this.first;
			while (current != null && current.block.baseAddress < data.baseAddress) {
			prev = current;
			current = current.next;
			}
			node.next = current;
			prev.next = node;
			this.size++;
	}
}