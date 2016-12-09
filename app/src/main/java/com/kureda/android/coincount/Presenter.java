package com.kureda.android.coincount;

/**
 * Created by Serg on 12/8/2016.
 */


public class Presenter implements Contract.Presenter{

    Contract.View mView;

    public Presenter(Contract.View view) {
        mView=view;
    }
}
