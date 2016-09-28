package com.riontech.demoproject.ui.viewmodel;

import com.riontech.demoproject.AppConstants;
import com.riontech.demoproject.dao.Dao;
import com.riontech.demoproject.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dhaval Soneji Riontech on 27/9/16.
 */

public class MainViewModel {
    private MainActivity mActivity;
    private List<Dao> mList;

    public MainViewModel(MainActivity activity) {
        mActivity = activity;
        mList = new ArrayList<>();
        initRequest();
    }

    private void initRequest() {
        mActivity.generateList(generateList());
    }

    public List<Dao> generateList() {
        for (int i = 0; i < AppConstants.LIMIT_LIST_COUNT; i++) {
            if (i % 3 == 0) {
                mList.add(new Dao(AppConstants.LIST_TYPE_ONE, AppConstants.NOT_LIKED));
            } else {
                mList.add(new Dao(AppConstants.LIST_TYPE_TWO, AppConstants.NOT_LIKED));
            }
        }
        mList.add(new Dao(AppConstants.LIST_TYPE_LAST, AppConstants.LIKED));
        return mList;
    }
}
