package com.diviso.graeshoppe.service.mapper;

import com.diviso.graeshoppe.domain.*;
import com.diviso.graeshoppe.service.dto.BannerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Banner} and its DTO {@link BannerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BannerMapper extends EntityMapper<BannerDTO, Banner> {



    default Banner fromId(Long id) {
        if (id == null) {
            return null;
        }
        Banner banner = new Banner();
        banner.setId(id);
        return banner;
    }
}
