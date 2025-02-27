package com.elouissi.cotrade.web.rest.VM.mapper;

import com.elouissi.cotrade.domain.Photo;
import com.elouissi.cotrade.web.rest.VM.PhotoVM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhotoMapper {
    @Mapping(source = "post.id", target = "postId")
    PhotoVM toVM(Photo photo);

    @Mapping(target = "post.id", source = "postId")
    Photo toEntity(PhotoVM photoVM);
}