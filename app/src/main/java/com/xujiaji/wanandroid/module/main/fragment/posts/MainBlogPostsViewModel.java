package com.xujiaji.wanandroid.module.main.fragment.posts;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.xujiaji.mvvmquick.lifecycle.SingleLiveEvent;
import com.xujiaji.wanandroid.RefreshLoadViewModel;
import com.xujiaji.wanandroid.base.BaseViewModel;
import com.xujiaji.wanandroid.model.RefreshLoadModel;
import com.xujiaji.wanandroid.repository.bean.BlogPostBean;
import com.xujiaji.wanandroid.repository.bean.PageBean;
import com.xujiaji.wanandroid.repository.bean.Result;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * author: xujiaji
 * created on: 2018/8/5 23:35
 * description:
 */
@Singleton
public class MainBlogPostsViewModel extends BaseViewModel implements RefreshLoadViewModel<BlogPostBean>{

    public final SingleLiveEvent<BlogPostBean> mClickEvent = new SingleLiveEvent<>();
    public final ObservableList<BlogPostBean> items = new ObservableArrayList<>();
    public final SingleLiveEvent<RefreshLoadModel<MutableLiveData<Result<PageBean<BlogPostBean>>>>> blogPostsLiveData = new SingleLiveEvent<>();

    @Inject
    public MainBlogPostsViewModel(@NonNull Application application) {
        super(application);
    }

    public SingleLiveEvent<RefreshLoadModel<MutableLiveData<Result<PageBean<BlogPostBean>>>>> getObservableBlogPosts() {
        blogPostsLiveData.setValue(new RefreshLoadModel<>(net.get().getBlogPosts(UPDATE_INDEX), true));
        return blogPostsLiveData;
    }

    @Override
    public void onListRefresh() {
        blogPostsLiveData.setValue(new RefreshLoadModel<>(net.get().getBlogPosts(UPDATE_INDEX), true));
    }

    @Override
    public void onListLoad(int offset) {
        blogPostsLiveData.postValue(new RefreshLoadModel<>(net.get().getBlogPosts(offset), false));
    }

    @Override
    public ObservableList<BlogPostBean> getList() {
        return items;
    }
}
