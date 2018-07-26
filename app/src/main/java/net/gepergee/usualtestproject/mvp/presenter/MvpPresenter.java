package net.gepergee.usualtestproject.mvp.presenter;

import net.gepergee.usualtestproject.mvp.model.IMvpModel;
import net.gepergee.usualtestproject.mvp.model.IMvpModelImpl;
import net.gepergee.usualtestproject.mvp.view.IGetInfoView;

/**
 * Presenter
 *
 * @author petergee
 * @date 2018/7/25
 */
public class MvpPresenter {
    IMvpModel iMvpModel;
    IGetInfoView iGetInfoView;

    public MvpPresenter(IGetInfoView iGetInfoView) {
        this.iGetInfoView = iGetInfoView;
        iMvpModel = new IMvpModelImpl();
    }


    // 操作view层数据
    public void updateAge(int age) {
        iGetInfoView.updateAge(age);
    }

    public void updateName(String name) {
        if (name == null) {
            return;
        }
        iGetInfoView.updateName(name);
    }


    //操作model层数据

    public String setNameInfo(String name) {
        return iMvpModel.setNameInfo(name);
    }

    public int setAgeInfo(int age) {
        return iMvpModel.setAgeInfo(age);
    }

    // 将model与view进行关联
    public void getNetInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 模拟耗时操作
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // 设置model层数据
        int age = setAgeInfo(25);
        String name = setNameInfo("JAMES");

        // 传递给view层展示
        updateAge(age);
        updateName(name);
    }

}
