package com.elouissi.cotrade.web.rest.VM.mapper;

import com.elouissi.cotrade.domain.Conversation;
import com.elouissi.cotrade.service.DTO.ConversationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ConversationMapper {
    ConversationDTO EntityToDto(Conversation conversation);

}
