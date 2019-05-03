package Encoding;

import java.util.ArrayList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/*
 * Class Huffman permettant la compression d'un texte
 * le résultat sera retourné dans le fichier encoded-result.txt
 */
public class Huffman {

	static Node node;
	static Node newRoot;
	static String binaryString = "";
	static String encodedBinary = "";

	public static void main(String[] args) {
		
		// on utilise la methode reader pour extraire les elements du text sous forme
		// d'un String
		String texte = FileRead.reader("C:\\Users\\chiheb\\Desktop\\test1.txt");
		// On convertit les elements en liste

		char[] txtChar = texte.toCharArray();
		ArrayList<Character> characters = new ArrayList<Character>();

		/*
		 * On sort une liste de toutes les lettres présentes en enlevent les doublants
		 */
		for (int i = 0; i < txtChar.length; i++) {
			if (!(characters.contains(txtChar[i]))) {
				characters.add(txtChar[i]);
			}
		}

		/* on print les lettres présentes dans le texte */
		// System.out.println(characters);

		/* On calcule la fréqeunce d'apparition des lettres */
		int[] countOfChar = new int[characters.size()];

		/* Fill The Array Of Counts with one as base value */
		for (int x = 0; x < countOfChar.length; x++) {
			countOfChar[x] = 0;
		}
		for (int i = 0; i < characters.size(); i++) {
			char checker = characters.get(i);
			for (int x = 0; x < txtChar.length; x++) {
				if (checker == txtChar[x]) {
					countOfChar[i]++;
				}
			}
		}

		/* Tri des elements de manière croissante */
		for (int i = 0; i < countOfChar.length - 1; i++) {
			for (int j = 0; j < countOfChar.length - 1; j++) {
				if (countOfChar[j] < countOfChar[j + 1]) {
					int temp = countOfChar[j];
					countOfChar[j] = countOfChar[j + 1];
					countOfChar[j + 1] = temp;

					char tempChar = characters.get(j);
					characters.set(j, characters.get(j + 1));
					characters.set(j + 1, tempChar);
				}
			}
		}
		/* On print les fréquances */
		for (int x = 0; x < countOfChar.length; x++) {
			System.out.println(characters.get(x) + " - " + countOfChar[x]);
		}

		/* Création de l'arbre */

		/* On commence par les feuilles de l'arbre */
		Node root = null;
		Node current = null;
		Node end = null;

		for (int i = 0; i < countOfChar.length; i++) {
			Node node = new Node(characters.get(i).toString(), countOfChar[i]);
			if (root == null) {
				root = node;
				end = node;
			} else {
				current = root;
				while (current.linker != null) {
					current = current.linker;
				}
				current.linker = node;
				current.linker.linkerBack = current;
				end = node;
			}
		}
		creArbre(root, end);
		// Calcul des fréquences
		char[] messageArray = texte.toCharArray();
		char checker;

		for (int i = 0; i < messageArray.length; i++) {
			current = node;
			checker = messageArray[i];
			String code = "";
			while (true) {
				if (current.left.valeur.toCharArray()[0] == checker) {
					code += "0";
					break;
				} else {
					code += "1";
					if (current.right != null) {
						if (current.right.valeur.toCharArray()[0] == characters.get(countOfChar.length - 1)) {
							break;
						}
						current = current.right;
					} else {
						break;
					}
				}
			}
			binaryString += code;
		}
		System.out.println(binaryString);
		/*
		 * On transforme le string binaire en norme ASCII
		 * pour le compresser, Pour cela on va faire des ensemble de 8 octets
		 * On vérifie que le String est un multiple sinon on lui rajoute un 0.
		 * Puis on convertis en décimal et de décimal en ASCII
		 */
		int size = binaryString.length();
		while(size%8!=0){
            binaryString+=0;
		}
		 
		for(int index = 0; index < binaryString.length(); index+=8) {
            String temp = binaryString.substring(index, index+8);
            int num = Integer.parseInt(temp,2);
            char letter = (char) num;
            encodedBinary = encodedBinary+letter;
        }
		System.out.println(encodedBinary);		
		/*
		 * Création du fichier txt pour afficher le resultat
		 */
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("./Encoded-result.txt"));

			writer.write(encodedBinary);

			writer.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}				
	}

	/*
	 * Classe permettant la création de l'arbre binaire
	 * prenant en compte une racine et un noeud
	 * 
	 */
	public static void creArbre(Node root, Node end) {
		node = new Node(end.linkerBack.valeur + end.valeur, end.linkerBack.countfreq + end.countfreq);
		node.left = end.linkerBack;
		node.right = end;
		end.linkerBack.linkerBack.linker = node;
		node.linkerBack = end.linkerBack.linkerBack;
		end = node;
		end.linker = null;
		Node current = root; 

		while (current.linker != null) {

			current = current.linker;
		}

		if (root.linker == end) {
			node = new Node(root.valeur + end.valeur, root.countfreq + end.countfreq);
			node.left = root;
			node.right = end;
			node.linker = null;
			node.linkerBack = null;
			newRoot = node;
		} else {
			creArbre(root, end);
		}
	}	
}