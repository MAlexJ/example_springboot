package com.malexj.repository;

import com.malexj.model.Task;
import java.util.List;

public interface TasksRepository {

  List<Task> findAll();
}
