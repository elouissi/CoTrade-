package com.elouissi.cotrade.web.rest.VM.mapper;

import com.elouissi.cotrade.domain.Photo;
import com.elouissi.cotrade.domain.Post;
import com.elouissi.cotrade.service.DTO.PostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "photos", target = "photoIds")
    PostDTO toDto(Post post);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "photos", ignore = true)
    Post toEntity(PostDTO postDTO);

    default List<UUID> mapPhotosToPhotoIds(List<Photo> photos) {
        if (photos == null) {
            return null;
        }
        return photos.stream()
                .map(Photo::getId)
                .collect(Collectors.toList());
    }
}