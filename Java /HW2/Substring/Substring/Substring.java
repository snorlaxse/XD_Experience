public class Substring {

    public static void main(String[] args){
        String str = args[0];
        int start = Integer.parseInt(args[1]);
        int length = Integer.parseInt(args[2]);
        int end = start + length;
        System.out.println(str.substring(start,end));
    }

}

