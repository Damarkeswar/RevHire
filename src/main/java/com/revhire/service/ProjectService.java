package com.revhire.service;

public interface ProjectService {

    boolean addProject(
        int resumeId,
        String title,
        String description
    );
}
