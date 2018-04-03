/**
 * @ author Captain
 * @ date 2018/4/2
 * @ description as bellow.
 * Create a class named Substring that will expect the first command line argument to be a string, and the second two command line arguments to be integers,
 * the first will be used as an index and the second as a length.
 * The output should be the subtring of string starting at the index and of the specified length.
 */


public class Substring {

    public static void main(String[] args){
        String str = args[0];
        int start = Integer.parseInt(args[1]);
        int length = Integer.parseInt(args[2]);
        int end = start + length;
        System.out.println(str.substring(start,end));
    }

}
