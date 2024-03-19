package com.project.tutor.mapstruct;

import com.project.tutor.dto.TeachingDTO;
import com.project.tutor.many.dto.UserManyDTO;
import com.project.tutor.model.Teaching;
import com.project.tutor.model.User;
import com.project.tutor.request.TeachingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapStructGlobal {
    // TEACHING
    TeachingDTO toTeaching (Teaching teaching);

    @Mapping(target = "tutor.id" , source = "tutorId")
    Teaching toTeachingRequest(TeachingRequest request);

    // USER
    UserManyDTO toUser (User user);

}
