package com.BookingApp.dto;

import com.BookingApp.model.AppUser;

public class UserTokenState {
	
    private String accessToken;
    private int expiresIn;
    private AppUser user;

    public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = 0;
        this.user = null;
    }



    public UserTokenState(String accessToken, int expiresIn, AppUser user) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.user = user;
	}

	public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
    
}
