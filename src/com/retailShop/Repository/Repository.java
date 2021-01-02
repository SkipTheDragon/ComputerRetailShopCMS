package com.retailShop.Repository;

import com.retailShop.Entity.User;

public interface Repository<T> {
    User findBy(String attribute, String value);
}
