public class SumOfArgs {

    public static void main(String args[]){

        int sum = 0;

        if(0 != args.length){

            for(int i=0;i<args.length;i++)
                try {
                    sum += Integer.parseInt(args[i]);

                } catch (NumberFormatException e) {

                    // System.out.println("args[" + i + "]  type convert in error");

                }
        }

        System.out.println(sum);

    }
}
