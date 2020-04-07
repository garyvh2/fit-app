package com.gitgud.fitapp.ui.unauthorized.registration;

import com.gitgud.fitapp.data.source.UserDataSource;

public class RegistrationModule {
    public static RegistrationViewModel createViewModel() {
        return new RegistrationViewModel(
                UserDataSource.getInstance()
        );
    }
}
