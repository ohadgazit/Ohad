
public class WAVLTreeTester {
	public static void main (String [] args) {
		WAVLTree myTree = new WAVLTree();


		
		String str = "aaa";
		myTree.insert(1, "hola");
		myTree.display(true);
		myTree.insert(2, "hola");
		myTree.display(true);
		myTree.insert(3, "hola");
		myTree.display(true);
		System.out.println(myTree.root.subTreeSize);
		myTree.insert(4, "hola");
		myTree.display(true);
		System.out.println(myTree.root.subTreeSize);
		myTree.insert(5, "hola");
		myTree.display(true);
		System.out.println(myTree.root.subTreeSize);
		myTree.insert(6, "hola");
		myTree.display(true);
		System.out.println(myTree.root.subTreeSize);
		myTree.insert(7, "hola");
		myTree.display(true);
		System.out.println(myTree.root.subTreeSize);
		
		
	}

}
