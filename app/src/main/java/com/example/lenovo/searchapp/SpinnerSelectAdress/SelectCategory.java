package com.example.lenovo.searchapp.SpinnerSelectAdress;

/**
 * Created by lenovo on 2019-03-26.
 * 需要实现的接口
 */
public interface SelectCategory {
    /**
     * 把选中的下标通过方法回调回来
     * @param parentSelectposition  父类别选中下标
     * @param type
     */
    public void selectCategory(int parentSelectposition,boolean type);
}
