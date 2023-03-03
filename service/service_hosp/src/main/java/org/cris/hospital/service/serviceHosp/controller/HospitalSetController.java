package org.cris.hospital.service.serviceHosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import io.swagger.annotations.ApiOperation;
import org.cris.hospital.common.commonUtil.result.Result;
import org.cris.hospital.common.commonUtil.util.MD5;
import org.cris.hospital.service.serviceHosp.service.HospitalSetService;
import org.cris.model.entity.hosp.HospitalSet;
import org.cris.model.vo.hosp.HospitalSetQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("findAll")
    public Result findAllHospitalSet(){
        List<HospitalSet> list =hospitalSetService.list();
        return Result.ok(list);
    }


    @ApiOperation(value = "逻辑删除医院设置")
    @GetMapping("{id}")
    public Result removeHospSet(@PathVariable Long id){
        boolean flag =hospitalSetService.removeById(id);
        if (flag){
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //分页查询
    @ApiOperation(value = "分页查询")
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false)HospitalSetQueryVo hospitalSetQueryVo){
        Page<HospitalSet> page = new Page<>(current,limit);
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if (!StringUtils.isNullOrEmpty(hosname)){
            wrapper.eq("hoscode",hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isNullOrEmpty(hoscode)){
            wrapper.eq("hosname",hospitalSetQueryVo.getHoscode());
        }
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page,wrapper);

        return Result.ok(pageHospitalSet);
    }

    //添医院设置
    @ApiOperation(value = "添加医院设置")
    @PostMapping("saveHospitalSet")
    public  Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        hospitalSet.setStatus(1);

        //签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        boolean save = hospitalSetService.save(hospitalSet);
        if (save){
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //根据ID获取医院设置
    @ApiOperation(value = "根据id获取医院设置")
    @GetMapping("getHostSet/{id}")
    public Result getHospSet(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    //修改医院设置
    @ApiOperation(value = "修改医院设置")
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag){
            return Result.ok();
        } else {
            return Result.fail();
        }

    }

    //批量删除
    @ApiOperation(value = "批量删除")
    @DeleteMapping("batchRemove")
    public  Result batchRemoveHospitalSet(@RequestBody List<Long> idlist){
        hospitalSetService.removeBatchByIds(idlist);
        return Result.ok();
    }

}
