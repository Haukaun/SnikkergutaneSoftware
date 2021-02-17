package com.gruppeprosjekt.Type;

import java.util.ArrayList;

public abstract class UserType {
    String name;
    ArrayList<String> permissions;

    public abstract String getName();
    public abstract ArrayList<String> getPermissions();
}
