package hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HashTable implements HW4_Interface {

    public ArrayList<ArrayList<Hash>> hashTable;
    public ArrayList<String> storage = new ArrayList();
    public int size;
    
    public HashTable() {
    }
    /**
     * FindHash method finds a string's hash key
     * @param mystring is string which its Hash key to be found
     * @return string's hash key
     */
    @Override
    public long FindHash(String mystring) {
        long hashKey = 0;
        char [] stringArray = mystring.toCharArray();   // Hash key'i bulunacak stringi charlara böler
        
        for(int i = 0; i < mystring.length(); i++) {
            hashKey = hashKey * 37 + stringArray[i];    // Hash key algoritması
            hashKey %= 997;                             // Büyük bir asal sayıya göre modulosunu alarak collision ihtimalini azaltıyoruz
        }
        return hashKey;
    }
    /**
     * wordFreq method finds the frequency of a word in a text file.
     * @param storage is file to be read in ArrayList form
     * @param word is the word which the frequency is found
     * @return frequency of word
     */    
    private int wordFreq(ArrayList storage, String word) {
        int frequency = 0;
        
        if (word == null) {
           return 0;
        } 
        
        else {
        for (int i = 0; i < storage.size(); i++)
            if (storage.get(i) == word)                 // Eğer girilen text dosyasında kelimeyi bulursa rastlanma frekansını bir arttırır
                frequency++;
        }
        return frequency;
    }
    /**
     * BuildHash method reads a file and build the hash table.
     * @param filename is file to be read
     * @param size is horizontal size of hashTable. vertical size is always 10 since insertion to table is done by modulo of 10
     */
    @Override
    public void BuildHash(String filename, int size) {
        int check = 0;
        int posX = 0;
        int posY = 0;
 
        this.size = size;
        hashTable = new ArrayList();
        for (int x = 0; x < size; x++) {
            hashTable.add(new ArrayList());
        }
  
        File file = new File(filename);

        Scanner scanFile = null;
        try {
            scanFile = new Scanner(new FileReader(file));
        } catch (FileNotFoundException ex) {
            System.out.println("NO SUCH FILE!");
        }
        
        String word;
        while(scanFile.hasNextLine()) {
            word = scanFile.nextLine().replaceAll("[(){},.;!?<>%]", "");
            storage.add(word);
        }
        
        String[] parse;
        
        for(int y = 0; y < storage.size(); y++) {
            parse = storage.get(y).split(" ");
        
            for(int x = 0; x < parse.length; x++) {
                
                HashPosition pos = new HashPosition(x + 1, y + 1);
                String checkWord = parse[x];
                
                int frequency = wordFreq(storage, checkWord);
                int hashKey = (int) FindHash(checkWord);
                int arraySize = hashTable.get(hashKey % 10).size();
                Hash element = new Hash(hashKey, frequency, x + 1, y + 1);
            
            if(arraySize == 0) {
                hashTable.get(hashKey % 10).add(element);               // ilk durum
                hashTable.get(hashKey % 10).get(0).value++;
            }
            
            else {
                for (int i = 0; i < hashTable.get(hashKey % 10).size(); i++) {
                    Hash temp;
                    temp = (Hash) hashTable.get(hashKey % 10).get(i);
                    if (temp.key == FindHash(checkWord)) {
                        check = 1;
                        posX = hashKey % 10;
                        posY = i;
                        break;
                    }
                    else {
                        check = 2;
                        posX = hashKey % 10;
                        posY = i;
                    }
                }
                
                if(check == 1) {
                    hashTable.get(posX).get(posY).value++;          // eger varsa 1 ekle
                    hashTable.get(posX).get(posY).positionList.add(pos);
                }
                if (check == 2) {
                    hashTable.get(hashKey % 10).add(element);       // yoksa o arrayin sonuna ekle
                    hashTable.get(hashKey % 10).get(hashTable.get(hashKey % 10).size() - 1).value++;
                    hashTable.get(posX).get(posY).positionList.add(pos);
                }
            }
        }
        }
    }
    /**
     * Finds a word's repetition time on text. Prints repeating locations and repeating amount
     * @param   myword is word to be found its repetition time 
     * @return  -1 if not found, any cardinal number representing its repetition time
     */
    @Override
    public int NumofWord(String myword) {
        boolean found = false;
        long key;
        int verticalIndex;
        int horizontalIndex;
        
        key = FindHash(myword);
        verticalIndex = (int) (key % 10);
        
        for(horizontalIndex = 0; horizontalIndex < hashTable.get(verticalIndex).size(); horizontalIndex++) {
            if(hashTable.get(verticalIndex).get(horizontalIndex).keyOf() == key) {
                found = true;
                System.out.println("\"" + myword + "\" is repeated " + hashTable.get(verticalIndex).get(horizontalIndex).valueOf() + " times.");
                for(int m = 0; m < hashTable.get(verticalIndex).get(horizontalIndex).positionList.size() - 1; m++) {
                    System.out.print(hashTable.get(verticalIndex).get(horizontalIndex).positionList.get(m).posY + ". row, ");
                    System.out.println(hashTable.get(verticalIndex).get(horizontalIndex).positionList.get(m).posX + ". word");
                }
                break;   
            }
        }
        
        if(found)
            return hashTable.get(verticalIndex).get(horizontalIndex).valueOf();
        else
            return -1;
    }
    /**
     * Finds a word's repetition time on text. Represents polymorphism.
     * @param   myword is word to be found its repetition time 
     * @param helper is a boolean to check whether method is called within ShowMax method.
     * @return  -1 if not found, any cardinal number representing its repetition time
     */
     public int NumofWord(String myword, boolean helper) {
        boolean found = false;
        long key;
        int verticalIndex;
        int horizontalIndex;
        
        key = FindHash(myword);
        verticalIndex = (int) (key % 10);
        
        for(horizontalIndex = 0; horizontalIndex < hashTable.get(verticalIndex).size(); horizontalIndex++) {
            if(hashTable.get(verticalIndex).get(horizontalIndex).keyOf() == key) {
                found = true;
                break;   
            }
        }
        
        if(found)
            return hashTable.get(verticalIndex).get(horizontalIndex).valueOf();
        else
            return -1;
    }
    /**
     * Decides whether a word exists in the file
     * @param myword is word to be checked
     * @return the hash key of the word if exists, else -1
     */
    @Override
    public int CheckWord(String myword) {
        long key;
        int verticalIndex;
        int horizontalIndex;
        boolean found = false;
        
        key = FindHash(myword);
        verticalIndex = (int) (key % 10);
        
        for(horizontalIndex = 0; horizontalIndex < hashTable.get(verticalIndex).size(); horizontalIndex++) {
            if(hashTable.get(verticalIndex).get(horizontalIndex).keyOf() == key) {
                found = true;
                System.out.println("\"" + myword + "\" is found! Hash key of \"" + myword + "\" is: " + hashTable.get(verticalIndex).get(horizontalIndex).keyOf());
                break;
            }
        }
        
        if(found)
            return hashTable.get(verticalIndex).get(horizontalIndex).keyOf();
        else
            return -1;
    }
    /**
     * Sorts the hash table in an descending way by word's frequency in text.
     * @param Outfile is name of the file which the sorted hash table to be written.
     */
    @Override
    public void Sort(String Outfile) {
    Hash temp;
    for (int i = 0; i < hashTable.size(); i++) {
      for (int j = 0; j < hashTable.get(i).size() - 1; j++) {
        if (hashTable.get(i).get(j).value > hashTable.get(i).get(j + 1).value) /* bir satırda soldan sağa sıralama yapar*/
        {
          temp = hashTable.get(i).get(j);
          hashTable.get(i).set(j, hashTable.get(i).get(j + 1));
          hashTable.get(i).set(j + 1, temp);
        }
      }
    }
    }
    /**
     * Creates a text file and writes the hash table on to it.
     * @param Outputfile is name of the file which the sorted hash table to be written.
     */
    @Override
    public void Display(String Outputfile) {
        try (PrintWriter writer = new PrintWriter(Outputfile, "UTF-8")) {
            for(int i = 0; i < 10; i++) {
                System.out.print(i + " ");
                writer.print(i + " ");
                for(int j = 0; j < hashTable.get(i).size(); j++) {
                    System.out.print(hashTable.get(i).get(j).keyOf() + " ");
                    writer.print(hashTable.get(i).get(j).keyOf() + " ");
                }
                System.out.println("");
                writer.println("");
            }
        }
        catch (IOException e) {
                System.out.println("I/O PROBLEM!");
            }
    }
    /**
     * Finds the maximum repeated word on a text file.
     * @return the max repeated word on text 
     */
    @Override
    public String ShowMax() {
        String maxWord = "";
        String [] parse;
        boolean helper = true;
        
        for(int i = 0;i < storage.size(); i++) {
            parse = storage.get(i).split(" ");
            for (String parse1 : parse) {
                if (NumofWord(maxWord, helper) < NumofWord(parse1, helper)) {
                    maxWord = parse1;
                }
            }
        }
        System.out.println("Max repeated word is: \"" + maxWord + "\". It's repeated " + NumofWord(maxWord, helper) + " times.");
        return maxWord;
    }
}