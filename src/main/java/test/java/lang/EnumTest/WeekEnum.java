package test.java.lang.EnumTest;

/**
 * Created by Administrator on 2017/5/31.
 */
public enum WeekEnum {
    Monday("星期一", 1),
    Tuesday("星期二", 2),
    Wednesday("星期三", 3),
    Thursday("星期四", 4),
    Friday("星期五", 5),
    Saturday("星期六", 6),
    Sunday("星期日", 7);

    private String cn_name;
    private int num;
    private WeekEnum(String cn_name, int num) {
        this.cn_name = cn_name;
        this.num = num;
    }

    public String getCn_name() {
        return this.cn_name;
    }

}
