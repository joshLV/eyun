package com.rainsoft.union.web.sysmanage.service;

import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.union.web.sysmanage.model.Material;
import org.springframework.web.multipart.MultipartFile;

/**
 * 认证素材
 *
 * @author 13646223842@163.com
 * @since 1.0.0
 * 2015/11/30.
 */
public interface IMaterialService extends IMybatisBasePersitenceService<Material, String> {

    /**
     * 保存素材
     *
     * @param material 素材
     * @return 返回值
     */
    public Integer saveMaterial(Material material, MultipartFile file) throws Exception;

    /**
     * 修改素材
     *
     * @param material 素材
     * @return 返回值
     */
    public Integer updateMaterial(Material material, MultipartFile file) throws Exception;

    /**
     * 删除素材
     *
     * @param ids          传入的id 一个或者多个
     * @param updateTimes  修改时间 同上
     * @param materialUrls 素材的url
     * @return 返回值
     * @throws Exception
     */
    public Integer deleteMaterial(String ids, String updateTimes, String materialUrls) throws Exception;
}
