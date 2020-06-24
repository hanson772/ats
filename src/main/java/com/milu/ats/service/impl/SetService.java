package com.milu.ats.service.impl;

import com.milu.ats.bean.enums.ELive;
import com.milu.ats.bean.enums.ERole;
import com.milu.ats.bean.enums.ESet;
import com.milu.ats.bean.pojo.EMessage;
import com.milu.ats.bean.pojo.Employee;
import com.milu.ats.bean.request.SetRequest;
import com.milu.ats.bean.response.SetResponse;
import com.milu.ats.bean.response.SetTypeResponse;
import com.milu.ats.dal.entity.SetDO;
import com.milu.ats.dal.repository.SetRepository;
import com.milu.ats.service.ISetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author max.chen
 * @class
 */
@Service
@Slf4j
public class SetService implements ISetService {
    private static String Cache_Set_All = "set:All";
    @Autowired
    SetRepository setRepository;

    /**
     * 返回所有Set Type类型
     * @return
     */
    @Override
    public List<SetTypeResponse> allSetTypes(){
        return Arrays.stream(ESet.values()).map(e-> SetTypeResponse.fromEnum(e)).collect(Collectors.toList());
    }

    /**
     * 返回Set值
     * @param isAll 是否返回已失效的值
     * @param type
     * @return
     */
    @Override
    public List<SetResponse> allSetsByType(boolean isAll, final int type){
        // 仅返回enble，或全部返回
        Stream<SetDO> values = isAll? cacheSetValueAll(false).stream() : findEnableAll().stream();
        // 如果type > 0 , 过滤某一类型type的值
        if(type > 0){
            values = values.filter(s-> type == s.getType());
        }

        return values.map(e-> SetResponse.fromDO(e)).collect(Collectors.toList());
    }

    /**
     * 保存基础数据
     * @param e
     * @param request
     */
    @Override
    public void save(Employee e, SetRequest request){
        checkAuthForSetEdit(e, request.getType());

        SetDO entity = SetDO.builder().type(request.getType())
                .code(request.getCode())
                .display(request.getDispaly())
                .description(request.getDescription())
                .build();

        entity.setCreator(e.getOperator());
        setRepository.saveAndFlush(entity);
        log.info("基础数据-{}-保存", request.getCode());
        cacheSetValueAll(true);
    }

    /**
     * 更新基础数据
     * @param e
     * @param sid
     * @param request
     */
    @Override
    public void update(Employee e, int sid, SetRequest request){
        checkAuthForSetEdit(e, request.getType());

        SetDO entity = setRepository.findById(sid).orElse(null);
        Assert.notNull(entity, EMessage.SET_NOT_EXIST.show());
        entity.setDisplay(request.getDispaly());
        entity.setDescription(request.getDescription());
        entity.setUpdater(e.getOperator());
        setRepository.saveAndFlush(entity);
        log.info("基础数据-{}-更新", request.getCode());
        cacheSetValueAll(true);
    }

    /**
     * 切换Set live 状态
     * @param e
     * @param sid
     * @param isLive
     */
    @Override
    public void switcher(Employee e, int sid, boolean isLive){
        checkAuthForSetEdit(e, 0);

        SetDO entity = setRepository.findById(sid).orElse(null);
        Assert.notNull(entity, EMessage.SET_NOT_EXIST.show());
        log.info("基础数据-{}-切换-{}", entity.getCode(), isLive ? "启用" : "禁用");
        // 如果已切换Set状态，不需要继续执行
        int newLive = isLive? ELive.ENABLE.getCode() : ELive.DISABLE.getCode();
        if(entity.getLive() != newLive){
            setRepository.switchSetLive(entity.getId(),  newLive, entity.getLive(), e.getOperator());
            cacheSetValueAll(true);
        }
    }

    /**
     * 缓存所有Set集合
     * @param flush 是否强制刷新缓存
     * @return
     */
    private List<SetDO> cacheSetValueAll(boolean flush){
        return setRepository.findAll();
    }

    /**
     * 返回所有有效的Set集合
     * @return
     */
    private List<SetDO> findEnableAll(){
        return cacheSetValueAll(false).stream().filter(s-> ELive.ENABLE.getCode() == s.getLive()).collect(Collectors.toList());
    }

    /**
     * 校验是否可以操作基础数据
     * @param e
     * @param type
     */
    private void checkAuthForSetEdit(Employee e, int type){
        Assert.isTrue(e.getActive() == ERole.Manager, EMessage.AUTH_NO.show());
    }

}
