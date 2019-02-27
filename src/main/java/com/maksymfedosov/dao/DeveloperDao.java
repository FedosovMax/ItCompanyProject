package com.maksymfedosov.dao;

import com.maksymfedosov.entity.Developer;

import java.util.List;

public interface DeveloperDao {

    void save(Developer developer);

    Developer findById(int id);

    List findAll();

    void remove(Developer developer);

    void setLanguage(Developer developer, int languageId);

    void setLevel(Developer developer, int levelId);

    List<Developer> getAllDevelopersWhichLanguageIsJava(int languageId);

    List<Developer> getAllDevelopersWithParticularLevel(int skillLevelId);
}
