package com.my.battery.service.impl;

import com.my.battery.mo.AdminUserOperationLogMo;
import com.my.battery.mapper.AdminUserOperationLogMapper;
import com.my.battery.service.AdminUserOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户操作日志表 服务实现类
 * </p>
 *
 * @author weibocy
 * @since 2020-08-14
 */
@Service
public class AdminUserOperationLogServiceImpl extends ServiceImpl<AdminUserOperationLogMapper, AdminUserOperationLogMo> implements AdminUserOperationLogService {

}
