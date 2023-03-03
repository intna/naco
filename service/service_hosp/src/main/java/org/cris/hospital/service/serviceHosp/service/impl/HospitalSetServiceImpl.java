package org.cris.hospital.service.serviceHosp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cris.hospital.service.serviceHosp.mapper.HospitalSetMapper;
import org.cris.hospital.service.serviceHosp.service.HospitalSetService;
import org.cris.model.entity.hosp.HospitalSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper,HospitalSet> implements HospitalSetService {
    @Autowired
    private HospitalSetMapper hospitalSetMapper;
}
