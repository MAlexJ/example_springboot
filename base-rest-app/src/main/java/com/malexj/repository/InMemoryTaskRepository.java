package com.malexj.repository;

import com.malexj.model.Task;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryTaskRepository implements TasksRepository {

  private final List<Task> tasks =
      new ArrayList<>() {
        {
          add(new Task("First task"));
          add(new Task("Second task"));
        }
      };

  @Override
  public List<Task> findAll() {
    return tasks;
  }
}
