package com.dokuny.mini_campus.admin.repository;

import com.dokuny.mini_campus.admin.dto.BannerDto;
import com.dokuny.mini_campus.admin.entity.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BannerRepository extends JpaRepository<Banner, Long>,BannerSearchRepository {



}
