package com.elouissi.cotrade.web.rest.VM.mapper;

import com.elouissi.cotrade.domain.AppUser;
import com.elouissi.cotrade.web.rest.VM.LoginVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapper {
    AppUser VmToEntity(LoginVM loginVM);
//    User DtoToEntity(AuthUserDTO AuthUserDTO);

    LoginVM toVM(AppUser appUser);
//    AuthUserDTO toDTO(User user);
}

