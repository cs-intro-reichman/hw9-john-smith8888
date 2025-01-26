/**
 * Represents a managed memory space. The memory space manages a list of allocated 
 * memory blocks, and a list free memory blocks. The methods "malloc" and "free" are 
 * used, respectively, for creating new blocks and recycling existing blocks.
 */
public class MemorySpace {
	
	// A list of the memory blocks that are presently allocated
	private LinkedList allocatedList;

	// A list of memory blocks that are presently free
	private LinkedList freeList;

	/**
	 * Constructs a new managed memory space of a given maximal size.
	 * 
	 * @param maxSize
	 *            the size of the memory space to be managed
	 */
	public MemorySpace(int maxSize) {
		// initiallizes an empty list of allocated blocks.
		allocatedList = new LinkedList();
	    // Initializes a free list containing a single block which represents
	    // the entire memory. The base address of this single initial block is
	    // zero, and its length is the given memory size.
		freeList = new LinkedList();
		freeList.addLast(new MemoryBlock(0, maxSize));
	}

	/**
	 * Allocates a memory block of a requested length (in words). Returns the
	 * base address of the allocated block, or -1 if unable to allocate.
	 * 
	 * This implementation scans the freeList, looking for the first free memory block 
	 * whose length equals at least the given length. If such a block is found, the method 
	 * performs the following operations:
	 * 
	 * (1) A new memory block is constructed. The base address of the new block is set to
	 * the base address of the found free block. The length of the new block is set to the value 
	 * of the method's length parameter.
	 * 
	 * (2) The new memory block is appended to the end of the allocatedList.
	 * 
	 * (3) The base address and the length of the found free block are updated, to reflect the allocation.
	 * For example, suppose that the requested block length is 17, and suppose that the base
	 * address and length of the the found free block are 250 and 20, respectively.
	 * In such a case, the base address and length of of the allocated block
	 * are set to 250 and 17, respectively, and the base address and length
	 * of the found free block are set to 267 and 3, respectively.
	 * 
	 * (4) The new memory block is returned.
	 * 
	 * If the length of the found block is exactly the same as the requested length, 
	 * then the found block is removed from the freeList and appended to the allocatedList.
	 * 
	 * @param length
	 *        the length (in words) of the memory block that has to be allocated
	 * @return the base address of the allocated block, or -1 if unable to allocate
	 */
	public int malloc(int length) {		
		ListIterator it = freeList.iterator();
		while (it.hasNext()) {
			if (it.current.block.length>=length) {
				MemoryBlock mb = new MemoryBlock(it.current.block.baseAddress, length);
				allocatedList.addLast(mb);

				if (it.current.block.length==length) {
					freeList.remove(it.current);
				}
				else{
					it.current.block.length = it.current.block.length-length;
					it.current.block.baseAddress = it.current.block.baseAddress+length;
				}
				return mb.baseAddress;
			}
			it.next();
		}
		return -1;
	}

	/**
	 * Frees the memory block whose base address equals the given address.
	 * This implementation deletes the block whose base address equals the given 
	 * address from the allocatedList, and adds it at the end of the free list. 
	 * 
	 * @param baseAddress
	 *            the starting address of the block to freeList
	 */
	public void free(int address) {
		if (allocatedList.getSize()==0) {
			allocatedList.remove(-1);
		}
		ListIterator it = allocatedList.iterator();
		while (it.hasNext()) {
			if (it.current.block.baseAddress==address) {
				MemoryBlock mb = it.current.block;
				allocatedList.remove(it.current);
				freeList.addLast(mb);

				return;
			}

			it.next();
		}
		
	}
	
	/**
	 * A textual representation of the free list and the allocated list of this memory space, 
	 * for debugging purposes.
	 */
	public String toString() {
		return freeList.toString() + "\n" + allocatedList.toString();		
	}
	
	/**
	 * Performs defragmantation of this memory space.
	 * Normally, called by malloc, when it fails to find a memory block of the requested size.
	 * In this implementation Malloc does not call defrag.
	 */
	public void defrag() {
		freeList = freeList.sortList();
		
		Node current = freeList.getFirst();
		
		while (current!=null && current.next!=null) {
			MemoryBlock currBlock = current.block;
			MemoryBlock next =  current.next.block;
			if (currBlock.baseAddress+currBlock.length == next.baseAddress) {
				currBlock.length+=next.length;
				freeList.remove(current.next);
			}
			else {current = current.next;}
		}
		


			
		// LinkedList blocks = new LinkedList();
		// while (it1.hasNext()) {
		// 	ListIterator it2 = freeList.iterator();
		// 	while (it2.hasNext()) {
		// 		if (it1.current.block.baseAddress + it1.current.block.length ==
		// 		it2.current.block.baseAddress) {
		// 			blocks.addFirst(it1.current.block);
		// 			// MemoryBlock newMemoryBlock = new MemoryBlock(it1.current.block.baseAddress, 
		// 			// it1.current.block.length + it2.current.block.length);
		// 			// it1.current.block = newMemoryBlock;
		// 			// freeList.remove(it2.current.block);
		// 		}
		// 		it2.next();
		// 	}
		// 	it1.next();
		// }
	// 	ListIterator it3 = blocks.iterator();
	// 	boolean merge = true;
	// 	while (it3.hasNext()&&merge) {
	// 		merge = blockMerge(it3.current.block, it3.next());
	// 	}
	// }
	// public boolean blockMerge(MemoryBlock block1, MemoryBlock block2) {
	// 	MemoryBlock newMemoryBlock = new MemoryBlock(block1.baseAddress, 
	// 				block1.length + block2.length);
	// 				block1 = newMemoryBlock;
	// 				// freeList.remove(it2.current.block);
	// 	return true;
	}
}
