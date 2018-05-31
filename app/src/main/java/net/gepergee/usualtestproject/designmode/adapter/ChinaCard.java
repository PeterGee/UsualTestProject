package net.gepergee.usualtestproject.designmode.adapter;

/**
 * @author geqipeng
 * @date 2018/3/9
 */

public class ChinaCard implements IWithdrawCash {
    @Override
    public String withdrawCash() {
        return "RMB";
    }
}
