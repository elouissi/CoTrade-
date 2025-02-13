package com.elouissi.cotrade.web.rest.VM.mapper;


import com.elouissi.cotrade.domain.AppUser;
import com.elouissi.cotrade.service.DTO.AuthUserDTO;
import com.elouissi.cotrade.web.rest.VM.RegisterVM;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterMapper {
    AppUser VmToEntity(RegisterVM registerVM);
    AppUser DtoToEntity(AuthUserDTO AuthUserDTO);

    RegisterVM toVM(AppUser appUser);
    AuthUserDTO toDTO(AppUser appUser);
}
