package Encoding;

/*
 * Constructeur de la classe Node
 */
class Node {

	String valeur;
	int countfreq;
	Node left;
	Node right;
	Node linker;
	Node linkerBack;

	Node(String value, int count) {

		this.valeur = value;
		this.countfreq = count;
		this.left = null;
		this.right = null;
		this.linker = null;
		this.linkerBack = null;
		
		
	}
	
}

