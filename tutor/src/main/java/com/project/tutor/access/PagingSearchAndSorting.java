package com.project.tutor.access;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PagingSearchAndSorting {
  public  Pageable pageablePageSizeAndRecordOrSearchOrSort (int page ,int record ){
      return PageRequest.of(page , record);
  }

}
