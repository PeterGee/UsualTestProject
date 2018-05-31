package net.gepergee.usualtestproject.designmode.adapter;

/**
 * @author geqipeng
 * @date 2018/3/9
 */

public class AmericaBank implements IWithdrawCash {
    private ChinaCard mChinaCard;

    public AmericaBank(ChinaCard chinaCard) {
        this.mChinaCard = chinaCard;
    }

    @Override
    public String withdrawCash() {
        return mChinaCard.withdrawCash();
    }
}
