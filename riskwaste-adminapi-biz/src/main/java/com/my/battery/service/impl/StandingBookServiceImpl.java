package com.my.battery.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.my.battery.mapper.StandingBookMapper;
import com.my.battery.mo.StandingBookMo;
import com.my.battery.service.StandingBookService;

/**
 * <p>
 * 台账 服务实现类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
@Service
public class StandingBookServiceImpl extends ServiceImpl<StandingBookMapper, StandingBookMo>
        implements StandingBookService {

}
