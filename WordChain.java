import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
 
 
public class WordChain {
 private static List<String> wordList = new ArrayList<String>();
 private static Map<String, Integer> visitMap = new HashMap<String, Integer>();
 private static Queue<String> activeQ = new LinkedList<String>();
 private static Map<String, String> parentMap = new HashMap<String, String>();
 private static Map<Integer, Stack<String>> wordChainMap = new HashMap<Integer, Stack<String>>();
 private static Integer wordChainCount = 0;
 private static int shortestWordChain = Integer.MAX_VALUE;
 private static int longestWordChain = Integer.MIN_VALUE;
 private static int longestChainIndex = 0;
 private static int shortestChainIndex = 0;
  
 public static void main(String[] args) {
  readWordFile();
  printWordList();
  String [] inputWords = readInput();
  System.out.println("==> reading of file nad taking input done"+inputWords[0]);
  wordChainFinder(inputWords);
  System.out.println();
  printWordChains();
 }
  
 public static void wordChainFinder(String [] words) {
  if (words == null) {
   return ;
  }
  String start = words[0]; 
  String end = words[1];
   
  activeQ.add(start);
  while (!activeQ.isEmpty()) {
   String first = activeQ.remove();
   if (visitMap.containsKey(first)) {
    continue;
   }
   Iterator<String> it = wordList.iterator();
   while (it.hasNext()) {
    String second = it.next();
    if (!visitMap.containsKey(second)) {
     boolean isPath = isOneLetterApart(first, second);
     if (isPath) {
      activeQ.add(second);
      parentMap.put(second, first);
      if (second.compareTo(end) == 0) {
       storeValidWordChain(second);
      }
     }
    }
   }
   visitMap.put(first, 1);
  }
   
 }
  
 private static void storeValidWordChain(String target) {
  ++wordChainCount;
  Stack<String> stack = new Stack<String>();
  String word = "";
  stack.push(target);
  while ((word = parentMap.get(target)) != null) {
   target = word;
   stack.push(target);
  }
  int chainLength = stack.size();
  if ( chainLength > longestWordChain) {
   longestWordChain = chainLength;
   longestChainIndex = wordChainCount;
  }
  if (chainLength < shortestWordChain) {
   shortestWordChain = chainLength;
   shortestChainIndex = wordChainCount;
  }
  wordChainMap.put(wordChainCount, stack);
 }
  
 public static void printWordChains() {
  System.out.println("Length of shortest word chain: " + shortestWordChain);
  Stack<String> stack = new Stack<String>();
  stack = wordChainMap.get(shortestChainIndex);
  printStack(stack);
   
  System.out.println("Printing all the word chains: ");
  Iterator mapIter = wordChainMap.entrySet().iterator();
  while (mapIter.hasNext()) {
   Map.Entry<Integer, Stack<String>> pair = (Map.Entry<Integer, Stack<String>>)mapIter.next();
   stack = pair.getValue();
   printStack(stack);
  }
 }
  
 private static void printStack(Stack<String> stack) {
  if (null == stack) { return ;}
  Iterator<String> it = stack.iterator();
  while (it.hasNext()) {
   System.out.print(it.next() + " ");
  }
  System.out.println();
 }
 private static boolean isOneLetterApart(String first, String second) {
  int len = first.length();
  int diff = 0;
  for (int i=0; i<len; i++) {
   if (first.charAt(i) != second.charAt(i)) {
    ++diff;
   }
  }
  return (diff == 1) ? true : false;
 }
  
 public static String [] readInput() {
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  try {
   System.out.println("enter the source and target words");
   String inputStrs[]=new String[2];
   inputStrs[0] = br.readLine();
   inputStrs[1] = br.readLine();
   return inputStrs;
   
  } catch (IOException e) {
   e.printStackTrace();
  }
  return null;
 }
  
 public static void readWordFile() {
  try {
   FileInputStream fileStream = new FileInputStream("FourLetterWords.txt");
   DataInputStream in = new DataInputStream(fileStream);
   BufferedReader br = new BufferedReader(new InputStreamReader(in));
    
   String sline = "";
   while ((sline = br.readLine()) != null) {
    String [] sArr = sline.split(" ");
    int len = sArr.length;
    for (int i=0; i<len; i++) {
     wordList.add(sArr[i]);
    }
   }
  } catch (Exception e) {
   System.err.println("ERROR: " + e.getMessage());
  }
 }

 public static void printWordList() {
  Iterator<String> it = wordList.iterator();
   
  while (it.hasNext()) {
   System.out.print(it.next() + "\n");
  }
  System.out.println();
 }

  
}
