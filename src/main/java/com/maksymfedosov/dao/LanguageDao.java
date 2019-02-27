package com.maksymfedosov.dao;

import com.maksymfedosov.entity.Language;
import com.maksymfedosov.entity.Project;

import java.util.List;

public interface LanguageDao {

    void save(Language language);

    Language findById(int id);

    List findAll();

    void remove(Language language);
}
