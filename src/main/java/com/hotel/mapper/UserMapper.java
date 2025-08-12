package com.hotel.mapper;

import com.hotel.entity.User;
import com.hotel.web.dto.user.UserListResponse;
import com.hotel.web.dto.user.UserRequest;
import com.hotel.web.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UserRequest request);

    User requestToUser(Long userId, UserRequest request);

    UserResponse userToResponse(User user);

    List<UserResponse> userListToUserResponseList(List<User> users);

    default UserListResponse userListToUserListResponse(List<User> users) {
        UserListResponse listResponse = new UserListResponse();
        listResponse.setUsers(userListToUserResponseList(users));
        return listResponse;
    }
}
