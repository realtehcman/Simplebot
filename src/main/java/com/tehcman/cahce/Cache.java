package com.tehcman.cahce;

import java.util.List;

public interface Cache <T>{
   void add();
   void remove();
   T findBy(Long id);
   List<T> getAll();

}
