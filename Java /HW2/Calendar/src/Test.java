import java.util.Calendar;
import java.util.Date;

/**
 * @ author Captain
 * @ date 2018/4/2
 * @ description as bellow.
 */


public class Test {

    public static void main(String args[]){
        Calendar calendar = Calendar.getInstance(); // 创建Calendar 对象
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        System.out.println(" 当前时间 " + year +"-"+month+"-" +day+" "+hour+":"+minute + ":"+ second);

        Calendar calendar0 = Calendar.getInstance(); // 创建Calendar 对象
//        System.out.println(calendar0.getTime() );   // 转换成Date对象
//        System.out.println(calendar0.getTimeInMillis());     // 获取时间戳转换成毫秒值

        // 初始化 Date 对象
        Date date = new Date();

        // 使用toString()显示日期和时间
       // System.out.printf("%1$s %2$tB %2$td, %2$tY\n", "Due date:", date);
        // 显示格式化时间
        //System.out.printf("%s %tB %<te, %<tY\n", "Due date:", date);
    }

}
