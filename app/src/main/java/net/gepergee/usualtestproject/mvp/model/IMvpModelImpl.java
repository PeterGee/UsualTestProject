package net.gepergee.usualtestproject.mvp.model;

/**
 * @author petergee
 * @date 2018/7/25
 */
public class IMvpModelImpl implements IMvpModel{
    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public String setNameInfo(String name) {
        return name;
    }

    @Override
    public int setAgeInfo(int age) {
        return age;
    }
}
