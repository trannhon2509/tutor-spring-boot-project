package com.project.tutor.request;

import com.project.tutor.mapper.FeedBack;
import com.project.tutor.model.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequest {
   private String username;
   private  String password;
   private String passwordRepeat;
   private String passwordRepeatNew;
   private String email;
   private  String firstName;
   private String lastName;
   private String address;
   private String phoneNumber;
   private boolean isActive;
   private String activeCode;
   private LocalDateTime createAt;
   private List<Role> listUserRoles;
   private List<FeedBack> listFeedbacks;

   public boolean isActive() {
      return isActive;
   }

   public void setActive(boolean active) {
      isActive = active;
   }
}
