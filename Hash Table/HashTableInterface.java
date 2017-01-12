package hw4;

public interface HW4_Interface {
 long FindHash(String mystring);                // generate an integer value related to the input word.
 
 void BuildHash(String filename, int size);     // Open and read the
                                                // file then create the linked list hash structure with the size given by
                                                // the user. The file which contains a very long text will be parsed and
                                                // during the parsing hash table has to be modified by the words.
 
 int NumofWord(String myword);                  // The number of myword in the text
                                                // file will be given. If there is no myword in the text -1 has to be
                                                // returned.
 
 int CheckWord(String myword);                  // Checks whether myword is found
                                                // in the text. If so returns its position. If not returns -1
 
 void Sort(String Outfile);                    // The words in the text file has
                                                // been sorted in increasing order according to the number of repetition
                                                // into the file. The file has to be given by the user. You can use any
                                                // sort algorithm you want. 
 
 void Display(String Outputfile);               // All the words in the text
                                                // and their frequency has to be displayed in a text file.
 
 String ShowMax (); // The most repeated word has to be
//returned.
}