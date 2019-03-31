package com.kypi.demoproject.app.rx;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Khoa on 8/10/2017.
 */

public class SimpleEmptyObserver<T> extends DisposableObserver<T> {

    public SimpleEmptyObserver(){
    }

    @Override
    public void onNext(@NonNull T t) {
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
    }
}
