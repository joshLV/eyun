package com.rainsoft.riplat.web.notice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.rainsoft.base.common.page.Page;
import com.rainsoft.base.common.page.PageHelper;
import com.rainsoft.base.common.service.IMybatisBasePersitenceService;
import com.rainsoft.base.common.utils.Constants;
import com.rainsoft.base.common.utils.DesUtil;
import com.rainsoft.base.common.utils.SpringMvcUtil;
import com.rainsoft.base.common.utils.StringUtil;
import com.rainsoft.base.common.web.controller.SpringBaseController;
import com.rainsoft.base.common.web.vo.AuthenToken;
import com.rainsoft.base.common.web.vo.PageBean;
import com.rainsoft.base.common.web.vo.Result;
import com.rainsoft.base.web.system.model.BaseUser;
import com.rainsoft.base.web.system.service.IBaseUserService;
import com.rainsoft.riplat.web.notice.model.AccpeterUser;
import com.rainsoft.riplat.web.notice.model.EdaAppMembers;
import com.rainsoft.riplat.web.notice.model.Group;
import com.rainsoft.riplat.web.notice.model.Notice;
import com.rainsoft.riplat.web.notice.model.Notice4App;
import com.rainsoft.riplat.web.notice.model.Place;
import com.rainsoft.riplat.web.notice.model.ReplyHistory;
import com.rainsoft.riplat.web.notice.service.IAccpeterUserService;
import com.rainsoft.riplat.web.notice.service.IEdaAppService;
import com.rainsoft.riplat.web.notice.service.IGroupService;
import com.rainsoft.riplat.web.notice.service.IMemberGroupService;
import com.rainsoft.riplat.web.notice.service.INoticeReplyService;
import com.rainsoft.riplat.web.notice.service.INoticeService;
import com.rainsoft.riplat.web.notice.service.IPlaceService;
import com.rainsoft.riplat.web.notice.service.IPlatformUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/NoticeService")
public class NoticeWSController extends SpringBaseController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeWSController.class);
    private static final String AUTHDIGEST = "EYUN";// HTTP消息头请求的digest
    private static final String RESDIGEST = "3uUGXPEC844=";// 返回消息头加密信息：EYUN

    @Resource
    private INoticeService noticeServiceImpl;

    @Resource
    private IAccpeterUserService accpeterUserServiceImpl;

    @Resource
    private IBaseUserService baseUserService;

    @Resource
    private IPlatformUserService platformUserServiceImpl;

    @Resource
    private IGroupService groupServiceImpl;

    @Resource
    private IPlaceService placeServiceImpl;

    @Resource
    private IEdaAppService edaAppServiceImpl;

    @Resource
    private IMemberGroupService memberGroupServiceImpl;
    
    @Resource
    private INoticeReplyService noticeReplyServiceImpl;
    
    /**
     * 其他平台导入Key文件校验
     * 
     * @param params
     */
    @RequestMapping(value = "/activatePlatformKey", method = RequestMethod.POST)
    public void saveActivatePlatformKey(@ModelAttribute("params") String params) {

        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    String oldPlatformId = jsonObj.getString("oldPlatformId");// 旧的平台Id
                    String platformId = jsonObj.getString("platformId");// 新的平台Id
                    if (StringUtil.isNotEmpty(platformId) && noticeServiceImpl.validateEnablePlatformId(platformId)) {// 验证输入的平台ID是否有效
                        logger.info("platformId is enabled");
                        if (StringUtil.isNotEmpty(oldPlatformId)) {// updateDB中的用户平台ID
                            noticeServiceImpl.removePlatformId(oldPlatformId);// 移除文本中的平台ID MAP 和 DB
                            platformUserServiceImpl.updatePlatformKey(oldPlatformId, platformId);// 升级DB中用户的平台ID
                        }
                        // 激活
                        noticeServiceImpl.disablePlatformId(platformId);// 设置平台ID失效并修改文件中平台ID已激活
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                    } else {
                        result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);// 激活失败或参数错误
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        } finally {
            SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
        }
    }

    /**
     * 获取通知通告列表信息
     * 
     * @param params
     */
    @RequestMapping(value = "/loadNoticeGrid", method = RequestMethod.POST)
    public void loadNoticeGrid(@ModelAttribute("params") String params) {

        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    // 验证用户是否有效
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {//&& validatePlatformUser(jsonObj)
                        // 反射实体获取参数
                        Notice notice = JSONObject.parseObject(json, Notice.class);
                        String item = jsonObj.getString("itemId");
                        notice.setItem(item);
                        setPageBean(jsonObj);
                        PageBean page = getGridData();
                        PageHelper.startPage(page.getCurrPage() , page.getPageSize()); //创建页
                        List<Notice> list = noticeServiceImpl.findListBy(notice);
                        page.setTotal(((Page<Notice>)list).size());
//                        super.loadGrid(notice);
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                        result.setCount(Integer.parseInt(String.valueOf(page.getTotal())));
                        result.setData(list);
                        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result); //返回结果要进行加密
                        return;
                    } else {
                        result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    /**
     * 获取通知通告接收人信息
     * 
     * @param params
     */
    @RequestMapping(value = "/loadAccpeterUserGrid", method = RequestMethod.POST)
    public void loadAccpeterUserGrid(@ModelAttribute("params") String params) {

        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {//&& validatePlatformUser(jsonObj)
                        // 反射实体获取参数
                        String noticeId = jsonObj.getString("noticeId");// 获取通知ID
                        AccpeterUser accpeterUser = new AccpeterUser();
                        accpeterUser.setNoticeId(noticeId);
                        // 获取分页数据--接受人信息的翻页信息
                        setPageBean(jsonObj);
                        PageBean page = getGridData();
                        String search = jsonObj.getString("searchValue");
                        accpeterUser.setSearchValue(search);
                        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
                        List<AccpeterUser> list = accpeterUserServiceImpl.findListBy(accpeterUser, page.getSort(), page.getOrder());
                        page.setTotal(((Page<AccpeterUser>) list).getTotal());
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                        result.setCount(Integer.parseInt(String.valueOf(page.getTotal())));
                        result.setData(list);
                        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
                        return;
                    } else {
                        result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    /**
     * 获取通知通告详情
     * 
     * @param params
     */
    @RequestMapping(value = "/noticeDetailInfo", method = RequestMethod.POST)
    public void noticeDetailInfo(@ModelAttribute("params") String params) {

        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId"))) {// && validatePlatformUser(jsonObj)
                        String noticeId = jsonObj.getString("noticeId");
                        Notice notice = noticeServiceImpl.getNoticeDetailInfoById(noticeId);
                        result.setData(notice);
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                    } else {
                        result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    /**
     * 获取通知通告详情
     * 
     * @param params
     */
    @RequestMapping(value = "/saveNotice", method = RequestMethod.POST)
    public void saveNotice(@ModelAttribute("params") String params) {

        String json = null;
        Result result = new Result();
        String msg = "";
        String status = "";
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {
                        if(validatePlatformUser(jsonObj)){

                            Notice notice = JSONObject.parseObject(json, Notice.class);
                            //针对ISEC同步通知通告,需要再service内部做userId判断并获取
                            noticeServiceImpl.saveOrUpdate(notice,jsonObj);
                            status = Constants.RETURN_SUCCESS_INTERFACE;
                            msg = "保存成功";
                        }else{
                            msg = "用户不合法";
                            status = Constants.RETURN_SUCCESS_INTERFACE;
                        }

                    } else {
                        status = Constants.RETURN_WRONGPARAM_INTERFACE;
                        msg = "平台不存在或者平台未激活";
                    }
                } else {
                    status = Constants.RETURN_WRONGPARAM_INTERFACE ;
                    msg = "参数不存在或者参数不是合法的JSON格式";
                }
            } else {// HTTP消息头错误
                status = Constants.RETURN_UNAUTHORISED_INTERFACE;
                msg = "HTTP消息头错误";
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            msg = "NoticeWSController.saveNotice-->riplat 异常";
            logger.error("NoticeWSController.saveNotice-->"+e.getMessage());
        }
        result.setStatus(status);
        result.setMessage(msg);
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    /**
     * 加载通知修改页面数据,目前只获取通知的基本信息
     * 
     * @param params
     */
    @RequestMapping(value = "/loadNoticeData", method = RequestMethod.POST)
    public void loadNoticeData(@ModelAttribute("params") String params) {

        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {//&& validatePlatformUser(jsonObj)
                        String id = jsonObj.getString("id");
                        Notice notice = noticeServiceImpl.findById(id);
                        result.setData(notice);
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
                    } else {
                        result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    /**
     * 列表页面发送通知
     * 
     * @param params
     */
    @RequestMapping(value = "/sendNoticeById", method = RequestMethod.POST)
    public void saveSendNoticeById(@ModelAttribute("params") String params) {
        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {//&& validatePlatformUser(jsonObj)
                        String id = jsonObj.getString("id");// 通知ID
                        if (StringUtil.isNotEmpty(id)) {
                            noticeServiceImpl.saveSendNotice(id);
                            result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                        } else {
                            result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                        }
                    } else {
                        result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    /**
     * 通知删除
     * 
     * @param params
     */
    @RequestMapping(value = "/delNotice", method = RequestMethod.POST)
    public void delNotice(@ModelAttribute("params") String params) {
        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {//&& validatePlatformUser(jsonObj)
                        String id = jsonObj.getString("id");// 通知ID
                        if (StringUtil.isNotEmpty(id)) {
                            super.del(id);// 删除通知
                            result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                        } else {
                            result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                        }
                    } else {
                        result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    /**
     * 获取组列表信息
     * 
     * @param params
     */
    @RequestMapping(value = "/loadGroupGrid", method = RequestMethod.POST)
    public void loadGroupGrid(@ModelAttribute("params") String params) {
        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    // 验证用户是否有效
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {//&& validatePlatformUser(jsonObj)
                        // 反射实体获取参数
                        Group group = JSONObject.parseObject(json, Group.class);
                        setPageBean(jsonObj);
                        PageBean page = getGridData();
                        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
                        setPageBean(jsonObj);
                        List<Group> list = groupServiceImpl.findListBy(group, page.getSort(), page.getOrder());
                        page.setTotal(((Page<Group>) list).getTotal());
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                        result.setCount(Integer.parseInt(String.valueOf(page.getTotal())));
                        result.setData(list);
                        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
                        return;
                    } else {
                        result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    /**
     * 获取通知通告列表信息
     * 
     * @param params
     */
    @RequestMapping(value = "/loadPlaceGrid", method = RequestMethod.POST)
    public void loadPlaceGrid(@ModelAttribute("params") String params) {
        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    // 验证用户是否有效
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {//&& validatePlatformUser(jsonObj)
                        // 反射实体获取参数
                        Place place = JSONObject.parseObject(json, Place.class);
                        String id = jsonObj.getString("groupId");// 获取GroupId
                        String loginUserName = jsonObj.getString("loginUsername");
                        place.setGroupId(id); //此处为groupId,怎么赋值给Id，后面取还是groupId
                        place.setUserName(loginUserName);
                        // 获取分页数据--接受人信息的翻页信息
                        setPageBean(jsonObj);
                        PageBean page = getGridData();
                        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
                        List<Place> list = placeServiceImpl.findListBy(place, page.getSort(), page.getOrder());
                        page.setTotal(((Page<Place>) list).getTotal());
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                        result.setCount(Integer.parseInt(String.valueOf(page.getTotal())));
                        result.setData(list);
                        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
                        return;
                    } else {
                        result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    /**
     * 加载易达用户列表
     * 
     * @param params
     */
    @RequestMapping(value = "/loadEdaGrid", method = RequestMethod.POST)
    public void loadEdaGrid(@ModelAttribute("params") String params) {
        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    // 验证用户是否有效
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {//&& validatePlatformUser(jsonObj)
                        // 反射实体获取参数
                        EdaAppMembers eda = JSONObject.parseObject(json, EdaAppMembers.class);
                        // 获取分页数据--接受人信息的翻页信息
                        setPageBean(jsonObj);
                        PageBean page = getGridData();
                        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
                        List<EdaAppMembers> list = edaAppServiceImpl.findListBy(eda, page.getSort(), page.getOrder());// 编辑用户组时加载易达用户列表信息
                        page.setTotal(((Page<EdaAppMembers>) list).getTotal());
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                        result.setCount(Integer.parseInt(String.valueOf(page.getTotal())));
                        result.setData(list);
                        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
                        return;
                    } else {
                        result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    /**
     * 组策略新增/修改
     * 
     * @param params
     */
    @RequestMapping(value = "/saveOrUpdateGroup", method = RequestMethod.POST)
    public void saveOrUpdateGroup(@ModelAttribute("params") String params) {
        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    // 验证用户是否有效
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId"))) {// && validatePlatformUser(jsonObj)
                        // 反射实体获取参数
                        Group group = JSONObject.parseObject(json, Group.class);
                        groupServiceImpl.saveOrUpdate(group);
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                    } else {
                        result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    /**
     * 加载组信息
     * 
     * @param params
     */
    @RequestMapping(value = "/loadGroupData", method = RequestMethod.POST)
    public void loadGroupData(@ModelAttribute("params") String params) {
        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    // 验证用户是否有效
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {//&& validatePlatformUser(jsonObj)
                        Group group = JSONObject.parseObject(json, Group.class);
                        group = groupServiceImpl.loadGroupData(group);
                        result.setData(group);
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
                        return;
                    } else {
                        result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    /**
     * 删除组策略
     * 
     * @param params
     */
    @RequestMapping(value = "/delGroup", method = RequestMethod.POST)
    public void delGroup(@ModelAttribute("params") String params) {
        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    // 验证用户是否有效
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {//&& validatePlatformUser(jsonObj)
                        // 反射实体获取参数
                        Group group = JSONObject.parseObject(json, Group.class);
                        groupServiceImpl.deleteById(group.getId().toString());// 删除Group表记录
                        memberGroupServiceImpl.deleteById(group.getId().toString());// 删除Group中成员的表记录
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                    } else {
                        result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }    

    /**
     * 验证平台用户是否和合法的登录用户 Authorization：
     * 
     * @return
     */
    public boolean validatePlatformUser(JSONObject jsonObj) {
        // 验证用户是否有效
        String loginName = jsonObj.getString("loginUsername");
        String password = jsonObj.getString("loginPassword");
        String platformId = jsonObj.getString("platformId");
        BaseUser user = new BaseUser();
        user.setLoginName(loginName);
        user.setPassword(password);// 其他平台传过来是已经加密后的
        user.setLoginWay(1); // 最后登录方式, 1：web网页; 2：wap网页; 3：手机客户端；4：未知
        user.setPlatformId(platformId);// 属于哪个平台
        user = baseUserService.loginWithPlatform(user);
        if (user != null && user.getId() != null) {
            setAuthenToken(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置认证token信息
     * 
     * @param user
     */
    public void setAuthenToken(BaseUser user) {
        // 用户信息保存到session
        AuthenToken authenToken = new AuthenToken();
        authenToken.setAnotherName(user.getAnotherName());
        authenToken.setLoginTime(new Date());
        authenToken.setLoginName(user.getLoginName());
        authenToken.setUserId(user.getId());
        authenToken.setPlatformId(user.getPlatformId());
        getRequest().getSession().setAttribute("AuthenToken", authenToken);
    }

    /**
     * 设置分页信息和搜索条件
     * 
     * @param jsonObj
     */
    public void setPageBean(JSONObject jsonObj) {
        // 转换成json对象，获取参数
        String searchValue = jsonObj.getString("searchValue");// 获取搜索值
        getRequest().setAttribute("searchValue", searchValue);
        // 获取分页数据
        String currPage = StringUtil.isNotEmpty(jsonObj.getString("currPage")) ? jsonObj.getString("currPage") : "1";
        String pageSize = StringUtil.isNotEmpty(jsonObj.getString("pageSize")) ? jsonObj.getString("pageSize") : String.valueOf(Constants.PAGE_ROWS);
        getRequest().setAttribute("page", currPage);// 设置当前页和页面显示条数到request中
        getRequest().setAttribute("rows", pageSize);
    }

    /**
     * 发送给易达App的信息列表
     *
     * @param params
     */
    @RequestMapping(value = "/noticeList", method = RequestMethod.POST)
    public void noticeList(@ModelAttribute("params") String params) {

        String json = null;
        Result result = null;
        String userId = null;// 易达ID
        String noticeLevel = null;//优先级
        String isDelete = null;//是否删除
        int pageSize;// 新页面记录数
        String lastRecordId = null;// 最后一条记录的Id,存在则进行翻页查询
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                logger.info("=============================noticeList接口：请求参数解密后为" + json + "=========================================");
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    if (jsonObj != null) {
                        userId = jsonObj.getString("edaId");
                        noticeLevel = jsonObj.getString("noticeLevel");
                        isDelete = jsonObj.getString("isDelete");
                        pageSize = jsonObj.getInteger("pageSize") == null ? Constants.PAGE_ROWS : jsonObj.getInteger("pageSize");
                        lastRecordId = jsonObj.getString("lastRecordId");
//                        if (StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty(noticeLevel) && StringUtil.isNotEmpty(isDelete)) {// 判断必填参数是否都有值
                        if (StringUtil.isNotEmpty(userId)) {// 判断必填参数是否都有值
                            result = noticeServiceImpl.noticeList(userId, pageSize, lastRecordId, noticeLevel, isDelete);
                        }
                    }
                }
            } else {// HTTP消息头错误
                result = new Result();
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == null) {// 当参数不正确时
            result = new Result();
            result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
        }
        this.responseAjaxData(result, "noticeList");
    }

    /**
     * 已读状态变更
     *
     * @param params
     */
    @RequestMapping(value = "/markRead", method = RequestMethod.POST)
    public void readNotice(@ModelAttribute("params") String params) {

        String json = null;
        Result result = null;
        String userId = null;// 易达ID
        String recordId = null;// 标记为已读状态的NoticeRecordId
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    if (jsonObj != null) {
                        userId = jsonObj.getString("edaId");
                        recordId = jsonObj.getString("recordId");
                        if (StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty(recordId)) {// 判断必填参数是否都有值
                            result = noticeServiceImpl.readNotice(userId, recordId);
                        }
                    }
                }
            } else {// HTTP消息头错误
                result = new Result();
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == null) {// 当参数不正确时
            result = new Result();
            result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
        }
        this.responseAjaxData(result, "noticeList");
    }

    /**
     * 通知消息详情
     * 
     * @param recordId
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value = "/noticeDetail")
    public String noticeDetail(@ModelAttribute("recordId") String recordId, @ModelAttribute("userId") String userId, Model model) {

        if (StringUtil.isNotEmpty(recordId)) {// 判断必填参数是否都有值
            Notice4App notice = noticeServiceImpl.noticeDetail(recordId);
                ReplyHistory replyHistory = new ReplyHistory();
                replyHistory.setNoticeId(recordId);
                List<ReplyHistory> rhList = noticeReplyServiceImpl.findListBy(replyHistory, null, null);
                model.addAttribute("notice", notice);
                model.addAttribute("userId", userId);
                model.addAttribute("rhList", rhList);
                model.addAttribute("userType", Constants.EDA_ACCOUNT_TYPE);
        }
        return getPrefix() + "/noticeDetail";
    }
    
    /**
     * 通知消息详情
     * 
     * @param recordId
     * @param model
     * @return
     */
    @RequestMapping(value = "/replyHistory")
    public void replyHistory(@ModelAttribute("recordId") String recordId, Model model) {
        Result result = new Result();
        try {
            if (StringUtil.isNotEmpty(recordId)) {// 判断必填参数是否都有值
                ReplyHistory replyHistory = new ReplyHistory();
                replyHistory.setNoticeId(recordId);
                List<ReplyHistory> rhList = noticeReplyServiceImpl.findListBy(replyHistory, null, null);
                result.setStatus(Constants.RETURN_SUCCESS);
                result.setData(rhList);
                super.responseAjaxData(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(Constants.RETURN_ERROR);
            super.responseAjaxData(result);
        }
    }
    
   
    /**
     * APP页面回复
     * @param recordId
     * @param userId
     * @param model
     */
    @RequestMapping(value = "/replayNotice")
    public void replyNotice(@ModelAttribute("recordId") String recordId,@ModelAttribute("userId") String userId,@ModelAttribute("userType") String userType,@ModelAttribute("replyContent") String replyContent,@ModelAttribute("params") String params ,Model model) {
        try {
        	String json = null;
        	if(!StringUtil.isEmpty(params)){
        		json = desDecrypt(params);// 统一解密params
        		if(null != json){
        			JSONObject jsonObj = JSONObject.parseObject(json);
        			noticeServiceImpl.saveReplayNotice(jsonObj.getString("userId"), jsonObj.getString("recordId"), jsonObj.getString("replyContent"), jsonObj.getString("userType"));
        			SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), new Result(Constants.RETURN_SUCCESS_INTERFACE, "保存成功", null));
        		}
        	} else {
        		noticeServiceImpl.saveReplayNotice(userId, recordId, replyContent, userType); 
        		SpringMvcUtil.responseJSONWriter(getResponse(),new Result(Constants.RETURN_SUCCESS_INTERFACE, "保存成功", null));
        	}
        } catch (Exception e) {
        	if(!StringUtil.isEmpty(params)){
        		SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), new Result(Constants.RETURN_WRONGPARAM_INTERFACE, "保存失败", null));
        	}else{
        		SpringMvcUtil.responseJSONWriter(getResponse(),new Result(Constants.RETURN_WRONGPARAM_INTERFACE, "保存失败", null));
        	}
        	logger.error("回复失败", e);
        }        
    }

    @RequestMapping("/logout")
    public String loginout() {
        HttpSession session = SpringMvcUtil.getRequest().getSession();
        session.invalidate();
        return "forward:/tologin.do";
    }

    /**
     * 获取图片扩展名（图片后缀）
     * 
     * @return
     */
    public String getAttachmentName(String path) {
        String fileName = null;
        if (StringUtil.isNotEmpty(path)) {
            fileName = path.substring(path.lastIndexOf("/") + 1);
        }
        return fileName;
    }

    /**
     * 验证消息请求方是否为合法的请求方 Authorization：
     * 
     * @return
     */
    public boolean validateHttpHeader() {
        Enumeration<String> emuHeaderNames = getRequest().getHeaderNames();
        boolean flag = false;
        while (emuHeaderNames.hasMoreElements()) {
            String name = (String) emuHeaderNames.nextElement();
            if ("authorization".equals(name)) {
                String authHeader = null;
                try {
                    authHeader = DesUtil.decrypt(getRequest().getHeader(name));// 解密digest消息头
                    logger.info("authHeader: " + authHeader);
                } catch (Exception e) {
                    logger.warn("解密HTTP消息头信息失败");
                    e.printStackTrace();
                }
                if (AUTHDIGEST.equals(authHeader)) {// 与本地消息头进行对比
                    flag = true;
                    logger.info("HTTP消息头验证通过");
                    return flag;
                } else {
                    flag = false;
                    logger.info("HTTP消息头未验证通过");
                }
            }
        }
        return true;
    }

    /**
     * /** 加密的Ajax返回
     *
     * @param result
     * @param method
     */
    public void responseAjaxData(Result result, String method) {
        // 定义返回的数据类型：json
        getResponse().setContentType("application/octet-stream");
        getResponse().setCharacterEncoding("UTF-8");
        String resDigest = null;
        // 使用已经加密好的返回DIGEST
        resDigest = RESDIGEST;
        getResponse().addHeader("Authorization", resDigest);// 消增加息头信息由信息发布平台返回的HTTP信息
        PrintWriter pw = null;
        try {
            pw = getResponse().getWriter();
            if (pw != null) {
                String returnJson = JSON.toJSONString(result, SerializerFeature.WriteMapNullValue);
                logger.info("调用webservice " + method + " returnJSon: " + returnJson);
                String returnDesJson = DesUtil.encrypt(returnJson);
                logger.info("调用webservice " + method + " returnDesJson: " + returnDesJson);
                pw.print(returnDesJson);
            }
        } catch (Exception e) {
            logger.error("返回接口调用返回值失败");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * 解密
     *
     * @param params
     *            字符串
     * @return 字符串
     */
    private String desDecrypt(String params) {
        String desStr = "";
        try {
            logger.info("解密前Params：" + params);
            desStr = DesUtil.decrypt(params);
            logger.info("解密后Params：" + desStr);
        } catch (Exception e) {
            logger.error("解密失败");
        }
        return desStr;
    }

    @Override
    protected IMybatisBasePersitenceService<Notice, String> getBaseService() {
        return noticeServiceImpl;
    }

    @Override
    protected String getPrefix() {
        return "/notice/";
    }

    @RequestMapping("/uploadFile")
    public void uploadFile(@ModelAttribute("params") String params) {
        String json = null;
        Result result = new Result();
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    // 验证用户是否有效
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {//&& validatePlatformUser(jsonObj)
                        getRequest().setCharacterEncoding("UTF-8");
                        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) getRequest();
                        Iterator<String> fileNames = multipartRequest.getFileNames();
                        Map<String, String> fileMap = new HashMap<String, String>();
                        while (fileNames.hasNext()) {
                            String fileFieldName = fileNames.next();
                            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(fileFieldName);
                            String filePath = noticeServiceImpl.storeFile(file).replaceAll("\\\\", "/");
                            fileMap.put("filePath", filePath);
                        }
                        result.setData(fileMap);
                        result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                    } else {
                        result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        }
        SpringMvcUtil.responseAjaxDataWithAuth(getResponse(), result);
    }

    @RequestMapping("downloadFile")
    public void downloadFile(@ModelAttribute("params") String params) {
        String json = null;
        Result result = new Result();
        InputStream in = null;
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    // 验证用户是否有效
                    if (noticeServiceImpl.validatePlatformId(jsonObj.getString("platformId")) ) {//&& validatePlatformUser(jsonObj)
                        String noticeId = jsonObj.getString("noticeId");
                        Notice notice = noticeServiceImpl.getNoticeByIdForWeb(noticeId);
                        String attachName = null;
                        if (notice != null && StringUtil.isNotEmpty(notice.getAttachPath()) && StringUtil.isNotEmpty(notice.getAttachment())) {
                            attachName = notice.getAttachment();
                            getResponse().setCharacterEncoding("utf-8");
                            getResponse().setContentType("multipart/form-data");
                            getResponse().setHeader("Content-Disposition", "attachment;fileName=" + attachName);
                            String path = SpringMvcUtil.getRequest().getSession().getServletContext().getRealPath("") + notice.getAttachPath();
                            File file = new File(path);
                            in = new FileInputStream(file);
                            byte[] content = new byte[in.available()];
                            in.read(content);
                            Map<String, Object> dataMap = new HashMap<String, Object>();
                            dataMap.put("content", content);
                            dataMap.put("fileLength", file.length());
                            dataMap.put("attachment", attachName);
                            result.setData(dataMap);
                        } else {
                            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
                        }
                    } else {
                        result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
                    }
                } else {
                    result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            result.setStatus(Constants.RETURN_ERROR_INTERFACE);
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.warn("下载文件关闭流失败");
                }
            }
        }
        super.responseAjaxData(result);
    }

    /**
     * 下载通知通告附件
     * 
     * @param noticeId
     *            通知通告Id
     * @param userId
     *            用户id
     * @return
     */
    @RequestMapping("/download")
    public String download(String noticeId) {
        Notice notice = noticeServiceImpl.getNoticeByIdForWeb(noticeId);
        String attachName = null;
        if (notice != null) {
            attachName = notice.getAttachment();
        } else {
            return null;
        }
        getResponse().setCharacterEncoding("utf-8");
        getResponse().setContentType("multipart/form-data");
        getResponse().setHeader("Content-Disposition", "attachment;fileName=" + attachName);
        InputStream in = null;
        OutputStream os = null;
        try {
            String path = SpringMvcUtil.getRequest().getSession().getServletContext().getRealPath("") + notice.getAttachPath();
            in = new FileInputStream(new File(path));
            os = getResponse().getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = in.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            SpringMvcUtil.getRealPath();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {// 关闭流
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 其他平台登录
     *
     * @param params
     */
    @RequestMapping(value = "/otherPlatformLogin", method = RequestMethod.POST)
    public String otherPlatformLogin(@ModelAttribute("params") String params, RedirectAttributes attr) {

        Result result = new Result();
        String json = null;
        String loginName = null;// 易达ID
        String password = null;// 标记为已读状态的NoticeRecordId
        String platformId = null;// 回复的内容
        try {
            if (validateHttpHeader()) {// 验证消息头
                json = desDecrypt(params);// 统一解密params
                if (json != null) {
                    JSONObject jsonObj = JSONObject.parseObject(json);
                    if (jsonObj != null) {
                        loginName = jsonObj.getString("username");
                        password = jsonObj.getString("password");
                        platformId = jsonObj.getString("platformId");
                        if (StringUtil.isNotEmpty(loginName) && StringUtil.isNotEmpty(password) && StringUtil.isNotEmpty(platformId)) {// 判断必填参数是否都有值

                            BaseUser user = new BaseUser();
                            user.setLoginName(loginName);
                            user.setPassword(password);// 其他平台传过来是已经加密后的
                            user.setLoginWay(1); // 最后登录方式, 1：web网页; 2：wap网页;//
                                                 // 3：手机客户端；4：未知
                            user.setPlatformId(platformId);// 属于哪个平台
                            user = baseUserService.loginWithPlatform(user);
                            if (Constants.RETURN_SUCCESS.equals(user.getRemark())) {
                                setAuthenToken(user);
                                result.setStatus(Constants.RETURN_SUCCESS_INTERFACE);
                                result.setMessage("登录成功！");
                            } else {
                                result.setStatus(Constants.RETURN_ERROR_INTERFACE);
                                result.setMessage(user.getRemark());
                            }
                        }
                    }
                }
            } else {// HTTP消息头错误
                result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        getRequest().removeAttribute("params");
        return "redirect:/main/union.do";

    }
    
	/**
	 * 
	 * @Description: 方法功能说明： app删除/还原通知
	 * @param params
	 * @return void
	 * @author yty
	 * @date 2016年8月30日下午4:21:25
	 */
	@RequestMapping(value = "/delOrRedNoticeByApp", method = RequestMethod.POST)
	public void delOrRedNoticeByApp(@ModelAttribute("params") String params) {
		String json = null;
		Result result = null;
		String edaId = null;// 易达ID
		String[] recordIdList = null;// 标记为已读状态的NoticeRecordId
		Map<String, Object> map = null;
		try {
			if (validateHttpHeader()) {// 验证消息头
				json = desDecrypt(params);// 统一解密params
				logger.info("=============================delOrRedNoticeByApp接口：请求参数解密后为" + json + "=========================================");
				if (json != null) {
					JSONObject jsonObj = JSONObject.parseObject(json);
					if (jsonObj != null) {
						edaId = jsonObj.getString("edaId");
						String recordIdListStr = jsonObj.getString("recordIdList");
						String type = jsonObj.getString("type");
						if(StringUtil.isNotEmpty(recordIdListStr) && StringUtil.isNotEmpty(type)) {
							recordIdList = recordIdListStr.split(",");
							if (StringUtil.isNotEmpty(edaId) && recordIdList != null && recordIdList.length > 0) {// 判断必填参数是否都有值
								map = new HashMap<String, Object>();
								map.put("edaId", edaId);
								map.put("type", type);
								map.put("array",recordIdList);
								result = noticeServiceImpl.delOrRedNoticeByApp(map);
							}
						}
					}
				}
			} else {// HTTP消息头错误
				result = new Result();
				result.setStatus(Constants.RETURN_UNAUTHORISED_INTERFACE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == null) {// 当参数不正确时
			result = new Result();
			result.setStatus(Constants.RETURN_WRONGPARAM_INTERFACE);
		}
		this.responseAjaxData(result, "noticeList");
	}
}
