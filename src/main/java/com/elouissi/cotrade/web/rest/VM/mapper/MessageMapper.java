package com.elouissi.cotrade.web.rest.VM.mapper;

import com.elouissi.cotrade.domain.Message;
import com.elouissi.cotrade.service.DTO.MessageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDTO EntityToDto(Message message);

}
