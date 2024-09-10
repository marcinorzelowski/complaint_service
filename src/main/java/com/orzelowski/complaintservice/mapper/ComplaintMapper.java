package com.orzelowski.complaintservice.mapper;

import com.orzelowski.complaintservice.dto.ComplaintResponse;
import com.orzelowski.complaintservice.model.Complaint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {

    @Mapping(source = "author.id", target = "authorId")
    @Mapping(source = "product.id", target = "productId")
    ComplaintResponse toResponse(Complaint complaint);
}
