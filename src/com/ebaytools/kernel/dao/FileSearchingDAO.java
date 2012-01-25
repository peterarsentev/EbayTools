package com.ebaytools.kernel.dao;

import com.ebaytools.kernel.entity.FileSearching;

import java.util.List;

public interface FileSearchingDAO extends CrUD<FileSearching> {
    public List<FileSearching> getAllFileSearching();
}