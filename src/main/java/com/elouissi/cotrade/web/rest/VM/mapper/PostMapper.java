package com.elouissi.cotrade.web.rest.VM.mapper;

import com.elouissi.cotrade.domain.Photo;
import com.elouissi.cotrade.domain.Post;
import com.elouissi.cotrade.service.DTO.PostDTO;
import com.elouissi.cotrade.service.DTO.PhotoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "user.id", target = "userId")
    PostDTO toDto(Post post);

    @Mapping(target = "user.id", source = "userId")
    Post toEntity(PostDTO postDTO);

    Photo photoToPhotoDTO(Photo photo);
    Photo photoDTOToPhoto(PhotoDTO photoDTO);
}