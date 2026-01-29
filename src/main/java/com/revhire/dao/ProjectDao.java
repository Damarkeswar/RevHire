package com.revhire.dao;

public interface ProjectDao {

    boolean addProject(
        int resumeId,
        String title,
        String description
    );
}
