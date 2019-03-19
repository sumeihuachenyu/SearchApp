package com.example.lenovo.searchapp.common;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by lenovo on 2019-03-12.
 */
public class ViewHolder {
    /**
     *  @SuppressWarnings 批注允许您选择性地取消特定代码段（即，类或方法）中的警告。其中的想法是当您看到警告时，您将调查它，如果您确定它不是问题，
     *  您就可以添加一个 @SuppressWarnings 批注，以使您不会再看到警告。
     * @param view
     * @param id
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
