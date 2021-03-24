package com.avinash.weatherdemo.viewmodel.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends AndroidViewModel {
    public final CompositeDisposable disposables = new CompositeDisposable();

    //
    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}

