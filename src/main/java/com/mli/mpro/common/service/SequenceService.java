package com.mli.mpro.common.service;

import com.mli.mpro.common.exception.UserHandledException;

public interface SequenceService {

    long getNextSequenceId(String key) throws UserHandledException;

}