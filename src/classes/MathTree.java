package classes;

import utils.AritmeticosUtils;

public class MathTree{
	
	private Node treeNode;
	
	public class Node {
		
		private Node left;
		private Node right;
		
		private String content;
		
		public Node() {
			
		}
		public Node(String content) {
			this.content = content;
		}
		
		public Node(Node left, Node right, String content) {
			this.left = left;
			this.right = right;
			this.content = content;
		}
		
	}
	public MathTree() {
		
	}
	public MathTree(Node node) {
		this.treeNode = node;
	}
	
	public void insert(String content) {
		
		this.treeNode = insert(this.treeNode, content);

	}
	
	private Node insert(Node root, String content) {
		
		if(root == null) {
			
			return new Node(null, null, content);
			
		}else {
			
			if(root.left != null) {
				if(!AritmeticosUtils.aritmeticosMapping.containsKey(root.left.content)) {
						if(root.right == null)
							root.right = insert(root.right,content);
						
				}else 
					root.left = insert(root.left, content);
			    
			}else {
				root.left = insert(root.left, content);
			}
			
			
		}
		
	
		return root;
	}
	
	public void printall(Node root) {
		if (root != null ) {
			System.out.println(root.content);
			printall(root.left);
			printall(root.right);
		}
	
	}
	
	public Node getRoot() {
		return this.treeNode;
	}
	

	
}
