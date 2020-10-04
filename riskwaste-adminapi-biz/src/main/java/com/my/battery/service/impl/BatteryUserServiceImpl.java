package com.my.battery.service.impl;

import com.my.battery.mo.BatteryUserMo;
import com.my.battery.mapper.BatteryUserMapper;
import com.my.battery.service.BatteryUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-11
 */
@Service
public class BatteryUserServiceImpl extends ServiceImpl<BatteryUserMapper, BatteryUserMo> implements BatteryUserService {

}
