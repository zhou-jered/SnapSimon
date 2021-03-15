package me.local;

/**
 * 银行经理号称月利率 %0.35
 * 借十万块，三年期，每月还款 3128元
 * 让我们来算算实际利息是多少。
 * 假设我们每个月还的3128元是 2777（10w/36) + 350块的利息。等额本息还款方式
 */
public class LoanCalculator {

    public static void main(String[] args) {

        final double base = 1e5;
        final double basePerMonth = base / 36;
        final double returnPerMonth = 3128;

        double remainBase = base;
        double tempInterestSum = 0;
        for (int i = 1; i <= 36; i++) {
            //这个月付的利息钱
            double interestPay = returnPerMonth - basePerMonth;
            //这个月的利率，由于是一个月的比率，所以需要乘以12
            double interest = interestPay / remainBase * 12;
            //剩下的本金要减去这个月还掉的本金
            remainBase -= basePerMonth;
            //总利率
            tempInterestSum += interest;
            System.out.println(String.format("第%d个月的利率 : %.2f%%", i, interest * 100));
        }
        //计算下平均利率，用总利率除以36
        System.out.println(String.format("平均利率 : %.2f%%", (tempInterestSum / 36 * 100)));
    }


}
