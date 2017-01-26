package com.example.kennethlopez.testapp.di.component;


import com.example.kennethlopez.testapp.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface ActivityComponent extends AppComponent {
}
