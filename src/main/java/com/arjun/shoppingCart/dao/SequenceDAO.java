package com.arjun.shoppingCart.dao;

import com.arjun.shoppingCart.exception.SequenceException;

public interface SequenceDAO {
	long getNextSequenceId(String key) throws SequenceException;
}
